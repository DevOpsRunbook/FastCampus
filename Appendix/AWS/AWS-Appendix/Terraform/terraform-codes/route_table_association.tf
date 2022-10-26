resource "aws_route_table_association" "test-route-association-pub-sub" {

    count = length(var.aws_vpc_public_subnets)
    subnet_id = aws_subnet.test-public-subnet[count.index].id
    route_table_id = aws_route_table.test-route-table-pub-sub.id
    
}
