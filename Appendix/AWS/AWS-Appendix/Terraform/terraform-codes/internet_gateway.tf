resource "aws_internet_gateway" "test-internet-gateway" {

  depends_on = [
    aws_vpc.test-vpc
  ]

  vpc_id = aws_vpc.test-vpc.id

  tags = {
    Name = "test-internet-gateway"
  }
}
