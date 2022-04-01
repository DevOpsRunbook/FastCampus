resource "aws_vpc" "tfer--vpc-0548910d6079510e6" {
  assign_generated_ipv6_cidr_block = "false"
  cidr_block                       = "172.31.0.0/16"
  enable_classiclink               = "false"
  enable_classiclink_dns_support   = "false"
  enable_dns_hostnames             = "true"
  enable_dns_support               = "true"
  instance_tenancy                 = "default"
  # ipv6_netmask_length              = "0"

  tags = {
    Name = "vpc"
  }

  tags_all = {
    Name = "vpc"
  }
}
