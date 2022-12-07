# Gradle 버전 업그레이드 방법
## Case 1. Ubuntu 리눅스에서 gradle init 명령어 실행시 에러메시지 발생할 때 해결 방법
**Question:** Ubuntu 리눅스에서 gradle init 명령어 실행시 다음의 에러메시지 발생
```
ubuntu@ip-172-31-0-111:~$ gradle init --dsl=groovy --type=java-application \
> --test-framework=junit \
> --package=com.test --project-name=test-docker-spring-boot
Starting a Gradle Daemon (subsequent builds will be faster)

FAILURE: Build failed with an exception.

* What went wrong:
Problem configuring task :init from command line.
> Unknown command-line option '--dsl'.

* Try:
Run gradle help --task :init to get task usage details. Run with --stacktrace option to get the stack trace. Run with --info or --debug option to get more log output. Run with --scan to get full insights.

* Get more help at https://help.gradle.org

BUILD FAILED in 5s
ubuntu@ip-172-31-0-111:~$
```

**Answer:** Gradle 버전을 7이상으로 업데이트 (수행 방식은 아래 내용 그대로 적용하시면 되십니다.)

(1) 현재 설치된 Gradle의 버전 확인
* Ubuntu 리눅스에서 기본 Gradle 패키지를 설치할 경우, Gradle 4버전으로 설치됨
* Gradle 4버전의 경우, Question에 명시된 gradle init과 같은 명령어를 그대로 실행할 경우, 에러가 발생함
```
$ gradle -v
```
![gradle-ver4](https://devopsrunbook-fastcampus.s3.ap-northeast-2.amazonaws.com/FastCampus/Part2_Docker/Chapter02/2-gradle/gradle-ver4.png)

(2) 다음 명령을 실행하여 Ubuntu 리눅스에 PPA 저장소를 추가
```
$ sudo apt -y install vim apt-transport-https dirmngr wget software-properties-common
$ sudo add-apt-repository ppa:cwchien/gradle
```

(3) Ubuntu 리눅스 패키지 업데이트 및 Gradle을 7이상 버전으로 설치
```
sudo apt-get update
sudo apt -y install gradle
```

(4) 설치된 Gradle 버전 확인
* 여기서는 7버전 이상으로 출력
![gradle-ver7](https://devopsrunbook-fastcampus.s3.ap-northeast-2.amazonaws.com/FastCampus/Part2_Docker/Chapter02/2-gradle/gradle-ver7.png)

(5) Ubuntu 리눅스에서 gradle init 명령어 재실행시 정상 수행됨을 확인
```
$ gradle init --dsl=groovy --type=java-application --test-framework=junit --package=com.test --project-name=test-docker-spring-boot
```
![gradle-init-success](https://devopsrunbook-fastcampus.s3.ap-northeast-2.amazonaws.com/FastCampus/Part2_Docker/Chapter02/2-gradle/gradle-init-success.png)