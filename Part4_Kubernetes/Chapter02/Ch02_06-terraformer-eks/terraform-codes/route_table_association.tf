resource "aws_route_table_association" "tfer--subnet-02ac5467e22834a7f" {
  route_table_id = aws_route_table.tfer--rtb-0838eb612056c8102.id
  subnet_id      = "<subnet_id>"
}

resource "aws_route_table_association" "tfer--subnet-06563c9c5cdc9cd0e" {
  route_table_id = aws_route_table.tfer--rtb-0493160681ebac586.id
  subnet_id      = "<subnet_id>"
}
