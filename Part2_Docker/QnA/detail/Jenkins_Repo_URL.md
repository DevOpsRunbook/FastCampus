# Jenkins에서 Github Repository를 SSH URL로 연결시 문제 해결방법 
### * 목적 : Jenkins에서 Github Repository를 SSH URL로 연결시 문제가 발생할 경우, 다음과 같은 방법으로 해결한다.


## 1. 개요
* 최근에 Latest 버전으로 배포되는 Jenkins 컨테이너 이미지 기반으로 Jenkins를 실행한뒤, Github Repository를 SSH URL로 연결시 다음과 같은 문제가 발생하였다.
![jenkins-repo-url-err](https://devopsrunbook-fastcampus.s3.ap-northeast-2.amazonaws.com/FastCampus/Part2_Docker/Chapter02/2-jenkinsci/jenkins-repo-url-err.png)
* 이 문제를 해결하기 위해서는 다음과 같은 방법으로 해결한다.


## 2. Git Host Key Verification 설정 해제
* Jenkins에서 Github Repository를 SSH URL로 연결시 발생하는 문제는 Git Host Key Verification 문제이므로, 다음과 같은 방법으로 설정을 해제한다.

(1) Jenkins 메인화면 > Jenkins 관리 > Security > Configure Global Security 클릭
![jenkins-mgmt-global-sec](https://devopsrunbook-fastcampus.s3.ap-northeast-2.amazonaws.com/FastCampus/Part2_Docker/Chapter02/2-jenkinsci/jenkins-mgmt-global-sec.png)
(2) Git Host Key Verification Configuration 설정 변경
![git-host-key-veri-config](https://devopsrunbook-fastcampus.s3.ap-northeast-2.amazonaws.com/FastCampus/Part2_Docker/Chapter02/2-jenkinsci/git-host-key-veri-config.png)
> 2.1) 페이지의 맨 아래 끝까지 스크롤을 해서 "Git Host Key Verification Configuration" 부분을 확인한다.
> 
> 2.2) 기본값으로 "Known hosts file"로 설정되어 있는 것을 확인한다.
> 
> 2.3) "No verification"으로 값을 변경한다.
> 
> 2.4) "Save" 버튼을 클릭하여 적용한다.


## 3. Jenkins에서 Github Repository를 SSH URL로 연결 확인
* 아래 캡쳐와 같이 에러 표시가 나오지 않는다면, 정상적으로 연결 되었음을 확인 할 수 있다. 
![jenkins-github-conn-ok](https://devopsrunbook-fastcampus.s3.ap-northeast-2.amazonaws.com/FastCampus/Part2_Docker/Chapter02/2-jenkinsci/jenkins-github-conn-ok.png)

