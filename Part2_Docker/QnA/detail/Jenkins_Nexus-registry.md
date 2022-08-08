# Jenkins에서 Nexus Registry 로그인 문제 해결방법 
### * 목적 : Jenkins에서 Nexus를 Docker Registry로 사용할 때 로그인시 연결 문제가 발생할 경우, 다음과 같은 방법으로 해결한다.


## 1. 개요
* Jenkins가 버전이 업데이트가 된 이후로 부터 HTTP만으로의 Docker Registry로의 로그인이 안되는 문제가 발생했다.
* 여러 검증 결과, HTTP만으로는 진행이 불가 하기 때문에, 인증서를 적용한 HTTPS를 이용해서 Docker Regitry로의 로그인을 구현하고자 한다.
* 인증서를 적용한 HTTPS를 이용해서 Docker Registry로의 로그인 구현 방법은 다음과 같은 절차로 진행하면 된다.
* Docker 컨테이너 Push/Pull을 암호화 해서 처리하기 위해서는 반드시 HTTPS 방식으로 처리하는게 중요하기 때문에 이번에 같이 적용하면 계속 HTTPS로 사용가능하다.


## 2. Jenkins 컨테이너 재배포
* 컨테이너를 재배포 하더라도, 기존 데이터는 VM 내에 남아있기 때문에 데이터의 유실 혹은 변경 없이도 실습 진행이 가능하다.
  
(1) 기동중인 컨테이너 중지 및 삭제
```
# 기동중인 컨테이너 확인
$ docker ps

# 컨테이너 중지
$ docker stop <Jenkins 컨테이너명>

# 컨테이너 삭제
$ docker rm <Jenkins 컨테이너명>
```
(2) Jenkins 재배포 수행
* Jenkins 재배포 시 주의해야 할 점은 서버(VM)에 설치된 Docker관련 파일을 Attach하여 Jenkins내 Docker에서도 사용할 수 있게끔 Docker Volume 설정 옵션인 -v 옵션을 주어 설정해야 한다는 것이다. 
* 설정하는 항목은 다음과 같다.
> -v $(which docker):/usr/bin/docker
> 
> -v /var/run/docker.sock:/var/run/docker.sock
```
# Jenkins 재배포 수행
$ docker run --name jenkins -d -u root -p 8080:8080 -p 50000:50000 -v ~/jenkins:/var/jenkins_home -v $(which docker):/usr/bin/docker -v /var/run/docker.sock:/var/run/docker.sock jenkins/jenkins:latest

# Jenkins 재배포 수행 결과 확인
$ docker ps

# Jenkins 실행 로그 스트림 확인
$ docker logs -f <Jenkins 컨테이너 ID>
```

(3) Jenkins 접속 확인
* http://<Jenkins 서버의 Public 주소>:8080으로 접속
* 기존의 Login 정보로 접속


## 3. Nexus 재배포용 Dockerfile 및 Script 준비
* Nexus에서 인증서 적용하여 HTTPS로 Docker Registry 로그인 및 컨테이너 이미지 관리를 위해 다음과 같이 Dockerfile을 변경한다.
* 추가 Script를 Docker Build시 포함 할 수 있도록 적용한다.

(1) Docker 파일 작성
```
$ vi Dockerfile
FROM sonatype/nexus3:3.32.0
 
USER root
 
RUN chown -R nexus:nexus ${NEXUS_HOME}/etc \
    && sed '/^application-port/s:$:\napplication-port-ssl=8443:' -i ${NEXUS_HOME}/etc/nexus-default.properties \
    && sed '/^nexus-args/s:$:,${jetty.etc}/jetty-https.xml:' -i ${NEXUS_HOME}/etc/nexus-default.properties \
    && rm -rf ${NEXUS_HOME}/etc/ssl && ln -s ${NEXUS_DATA}/etc/ssl ${NEXUS_HOME}/etc/ssl
 
COPY start.sh /usr/local/bin
 
USER nexus
 
EXPOSE 8443
 
CMD start.sh
```

