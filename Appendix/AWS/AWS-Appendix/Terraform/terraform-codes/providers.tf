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