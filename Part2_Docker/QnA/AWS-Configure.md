# Linux 실습 환경에서 AWS 연동을 위한 설정 방법
## 1. 개요
* Linux 실습 환경에서 AWS 연동을 위해서는 AWS에 접근가능한 Access Key 및 Secret Key를 AWS IAM User에서 발급해야한다.
* 발급한 Key는 Linux 환경에서 AWS에 CLI 및 API 사용이 가능한 awscli 설치뒤, 설정을 진행해야한다.

## 2. 사전 준비사항
* AWS 가입 및 계정이 발급되어 있어야 한다.

## 3. AWS에서 실습 계정의 Access Key 및 Secret Key 발급 방법
* AWS의 실습 계정을 로그인후 다음과 같은 절차로 진행한다.
  
(1) AWS Management Console 로그인 후, 검색창에서 IAM 검색
![aws-iam-search](https://devopsrunbook-fastcampus.s3.ap-northeast-2.amazonaws.com/FastCampus/Part2_Docker/Chapter02/3-aws-ecr/aws-iam-search.png)

(2) AWS IAM의 메인 화면에서, 좌측 메뉴중 "사용자" 클릭
![iam-main](https://devopsrunbook-fastcampus.s3.ap-northeast-2.amazonaws.com/FastCampus/Part2_Docker/Chapter02/3-aws-ecr/iam-main.png)

(3) User 목록에서 Key를 생성할 User명 링크를 클릭한다.
![user-list](https://devopsrunbook-fastcampus.s3.ap-northeast-2.amazonaws.com/FastCampus/Part2_Docker/Chapter02/3-aws-ecr/user-list.png)

(4) User 상세 정보중에 중간 탭중 4번째 "보안 자격 증명"을 클릭한다.
![user-detail-main](https://devopsrunbook-fastcampus.s3.ap-northeast-2.amazonaws.com/FastCampus/Part2_Docker/Chapter02/3-aws-ecr/user-detail-main.png)

(5) User 보안 자격 증명 화면에서, 액세스 키 > "액세스 키 만들기" 버튼을 클릭한다.
![user-sec-cert](https://devopsrunbook-fastcampus.s3.ap-northeast-2.amazonaws.com/FastCampus/Part2_Docker/Chapter02/3-aws-ecr/user-sec-cert.png)

(6) User의 Access Key 및 Secret Key가 발급되면 확인하고, accessKeys.csv 파일을 다운로드 받는다.
![aws-iam-create-access-key](https://devopsrunbook-fastcampus.s3.ap-northeast-2.amazonaws.com/FastCampus/Part2_Docker/Chapter02/3-aws-ecr/aws-iam-create-access-key.png)

(7) 다운로드 받은 accessKey.csv 파일의 내용을 보면 다음과 같다.
![aws-access-key](https://devopsrunbook-fastcampus.s3.ap-northeast-2.amazonaws.com/FastCampus/Part2_Docker/Chapter02/3-aws-ecr/aws-access-key.png)

## 4. Linux 서버에서 AWS 연결 및 API 사용을 위한 awscli 설치 및 Access Key, Secret Key 설정
* Linux 서버에서 awscli 설치 및 Access Key, Secret Key 설정 방법은 다음과 같다.

(1) Linux 서버에 awscli가 있는지 확인한다. 현재 Linux 서버에는 awscli가 없으면 다음과 같은 에러 화면이 출력된다.
![awscli-not-install](https://devopsrunbook-fastcampus.s3.ap-northeast-2.amazonaws.com/FastCampus/Part2_Docker/Chapter02/3-aws-ecr/awscli-not-install.png)

(2) Ubuntu Linux 서버의 경우, root 계정 혹은 권한으로 다음의 명령어를 통해 OS 패키지 정보를 업데이트 한다.
![apt-update](https://devopsrunbook-fastcampus.s3.ap-northeast-2.amazonaws.com/FastCampus/Part2_Docker/Chapter02/3-aws-ecr/apt-update.png)

(3) awscli를 설치한다.
![awscli-installing](https://devopsrunbook-fastcampus.s3.ap-northeast-2.amazonaws.com/FastCampus/Part2_Docker/Chapter02/3-aws-ecr/awscli-installing.png)

(4) 설치후, 2번에서 발급 받은 Access Key, Secret Key를 설정한다.
![aws-configure](https://devopsrunbook-fastcampus.s3.ap-northeast-2.amazonaws.com/FastCampus/Part2_Docker/Chapter02/3-aws-ecr/aws-configure.png)





