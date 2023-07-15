terraform {
  required_version = ">= 1.0"

  backend "s3" {
    bucket = "<Test Name>-s3-tf-state"
    key = "terraform.tfstate"
    region = "ap-northeast-2"
    dynamodb_table = "<Test Name>-ddb-tf-lock"
    encrypt = "true"
  }
}

provider "aws" {
  region = var.aws_region
}