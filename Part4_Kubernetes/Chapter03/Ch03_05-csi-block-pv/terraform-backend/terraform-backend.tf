resource "aws_s3_bucket" "test-s3-tf-state" {

  bucket = "<Terraform Backend용 S3 Bucket명>"

  tags = {
    "Name" = "<Terraform Backend용 S3 Bucket명>"
  }
  
}

resource "aws_dynamodb_table" "test-ddb-tf-lock" {

  depends_on   = [aws_s3_bucket.test-s3-tf-state]
  name         = "<Terraform Backend용 DynamoDB Table명>"
  billing_mode = "PAY_PER_REQUEST"
  hash_key     = "LockID"

  attribute {
    name = "LockID"
    type = "S"
  }

  tags = {
    "Name" = "<Terraform Backend용 DynamoDB Table명>"
  }

}