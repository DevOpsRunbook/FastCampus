
########## Public Subnet Route Tables Association ########## 

resource "aws_route_table_association" "test-route-association-pub-sub1" {
  route_table_id = aws_route_table.test-route-table-pub-sub1.id
  subnet_id      = aws_subnet.test-public-subnet1.id
}

resource "aws_route_table_association" "test-route-association-pub-sub3" {
  route_table_id = aws_route_table.test-route-table-pub-sub3.id
  subnet_id      = aws_subnet.test-public-subnet3.id
}

########## Private Subnet Route Tables Association ########## 

resource "aws_route_table_association" "test-route-association-pri-sub1" {
  route_table_id = aws_route_table.test-route-table-pri-sub1.id
  subnet_id      = aws_subnet.test-private-subnet1.id
}

resource "aws_route_table_association" "test-route-association-pri-sub3" {
  route_table_id = aws_route_table.test-route-table-pri-sub3.id
  subnet_id      = aws_subnet.test-private-subnet3.id
}

