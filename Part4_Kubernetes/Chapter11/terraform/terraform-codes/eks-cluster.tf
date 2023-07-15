resource "aws_eks_cluster" "test-eks-cluster" {

    depends_on = [
        aws_iam_role_policy_attachment.test-eks_iam_cluster_AmazonEKSClusterPolicy,
        aws_iam_role_policy_attachment.test-eks_iam_cluster_AmazonEKSVPCResourceController
    ]

    name = var.cluster-name
    role_arn = aws_iam_role.test-eks_iam_cluster.arn
    version = "1.26"

    vpc_config{
        security_group_ids = [aws_security_group.test-eks_sg_controlplane.id, aws_security_group.test-eks_sg_nodes.id]
        subnet_ids = flatten([aws_subnet.test-public-subnet[*].id])
        endpoint_public_access = true
        public_access_cidrs = ["0.0.0.0/0"]
    }

    tags = {
        "Name" = "TEST-EKS-CLUSTER"
    }
}