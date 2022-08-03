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