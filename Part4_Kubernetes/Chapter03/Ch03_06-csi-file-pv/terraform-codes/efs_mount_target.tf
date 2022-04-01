resource "aws_efs_mount_target" "test-efs-mount-target1" {

  file_system_id  = aws_efs_file_system.efs-file-system.id
  security_groups = [aws_security_group.test-sg-efs.id]
  subnet_id       = aws_subnet.test-private-subnet1.id
}

resource "aws_efs_mount_target" "test-efs-mount-target3" {

  file_system_id  = aws_efs_file_system.efs-file-system.id
  security_groups = [aws_security_group.test-sg-efs.id]
  subnet_id       = aws_subnet.test-private-subnet3.id
}