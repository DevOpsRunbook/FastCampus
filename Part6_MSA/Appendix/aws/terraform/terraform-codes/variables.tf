variable "aws_region" {
  default = "ap-northeast-2"
  type = string 
}

variable "cluster-name" {
  default = "test-eks-cluster"
  type    = string
}

variable "aws_vpc_cidr_block" {
    default = "172.31.0.0/16"
    type = string
}

variable "aws_vpc_public_subnets" {
    default = ["172.31.0.0/20", "172.31.16.0/20"]
    type = list(string)
}

variable "aws_azs"  {
    default = ["ap-northeast-2a", "ap-northeast-2c"]
    type = list(string)
}