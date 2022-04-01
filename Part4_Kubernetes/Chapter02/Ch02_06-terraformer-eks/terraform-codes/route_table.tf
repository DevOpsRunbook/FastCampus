resource "aws_route_table" "tfer--rtb-0493160681ebac586" {
  route {
    cidr_block = "0.0.0.0/0"
    gateway_id = "<Internet Gateway ID>"
  }

  tags = {
    Name = "public-subnet3-routing"
  }

  tags_all = {
    Name = "public-subnet3-routing"
  }

  vpc_id = "<vpc_id>"
}

resource "aws_route_table" "tfer--rtb-0838eb612056c8102" {
  route {
    cidr_block = "0.0.0.0/0"
    gateway_id = "<Internet Gateway ID>"
  }

  tags = {
    Name = "public-subnet1-routing"
  }

  tags_all = {
    Name = "public-subnet1-routing"
  }

  vpc_id = "<vpc_id>"
}

resource "aws_route_table" "tfer--rtb-09544a875edc4d02f" {
  vpc_id = "<vpc_id>"
}
