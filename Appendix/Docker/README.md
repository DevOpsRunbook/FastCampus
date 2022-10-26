# Docker 구성 방법
## 1. Docker 설치
### 1.1 MacOS 설치 방법
(1) 아래 링크에서 Docker Desktop on Mac 설치를 위한 .dmg 파일을 다운로드하여 설치를 진행해줍니다.
> https://www.docker.com/products/docker-desktop/

(2) 다운로드한 .dmg 파일을 실행하여 Docker.app을 응용 프로그램으로 복사해줍니다.
(3) 애플리케이션을 실행하면 메뉴 막대 상단에 아이콘이 생성됩니다. 또한 터미널에서 docker 명령어를 통해 정상적으로 설치된 것을 확인할 수 있습니다.
```
$ docker version
```
### 1.2 Ubuntu/Debian 리눅스 설치 방법
(1) apt가 HTTPS 프로토콜을 통해서 repository를 사용할 수 있도록 패키지를 설치한다.
```
$ sudo apt-get update
$ sudo apt-get install \
    apt-transport-https \
    ca-certificates \
    curl \
    gnupg \
    lsb-release
```
(2) Docker의 공식 GPG key를 추가한다.
```
$ curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /usr/share/keyrings/docker-archive-keyring.gpg
```
(3) apt source list에 repository를 추가한다.
```
$ echo \
"deb [arch=amd64 signed-by=/usr/share/keyrings/docker-archive-keyring.gpg] https://download.docker.com/linux/ubuntu \
$(lsb_release -cs) stable" | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null
```
(4) apt package index를 update하고 Docker를 설치한다.
```
$ sudo apt-get update
$ sudo apt-get install docker-ce
```
(5) 터미널에서 docker 명령어를 통해 정상적으로 설치된 것을 확인할 수 있습니다.
```
$ docker version
```
### 1.3 CentOS 리눅스 설치 방법
(1) yum 패키지 업데이트 및 업그레이드
```
$ sudo yum -y update
$ sudo yum -y upgrade
```
(2) Docker 설치
```
$ sudo yum -y install docker docker-registry
```
(3) Docker 실행 및 자동실행 서비스 등록
```
$ sudo systemctl start docker.service
$ sudo systemctl enable docker.service
```
(4) 터미널에서 docker 명령어를 통해 정상적으로 설치된 것을 확인할 수 있습니다.
```
$ docker version
```
### 1.4 Windows 설치 방법
아래 링크 참고해서 설치 진행
> https://docs.docker.com/desktop/install/windows-install/