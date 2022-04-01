resource "aws_nat_gateway" "test-nat-gateway" {

  depends_on = [
    aws_eip.test-elastic-ip
  ]

  allocation_id     = aws_eip.test-elastic-ip.id
  subnet_id         = aws_subnet.test-public-subnet3.id
  connectivity_type = "public"

  tags = {
    Name        = "test-nat-gateway"
  }

  tags_all = {
    Name        = "test-nat-gateway"
  }

}