(2) Script 파일 작성
```
$ vi start.sh 
#! /bin/bash

if [ ! -e "$NEXUS_DATA/etc/ssl/keystore.jks" ]; then
mkdir -p "$NEXUS_DATA/etc/ssl"
chmod go-rwx "$NEXUS_DATA/etc/ssl"
keytool -genkeypair -keystore $NEXUS_DATA/etc/ssl/keystore.jks -storepass password -keypass password \
        -alias jetty -keyalg RSA -keysize 2048 -validity 5000 \
        -dname "CN=*.${HOSTNAME}, OU=FastCampus, O=FastCampus, L=Gangnam, ST=Seoul, C=KR" \
        -ext "SAN=DNS:${SAN_DNS}" -ext "BC=ca:true"
fi

sh -c ${SONATYPE_DIR}/start-nexus-repository-manager.sh
```

(3) Script 파일대상 실행 권한 부여 및 파일생성 현황 확인 
```
$ chmod +x start.sh 
$ ls -al
```


## 4. 기존 Nexus 컨테이너 중지 및 삭제
* 기존 사용한 Nexus 컨테이너를 중지 및 삭제해도 데이터 파일의 경우는 서버(VM)에 남아있기 때문에 기존에 설정항목이나 데이터는 그대로 남아 있다.
* 이에 Nexus Docker 컨테이너를 중지하고 삭제한다.
```
# 기동중인 컨테이너 확인
$ docker ps

# 컨테이너 중지
$ docker stop <Nexus 컨테이너명>

# 컨테이너 삭제
$ docker rm <Nexus 컨테이너명>
```


## 5. 컨테이너 이미지 빌드 및 실행
* HTTPS 인증서 처리 모듈이 반영된 Nexus 컨테이너 이미지를 빌드 및 실행한다.
  
(1) Nexus 컨테이너 이미지 빌드
```
$ docker build -t fastcampus-nexus3:3.32.0 .
```

(2) 생성된 Nexus 컨테이너 이미지 확인
```
$ docker images
```

(3) Nexus 컨테이너 실행
* 실행시 주의해야할 점은 SAN_DNS의 변수로는 반드시 Nexus 컨테이너를 실행중인 AWS EC2 VM의 Private DNS 도메인명을 넣어야 한다는 것이다.
```
# Nexus 재배포 수행
$ docker run -d -u root --net=host -e SAN_DNS=<Nexus VM의 프라이빗 IP DNS 이름> --name nexus -v ~/nexus-data:/nexus-data  fastcampus-nexus3:3.32.0

# Nexus 재배포 수행 결과 확인
$ docker ps

# Nexus 실행 로그 스트림 확인
$ docker logs -f <Nexus 컨테이너 ID>
```
* AWS EC2 VM의 Private DNS 도메인명 확인 방법
> AWS EC2 > 인스턴스 메뉴 > (Nexus 컨테이너가 실행중인 VM 선택 - 보통은 test-common VM에서 실행) > 세부정보 > 프라이빗 IP DNS 이름

