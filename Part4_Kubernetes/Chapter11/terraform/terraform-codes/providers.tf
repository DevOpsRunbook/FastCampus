terraform {
  
  required_providers {
    aws = {
      source = "hashicorp/aws"
      version = "4.22.0"
    }
  }

  backend "s3" {
    bucket = "<Project Name>-s3-tf-state"
    key = "terraform.tfstate"
    region = "ap-northeast-2"
    dynamodb_table = "<Project Name>-ddb-lock-table"
    encrypt = "true"
  }
}

provider "aws" {
  region = var.aws_region
}