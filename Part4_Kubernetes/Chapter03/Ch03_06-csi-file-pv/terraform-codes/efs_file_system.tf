resource "aws_efs_file_system" "efs-file-system" {

  encrypted                       = "false"
  performance_mode                = "generalPurpose"
  provisioned_throughput_in_mibps = "0"
  throughput_mode                 = "bursting"
}
