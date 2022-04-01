# ########### EFS IAM Policy ###########

resource "aws_iam_policy" "test-efs-csi-iam-policy" {
  name = "test-efs-csi-iam-policy"
  path = "/"
  policy = <<POLICY
{
  "Statement": [
    {
      "Action": [
        "elasticfilesystem:DescribeAccessPoints",
        "elasticfilesystem:DescribeFileSystems"
      ],
      "Effect": "Allow",
      "Resource": "*"
    },
    {
      "Action": [
        "elasticfilesystem:CreateAccessPoint"
      ],
      "Condition": {
        "StringLike": {
          "aws:RequestTag/efs.csi.aws.com/cluster": "true"
        }
      },
      "Effect": "Allow",
      "Resource": "*"
    },
    {
      "Action": "elasticfilesystem:DeleteAccessPoint",
      "Condition": {
        "StringEquals": {
          "aws:ResourceTag/efs.csi.aws.com/cluster": "true"
        }
      },
      "Effect": "Allow",
      "Resource": "*"
    }
  ],
  "Version": "2012-10-17"
}
POLICY
}
