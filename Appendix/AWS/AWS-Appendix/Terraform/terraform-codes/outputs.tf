output "cluster_name" {
  value = aws_eks_cluster.test-eks-cluster.name
}

output "cluster_endpoint" {
  value = aws_eks_cluster.test-eks-cluster.endpoint
}