resource "aws_s3_bucket" "test-s3-tf-state" {

  bucket = "<Project Name>-s3-tf-state"

  tags = {
    "Name" = "<Project Name>-s3-tf-state"
  }
  
}

resource "aws_dynamodb_table" "test-ddb-lock-table" {

  depends_on   = [aws_s3_bucket.test-s3-tf-state]
  name         = "<Project Name>-ddb-lock-table"
  billing_mode = "PAY_PER_REQUEST"
  hash_key     = "LockID"

  attribute {
    name = "LockID"
    type = "S"
  }

  tags = {
    "Name" = "<Project Name>-ddb-lock-table"
  }

}