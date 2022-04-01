
########## Public Subnet Route Tables ########## 

resource "aws_route_table" "test-route-table-pub-sub1" {

  depends_on = [
    aws_vpc.test-vpc,
    aws_internet_gateway.test-internet-gateway
  ]

  route {
    cidr_block = "0.0.0.0/0"
    gateway_id = aws_internet_gateway.test-internet-gateway.id
  }

  tags = {
    Name = "test-route-table-pub-sub1"
  }

  tags_all = {
    Name = "test-route-table-pub-sub1"
  }

  vpc_id = aws_vpc.test-vpc.id
}

resource "aws_route_table" "test-route-table-pub-sub3" {

  depends_on = [
    aws_vpc.test-vpc,
    aws_internet_gateway.test-internet-gateway
  ]

  route {
    cidr_block = "0.0.0.0/0"
    gateway_id = aws_internet_gateway.test-internet-gateway.id
  }

  tags = {
    Name = "test-route-table-pub-sub3"
  }

  tags_all = {
    Name = "test-route-table-pub-sub3"
  }

  vpc_id = aws_vpc.test-vpc.id
}


########## Private Subnet Route Tables ########## 

resource "aws_route_table" "test-route-table-pri-sub1" {

  depends_on = [
    aws_vpc.test-vpc,
    aws_nat_gateway.test-nat-gateway
  ]

  route {
    cidr_block = "0.0.0.0/0"
    nat_gateway_id = aws_nat_gateway.test-nat-gateway.id
  }

  tags = {
    Name = "test-route-table-pri-sub1"
  }

  tags_all = {
    Name = "test-route-table-pri-sub1"
  }

  vpc_id = aws_vpc.test-vpc.id
}

resource "aws_route_table" "test-route-table-pri-sub3" {

  depends_on = [
    aws_vpc.test-vpc,
    aws_nat_gateway.test-nat-gateway
  ]

  route {
    cidr_block = "0.0.0.0/0"
    nat_gateway_id = aws_nat_gateway.test-nat-gateway.id
  }

  tags = {
    Name = "test-route-table-pri-sub3"
  }

  tags_all = {
    Name = "test-route-table-pri-sub3"
  }

  vpc_id = aws_vpc.test-vpc.id
}
