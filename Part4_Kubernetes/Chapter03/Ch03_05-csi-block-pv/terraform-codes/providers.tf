terraform {
  required_version = ">= 1.0"

  backend "s3" {
    bucket = "<Terraform Backend용 S3 Bucket명>"
    key = "terraform.tfstate"
    region = "ap-northeast-2"
    dynamodb_table = "<Terraform Backend용 DynamoDB Table명>"
    encrypt = "true"
  }
}

provider "aws" {
  region = var.aws_region
}