![nexus-vm_private-ip-dns-name](https://devopsrunbook-fastcampus.s3.ap-northeast-2.amazonaws.com/FastCampus/Part2_Docker/Chapter06/3-1_nexus-jenkins-docker/nexus-vm_private-ip-dns-name.png)


## 6. 인증서 Export
* Nexus 컨테이너에서 HTTPS용도로 사용할 인증서를 생성하고 Export 해야한다.
* 해당 인증서는 오직 현재 서버(VM)위에서 기동중인 Nexus 컨테이너의 Docker Registry를 연결할 때만 사용해야 한다.
  
(1) Nexus에서 내장되어 있는 keytool을 이용해서 nexus.crt라는 Nexus 인증서를 생성후 Export 한다.
> 인증서명은 nexus.crt로 생성된다.
```
$ docker exec nexus keytool -printcert -sslserver 127.0.0.1:8443 -rfc | tee nexus.crt
-----BEGIN CERTIFICATE-----
MIIDpzCCAo+gAwIBAgIECefZAjANBgkqhkiG9w0BAQsFADBxMQswCQYDVQQGEwJL
... 중략 ...
tLLOW1jWZibfItnWEePdaeer7DNxDoOPONdO
-----END CERTIFICATE-----
```

(2) 생성된 nexus.crt 인증서 내용을 확인한다.
> 출력된 내역중 CN, DNS등의 정보가 입력된 정보 (FastCampus, Private DNS 도메인명) 과 동일한 값이 입력이 되었으면 정상적으로 인증서가 생성된 것이다.
```
$ openssl x509 -in nexus.crt -text |grep -i ' cn\|dns\|before'  -2
```


## 7. 인증서 Import
* 해당 인증서를 서버(VM) 내에 Import해서 인증서를 활성화 하고, Nexus에 적용할 수 있도록 nexus.crt파일을 설정한다.
```
$ sudo cp -av nexus.crt  /usr/local/share/ca-certificates/nexus.crt
$ sudo update-ca-certificates
```
* Docker Registory 접속할 때에도 인증서를 참조할 수 있도록 Docker 디렉토리 아래 서버(VM)의 프라이빗 ip dns 이름 및 5443 포트의 디렉토리를 생성한다.
* nexus.crt 인증서를 복사해 CA인증서로 등록한다.
```
$ sudo mkdir /etc/docker/certs.d/<Nexus VM의 프라이빗 ip dns 이름>\:5443/ -p
$ sudo cp -av nexus.crt /etc/docker/certs.d/<Nexus VM의 프라이빗 ip dns 이름>\:5443/ca.crt
```


## 8. Nexus Container Registry 생성
* Nexus Management Console에서 Container Registry 설정을 수행한다.

(1) Nexus Management Console 로그인 > Administration(톱니바퀴) > Repositories 메뉴 > Create Repository 버튼 클릭
![nexus-push-create-repository-button](https://devopsrunbook-fastcampus.s3.ap-northeast-2.amazonaws.com/FastCampus/Part2_Docker/Chapter06/3-1_nexus-jenkins-docker/nexus-push-create-repository-button.png)
(2) Docker Hosted를 선택
![nexus-select-docker-hosted](https://devopsrunbook-fastcampus.s3.ap-northeast-2.amazonaws.com/FastCampus/Part2_Docker/Chapter06/3-1_nexus-jenkins-docker/nexus-select-docker-hosted.png)
(3) Docker Registry관련 정보 입력
![nexus-insert-docker-registry](https://devopsrunbook-fastcampus.s3.ap-northeast-2.amazonaws.com/FastCampus/Part2_Docker/Chapter06/3-1_nexus-jenkins-docker/nexus-insert-docker-registry.png)
> 3.1) Docker Registry명을 "container-registry"로 입력한다.
> 
> 3.2) https를 체크, 포트를 5443으로 입력
> 
> 3.3) Allow anonymous docker pull을 체크
> 
> 3.4) Create repository 버튼 클릭

