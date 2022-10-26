resource "aws_route_table" "test-route-table-pub-sub" {

  depends_on = [
    aws_vpc.test-vpc,
    aws_internet_gateway.test-internet-gateway
  ]

  vpc_id = aws_vpc.test-vpc.id

  route {
      cidr_block = "0.0.0.0/0"
      gateway_id = aws_internet_gateway.test-internet-gateway.id
  }

  tags = {
    Name = "test-route-table-pub-sub"
  }

}