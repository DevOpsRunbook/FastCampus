# Jenkins 관련 질의응답 모음집입니다.

Jenkins 실습시 질의주신 내용에 대한 응답 모음집입니다. 에러에 대한 해결 방법도 같이 명시하였습니다.

## 1. Jenkins에서 no basic auth credentials. 에러메시지 발생할 때 해결 방법
**Question:** no basic auth credentials. 에러메시지 발생시

**Answer:** awscli 설치후 aws configure 설정으로 해결 (수행 방식은 아래 내용 그대로 적용하시면 되십니다.)

* 해당 부분은 AWS 계정의 API Key가 Profile로 등록이 안되 발생하는 에러입니다.
* awscli를 사용하실때는 반드시 본인의 AWS 계정의 API Key로 Profile을 등록하셔야 합니다.

```
## 실패 케이스1 : AWS ECR 로그인 실패
ubuntu@ip-172-16-0-243:~$ aws ecr get-login-password --region ap-northeast-2 | docker login --username AWS --password-stdin 347881231135.dkr.ecr.ap-northeast-2.amazonaws.com/test

Command 'aws' not found, but can be installed with:

sudo apt install awscli

Error: Cannot perform an interactive login from a non TTY device

## 실패 케이스2 : AWS ECR에서 Docker Image Pull 실패 (no basic auth credentials)
ubuntu@ip-172-16-0-243:~$ docker pull 347881231135.dkr.ecr.ap-northeast-2.amazonaws.com/test:1
Error response from daemon: Head https://347881231135.dkr.ecr.ap-northeast-2.amazonaws.com/v2/test/manifests/1: no basic auth credentials

## awscli를 설치하기 위한 Ubuntu OS 패키지 정보 업데이트
ubuntu@ip-172-16-0-243:~$ sudo apt update
Hit:1 http://ap-northeast-2.ec2.archive.ubuntu.com/ubuntu bionic InRelease
Hit:2 http://ap-northeast-2.ec2.archive.ubuntu.com/ubuntu bionic-updates InRelease

.. 중략 ..

Reading state information... Done
52 packages can be upgraded. Run 'apt list --upgradable' to see them.

## awscli 설치
ubuntu@ip-172-16-0-243:~$ sudo apt install awscli
Reading package lists... Done
Building dependency tree

.. 중략 ..

Setting up python3-s3transfer (0.3.3-1ubuntu0.18.04.1) ...
Setting up awscli (1.18.69-1ubuntu0.18.04.1) ...

## aws configure 설정 (AWS 개인 API Key를 Profile로 설정)
ubuntu@ip-172-16-0-243:~$ aws configure
AWS Access Key ID [None]: <개인 AWS 계정 API Access Key>
AWS Secret Access Key [None]: <개인 AWS 계정 Secret Access Key>
Default region name [None]: ap-northeast-2
Default output format [None]: json

## AWS ECR 로그인 성공 케이스
ubuntu@ip-172-16-0-243:~$ aws ecr get-login-password --region ap-northeast-2 | docker login --username AWS --password-stdin 347881231135.dkr.ecr.ap-northeast-2.amazonaws.com/test
WARNING! Your password will be stored unencrypted in /home/ubuntu/.docker/config.json.
Configure a credential helper to remove this warning. See
https://docs.docker.com/engine/reference/commandline/login/#credentials-store

Login Succeeded

## AWS ECR에서 Docker Image Pull 성공 케이스
ubuntu@ip-172-16-0-243:~$ docker pull 347881231135.dkr.ecr.ap-northeast-2.amazonaws.com/test:1
1: Pulling from test
554879bb3004: Pull complete
Digest: sha256:14d4f50961544fdb669075c442509f194bdc4c0e344bde06e35dbd55af842a38
Status: Downloaded newer image for 347881231135.dkr.ecr.ap-northeast-2.amazonaws.com/test:1
347881231135.dkr.ecr.ap-northeast-2.amazonaws.com/test:1

## Docker Image 저장 현황 결과 정상 Pull 확인
ubuntu@ip-172-16-0-243:~$ docker images
REPOSITORY                                               TAG       IMAGE ID       CREATED      SIZE
347881231135.dkr.ecr.ap-northeast-2.amazonaws.com/test   1         2fb6fc2d97e1   4 days ago   1.24MB

ubuntu@ip-172-16-0-243:~$
```

## 2. Jenkins에서 Nexus Registry 로그인 문제 해결방법
## [상세내용링크](detail/Jenkins_Nexus-registry.md)