(4) Nexus에서 Docker Registry 생성 결과 확인
![nexus-created-docker-registry](https://devopsrunbook-fastcampus.s3.ap-northeast-2.amazonaws.com/FastCampus/Part2_Docker/Chapter06/3-1_nexus-jenkins-docker/nexus-created-docker-registry.png)


## 9. Nexus 서버 로컬에서 Nexus Docker Registry로의 로그인 테스트
* URL 입력시 반드시 Nexus VM의 Private DNS 도메인명으로 입력한다.
* 포트는 5443으로 설정한다.
* test 계정의 ID 및 Password를 입력한다.
```
$ docker login -u '<Nexus 로그인 계정 ID>' -p '<Nexus 로그인 계정 Password>' https://<Nexus VM의 프라이빗 IP DNS 이름>:5443
```
> Login Succeeded가 출력되면 정상적으로 Nexus Docker Registry에 로그인이 된것이다.

## 10. Nexus Docker Registry의 HTTPS 포트를 Nexus VM의 Security Group에 등록
* AWS Management Console > EC2 > 인스턴스 > test-common(Nexus VM) 클릭 > 보안 탭 > 보안 그룹 링크 클릭
![nexus-vm-sg](https://devopsrunbook-fastcampus.s3.ap-northeast-2.amazonaws.com/FastCampus/Part2_Docker/Chapter06/3-1_nexus-jenkins-docker/nexus-vm-sg.png)
* 인바운드 규칙 편집을 눌러 다음의 정보를 입력하여 5443 포트 등록
![nexus-vm-sg-detail](https://devopsrunbook-fastcampus.s3.ap-northeast-2.amazonaws.com/FastCampus/Part2_Docker/Chapter06/3-1_nexus-jenkins-docker/nexus-vm-sg-detail.png)
> 유형 : 사용자 지정 TCP
> 
> 프로토콜 : TCP
> 
> 포트 범위 : 5443
> 
> 소스 : <Nexus VM의 VPC의 IP 대역/CIDR 등록>

## 11. Jenkins 서버 로컬에서 Nexus Docker Registry로의 로그인 테스트
* Jenkins 서버 로컬에서 Nexus 서버에 있는 nexus.crt 인증서를 복사하여 Jenkins 서버에 붙여넣기하여 파일을 생성한다.
```
$ vi nexus.crt
-----BEGIN CERTIFICATE-----
MIIDpzCCAo+gAwIBAgIECefZAjANBgkqhkiG9w0BAQsFADBxMQswCQYDVQQGEwJL
... 중략 ...
tLLOW1jWZibfItnWEePdaeer7DNxDoOPONdO
-----END CERTIFICATE-----
```
* Docker Registory 접속할 때에도 인증서를 참조할 수 있도록 Docker 디렉토리 아래 서버(VM)의 프라이빗 ip dns 이름 및 5443 포트의 디렉토리를 생성한다.
* nexus.crt 인증서를 복사해 CA인증서로 등록한다.
```
$ sudo mkdir /etc/docker/certs.d/<Nexus VM의 프라이빗 ip dns 이름>\:5443/ -p
$ sudo cp -av nexus.crt /etc/docker/certs.d/<Nexus VM의 프라이빗 ip dns 이름>\:5443/ca.crt
```
* Nexus Docker Registry로의 로그인을 다음과 같이 수행한다.
* URL 입력시 반드시 Nexus VM의 Private DNS 도메인명으로 입력한다.
* 포트는 5443으로 설정한다.
* test 계정의 ID 및 Password를 입력한다.
```
$ docker login -u '<Nexus 로그인 계정 ID>' -p '<Nexus 로그인 계정 Password>' https://<Nexus VM의 프라이빗 IP DNS 이름>:5443
```
> Login Succeeded가 출력되면 정상적으로 Nexus Docker Registry에 로그인이 된것이다.


## 12. Deploy VM 로컬에서 Nexus Docker Registry로의 로그인 테스트
* Deploy VM 서버 로컬에서 Nexus 서버에 있는 nexus.crt 인증서를 복사하여 Jenkins 서버에 붙여넣기하여 파일을 생성한다.
```
$ vi nexus.crt
-----BEGIN CERTIFICATE-----
MIIDpzCCAo+gAwIBAgIECefZAjANBgkqhkiG9w0BAQsFADBxMQswCQYDVQQGEwJL
... 중략 ...
tLLOW1jWZibfItnWEePdaeer7DNxDoOPONdO
-----END CERTIFICATE-----
```
* Docker Registory 접속할 때에도 인증서를 참조할 수 있도록 Docker 디렉토리 아래 서버(VM)의 프라이빗 ip dns 이름 및 5443 포트의 디렉토리를 생성한다.
* nexus.crt 인증서를 복사해 CA인증서로 등록한다.
```
$ sudo mkdir /etc/docker/certs.d/<Nexus VM의 프라이빗 ip dns 이름>\:5443/ -p
$ sudo cp -av nexus.crt /etc/docker/certs.d/<Nexus VM의 프라이빗 ip dns 이름>\:5443/ca.crt
```
* Nexus Docker Registry로의 로그인을 다음과 같이 수행한다.
* URL 입력시 반드시 Nexus VM의 Private DNS 도메인명으로 입력한다.
* 포트는 5443으로 설정한다.
* test 계정의 ID 및 Password를 입력한다.
```
$ docker login -u '<Nexus 로그인 계정 ID>' -p '<Nexus 로그인 계정 Password>' https://<Nexus VM의 프라이빗 IP DNS 이름>:5443
```
> Login Succeeded가 출력되면 정상적으로 Nexus Docker Registry에 로그인이 된것이다.


## 13. Jenkins 에서 Nexus Docker Registry 적용 및 CI/CD Pipeline 테스트
* 실제 수행하고자 하는 Jenkinsfile에 설정을 다음과 같이 변경한다. (설정파일 경로 : Part2_Docker/Chapter06/3-1_nexus-jenkins-docker/Jenkinsfile)
> mainDir의 경우는 다음 경로의 디렉토리로 입력한다. 예) Part2_Docker/Chapter06/3-1_nexus-jenkins-docker
> 
> nexusUrl은 반드시 Nexus 서버(VM)의 Private DNS 도메인명을 설정한다. 예) ip-10-0-14-166.ap-northeast-2.compute.internal:5443
> 
> repository는 Nexus Management Console에서 설정한 Repository명을 입력한다. 예) container-registry
> 
> nexusid는 Nexus 로그인시 사용되는 ID를 입력한다.(해당값의 경우, 중복값이 있으므로 변수처리함) 예) test
> 
> nexuspw는 Nexus 로그인시 사용되는 PW를 입력한다.(해당값의 경우, 중복값이 있으므로 변수처리함) 예) !xptmxm00
* Jenkinsfile 중간 gradle jib 실행하는 같은 라인에 기존에 명시가 된 다음의 값을 삭제한다. (있을 경우)
```
-DsendCredentialsOverHttp=true
```
> 삭제의 이유는 HTTPS 인증서를 통해 Nexus에 Push를 하기 때문에 더이상 해당 옵션이 불필요하므로 삭제처리 한다.
* 해당 정보들을 입력한후, 웹브라우저에서 Jenkins(http://<Jenkins VM의 Public DNS 도메인명>:8080)접속 및 로그인한다.
* Jenkins Pipeline을 실행시켜 정상적으로 동작하는지 확인한다.
* Jenkins Pipeline 실행 결과를 보면, 중간에 정상적으로 Nexus Docker Registry로의 로그인 및 Push/Pull이 된 것을 확인할 수 있다.

(1) Jenkins Pipeline 접속 및 빌드 수행
![jenkins-pipeline](https://devopsrunbook-fastcampus.s3.ap-northeast-2.amazonaws.com/FastCampus/Part2_Docker/Chapter06/3-1_nexus-jenkins-docker/jenkins-pipeline.png)

(2) Jenkins Job 수행시 Nexus Docker Registry 로그인
![jenkins-job-nexus-docker-login](https://devopsrunbook-fastcampus.s3.ap-northeast-2.amazonaws.com/FastCampus/Part2_Docker/Chapter06/3-1_nexus-jenkins-docker/jenkins-job-nexus-docker-login.png)

(3) Jenkins Job 수행시 Nexus Docker Registry로의 Jib으로 빌드된 컨테이너 이미지 Push
![docker-push-to-nexus-docker-registry](https://devopsrunbook-fastcampus.s3.ap-northeast-2.amazonaws.com/FastCampus/Part2_Docker/Chapter06/3-1_nexus-jenkins-docker/docker-push-to-nexus-docker-registry.png)

(4) Jenkins Job 수행시 Deploy VM에서 Nexus Docker Registry 로그인 및에서 컨테이너 이미지 Pull
![deploy-vm-nexus-docker-login](https://devopsrunbook-fastcampus.s3.ap-northeast-2.amazonaws.com/FastCampus/Part2_Docker/Chapter06/3-1_nexus-jenkins-docker/deploy-vm-nexus-docker-login.png)

(5) Jenkins Job 완료 및 Docker 컨테이너 정상 실행
![jenkins-cicd-pipeline-successed](https://devopsrunbook-fastcampus.s3.ap-northeast-2.amazonaws.com/FastCampus/Part2_Docker/Chapter06/3-1_nexus-jenkins-docker/jenkins-cicd-pipeline-successed.png)

(6) Nexus 확인 결과, Docker Registry에 정상 Push 및 저장
![pushed-docker-image-to-nexus](https://devopsrunbook-fastcampus.s3.ap-northeast-2.amazonaws.com/FastCampus/Part2_Docker/Chapter06/3-1_nexus-jenkins-docker/pushed-docker-image-to-nexus.png)

(7) Deploy VM에서 Nexus Docker Registry에서의 컨테이너 이미지 Pull
![pulled-from-nexus-docker-image](https://devopsrunbook-fastcampus.s3.ap-northeast-2.amazonaws.com/FastCampus/Part2_Docker/Chapter06/3-1_nexus-jenkins-docker/pulled-from-nexus-docker-image.png)

(8) Deploy VM에서 Pull된 컨테이너 정상 기동
![deployed-running-container](https://devopsrunbook-fastcampus.s3.ap-northeast-2.amazonaws.com/FastCampus/Part2_Docker/Chapter06/3-1_nexus-jenkins-docker/deployed-running-container.png)

(9) Deploy VM의 퍼블릭 ip 도메인 이름으로 웹브라우저에서 접속후 Spring Boot Web Application 정상 출력
![spring-boot-web-app](https://devopsrunbook-fastcampus.s3.ap-northeast-2.amazonaws.com/FastCampus/Part2_Docker/Chapter06/3-1_nexus-jenkins-docker/spring-boot-web-app.png)
