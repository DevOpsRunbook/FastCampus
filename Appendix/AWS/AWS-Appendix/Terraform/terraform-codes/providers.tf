terraform {
  
  required_providers {
    aws = {
      source = "hashicorp/aws"
      version = "4.22.0"
    }
  }

  backend "s3" {
    bucket = "test-s3-tf-state-fastcampus-bucket"
    key = "terraform.tfstate"
    region = "ap-northeast-2"
    dynamodb_table = "test-ddb-tf-lock-fastcampus-table"
    encrypt = "true"
  }
}

provider "aws" {
  region = var.aws_region
}