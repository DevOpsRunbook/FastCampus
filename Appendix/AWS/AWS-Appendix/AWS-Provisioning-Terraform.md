# AWS 클라우드 환경 구성
## 1. AWS CLI 및 TERRAFORM 설치
### 1.1 AWS CLI 설치
(1) MacOS
https://docs.aws.amazon.com/ko_kr/cli/latest/userguide/install-cliv2-mac.html
1. macOS pkg 파일을 다운로드 합니다.
> https://awscli.amazonaws.com/AWSCLIV2.pkg
2. 다운로드한 파일을 두 번 클릭하여 설치 관리자를 시작합니다.
3. 설정 및 버전을 확인합니다.
```
$ which aws
/usr/local/bin/aws

$ aws --version
aws-cli/2.0.34 ...
```
(2) Linux
* https://docs.aws.amazon.com/ko_kr/cli/latest/userguide/install-cliv2-linux.html#cliv2-linux-install
```
# Linux x86 (64-bit)
curl "https://awscli.amazonaws.com/awscli-exe-linux-x86_64.zip" -o "awscliv2.zip"
unzip awscliv2.zip
sudo ./aws/install
```
(3) Windows
https://docs.aws.amazon.com/ko_kr/cli/latest/userguide/install-cliv2-windows.html
1. Windows용 AWS CLI MSI 설치 관리자를 다운로드합니다.
> https://awscli.amazonaws.com/AWSCLIV2.msi
2. 기본적으로 AWS CLI는 C:\Program Files\Amazon\AWSCLIV2에 설치됩니다.
3. 버전을 확인합니다.
```
C:\> aws --version
aws-cli/2.0.23 Python/3.7.4 Windows/10 botocore/2.0.0
```
### 1.2 Terraform 설치
테라폼은 Infrastructure as Code를 구현할 수 있는 대표적인 오픈소스 툴입니다.
(1) URL로 특정 버전 설치
* 최신 버전 테라폼 : https://www.terraform.io/downloads.html
* Mac 유저는 darwin_amd64.zip 가 붙은 파일을 다운로드 하시면 됩니다.
* 설치한 Binary 파일을 /usr/local/bin 으로 옮기면 terraform 명령어를 사용하실 수 있습니다.
```
$ curl -sO https://releases.hashicorp.com/terraform/0.12.24/terraform_0.12.24_darwin_amd64.zip
$ unzip terraform_0.12.24_darwin_amd64.zip
$ mv terraform /usr/local/bin
$ terraform --version
Terraform v0.12.24
```
(2) Homebrew로 설치
Homebrew를 사용하면 손쉽게 최신 버전을 다운 받으실 수 있습니다.
```
$ brew install terraform
$ terraform --version
Terraform v0.12.24
```
## 2. AWS CONFIGURE 구성
### 2.1 AWS Configure 세팅
(1) AWSCLI 설치 링크
> https://docs.aws.amazon.com/ko_kr/cli/latest/userguide/cli-chap-install.html
```
$ aws configure
AWS Access Key ID [None]: <액세스 키 ID>
AWS Secret Access Key [None]: <비밀 액세스 키>
Default region name [ap-northeast-2]: ap-northeast-2
Default output format [json]:
```
(2) AWS Configure 세팅
세팅이 완료된 경우에는 cat ~/.aws/credentials 의 Default쪽에 설정되어 있는지 확인합니다.
```
$ cat ~/.aws/credentials
[default]
aws_access_key_id = ABCDEFXXXXXXX
aws_secret_access_key = KSdifi...
```
(3) 현 사용자 확인
현재 설정된 사용자가 누구인지 확인하려면 아래의 명령어를 통해 확인하실 수 있습니다.
```
$ aws sts get-caller-identity
{
  "UserId": "XXXXX",
  "Account": "1234567891011",
  "Arn": "arn:aws:iam::123456781011:user/terraform-101"
}
```
## 3. Terraform Backend 구성
### 3.1 Terraform Backend 소개
* Terraform “Backend” 는 Terraform의 state file을 어디에 저장을 하고, 가져올지에 대한 설정입니다. 
* 기본적으로는는 로컬 스토리지에 저장을 하지만, 설정에 따라서 s3, consul, etcd 등 다양한 “Backend type“을 사용할 수 있습니다.
* Terraform에서 가장 보편적으로 사용하는 s3 backend 를 예제로 합니다. AWS S3는 쉽게 구축할 수 있으며 versioning 을 지원하는 안전한 저장소입니다.
### 3.2 Terraform Backend의 특징
(1) S3 bucket as backend
* 로컬 스토리지에 저장한다는건 유실할 수 있다는 가능성을 내포합니다. 테라폼의 상태를 저장하기 위해 S3 버킷을 생성합니다. AWS S3는 쉽게 구축할 수 있으며 versioning 을 지원하는 안전한 저장소입니다.
(2) DynamoDB Table for Lock
* Locking: 보통 Terraform 코드를 혼자 작성하지 않습니다. 인프라를 변경한다는 것은 굉장히 민감한 작업이 될 수 있습니다. 동시에 같은 파일을 수정하지 못하도록 하기 위해 DynamoDB에 작업에 대한 Lock을 생성합니다.
### 3.3 Terraform Backend 실행
(1) 다음의 코드를 생성할 내역에 알맞게 수정
* 경로 : 현재 동일 경로에서 terraform-backend 디렉토리 > terraform-backend.tf 파일
* <> 괄호안에 있는 내용을 임의의 값으로 수정
```
resource "aws_s3_bucket" "test-s3-tf-state" {

  bucket = "<생성할 Terraform Backend 버킷명>"

  tags = {
    "Name" = "<생성할 Terraform Backend 버킷명>"
  }
  
}

resource "aws_dynamodb_table" "test-ddb-tf-lock" {

  depends_on   = [aws_s3_bucket.test-s3-tf-state]
  name         = "<생성할 Terraform Backend 테이블명>"
  billing_mode = "PAY_PER_REQUEST"
  hash_key     = "LockID"

  attribute {
    name = "LockID"
    type = "S"
  }

  tags = {
    "Name" = "<생성할 Terraform Backend 테이블명>"
  }

}
```
(2) 해당 Terraform Code가 있는 경로에서 다음의 명령어를 순차적으로 수행
```
$ terraform init
$ terraform plan
$ terraform apply
```
(3) AWS Management Console에서 Terraform Backend용 S3 Bucket과 DynamoDB Table이 정상 생성되었는지 확인
## 4. AWS 클라우드 내 VPC 네트워크 및 EKS Cluster 프로비저닝
(1) 다음의 코드를 생성할 내역에 알맞게 수정
* 경로 : 현재 동일 경로에서 terraform-codes 디렉토리 > providers.tf 파일
* <> 괄호안에 있는 내용을 임의의 값으로 수정
```
terraform {
  
  required_providers {
    aws = {
      source = "hashicorp/aws"
      version = "4.22.0"
    }
  }

  backend "s3" {
    bucket = "<생성할 Terraform Backend 버킷명>"
    key = "terraform.tfstate"
    region = "ap-northeast-2"
    dynamodb_table = "<생성할 Terraform Backend 테이블명>"
    encrypt = "true"
  }
}

provider "aws" {
  region = var.aws_region
}
```
(2) 해당 Terraform Code가 있는 경로에서 다음의 명령어를 순차적으로 수행
```
$ terraform init
$ terraform plan
$ terraform apply
```
(3) AWS Management Console에서 다음의 Resource들이 정상 생성되었는지 확인
* VPC 
> VPC, Subnet, Internet Gateway, Route Table, Security Group
* EKS(Kubernetes)
> EKS Cluster, EKS Node Group, IAM Role