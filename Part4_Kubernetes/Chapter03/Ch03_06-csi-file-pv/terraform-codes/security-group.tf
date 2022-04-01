########### EFS Security Group ###########

resource "aws_security_group" "test-sg-efs" {
  description = "Test EFS security group"

  egress {
    cidr_blocks = ["0.0.0.0/0"]
    from_port   = "0"
    protocol    = "-1"
    self        = "false"
    to_port     = "0"
  }

  ingress {
    cidr_blocks = ["0.0.0.0/0"]
    from_port   = "2049"
    protocol    = "tcp"
    self        = "false"
    to_port     = "2049"
  }

  name   = "test-sg-efs"
  vpc_id = aws_vpc.test-vpc.id
}

########### EKS Security Group ###########

resource "aws_security_group" "test-sg-eks-cluster" {
  name        = "test-sg-eks-cluster"
  description = "security_group for test-eks-cluster"
  vpc_id      = aws_vpc.test-vpc.id

  tags = {
    Name = "test-sg-eks-cluster"
  }
}

resource "aws_security_group_rule" "test-sg-eks-cluster-ingress" {

  security_group_id = aws_security_group.test-sg-eks-cluster.id
  type              = "ingress"
  description       = "ingress security_group_rule for test-eks-cluster"
  from_port         = 0
  to_port           = 0
  protocol          = "-1"
  cidr_blocks       = ["0.0.0.0/0"]
}

resource "aws_security_group_rule" "test-sg-eks-cluster-egress" {

  security_group_id = aws_security_group.test-sg-eks-cluster.id
  type              = "egress"
  description       = "egress security_group_rule for test-eks-cluster"
  from_port         = 0
  to_port           = 0
  protocol          = "-1"
  cidr_blocks       = ["0.0.0.0/0"]
}

########### Bastion (EC2 Instance) Security Group ###########

resource "aws_security_group" "test-sg-bastion" {

  name   = "test-sg-bastion"
  vpc_id = aws_vpc.test-vpc.id

  ingress {
    cidr_blocks = ["0.0.0.0/0"]
    description = "ingress security_group_rule for bastion"
    from_port   = "22"
    protocol    = "tcp"
    self        = "false"
    to_port     = "22"
  }

  egress {
    cidr_blocks = ["0.0.0.0/0"]
    description = "egress security_group_rule for bastion"
    from_port   = "0"
    protocol    = "tcp"
    self        = "false"
    to_port     = "65535"
  }

  tags = {
    Name = "test-sg-bastion"
  }
}
