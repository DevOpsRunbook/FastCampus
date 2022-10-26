resource "aws_subnet" "test-public-subnet" {

  depends_on = [
    aws_vpc.test-vpc
  ]

  count = length(var.aws_vpc_public_subnets)
  vpc_id = aws_vpc.test-vpc.id
  cidr_block = var.aws_vpc_public_subnets[count.index]
  availability_zone = var.aws_azs[count.index]
  map_public_ip_on_launch = true

  tags = {
    Name                                     = "test-public-subnet${count.index+1}"
    "kubernetes.io/cluster/test-eks-cluster" = "shared"
    "kubernetes.io/role/elb"                 = 1
  }

}