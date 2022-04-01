resource "aws_eip" "test-elastic-ip" {
  network_border_group = "ap-northeast-2"
  public_ipv4_pool     = "amazon"

  tags = {
    Name        = "test-elastic-ip"
  }

  tags_all = {
    Name        = "test-elastic-ip"
  }

  vpc = true
}
