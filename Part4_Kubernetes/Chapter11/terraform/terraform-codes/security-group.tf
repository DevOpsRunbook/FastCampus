// Default SG
resource "aws_default_security_group" "test-vpc_sg_default"{

    vpc_id = aws_vpc.test-vpc.id

    ingress {
      protocol  = -1
      self      = true
      from_port = 0
      to_port   = 0
    }

    egress {
      from_port   = 0
      to_port     = 0
      protocol    = "-1"
      cidr_blocks = ["0.0.0.0/0"]
    }
}

// EKS Cluster SG
resource "aws_security_group" "test-eks_sg_controlplane" {

    vpc_id = aws_vpc.test-vpc.id
    name = "Test-EKS-SG-ControlPlane"
    description = "Communication between the control plane and worker nodegroups"

    tags = {
      "Name" = "Test-EKS-ControlPlane-SG"
    }
}

resource "aws_security_group_rule" "test-eks_sg_cluster_inbound" {

    security_group_id = aws_security_group.test-eks_sg_controlplane.id
    source_security_group_id = aws_security_group.test-eks_sg_nodes.id

    type = "ingress"
    from_port = 443
    to_port = 443
    protocol = "tcp"
    description = "Allow nodes to communicate with the cluster API Server"
}

resource "aws_security_group_rule" "test-eks_sg_cluster_outbound" {

    security_group_id = aws_security_group.test-eks_sg_controlplane.id
    source_security_group_id = aws_security_group.test-eks_sg_nodes.id

    type = "egress"
    from_port = 1025
    to_port = 65535
    protocol = "tcp"
    description = "Allow Cluster API Server to communicate with the worker nodes"
}

// EKS Worker Node SG
resource "aws_security_group" "test-eks_sg_nodes" {

    vpc_id = aws_vpc.test-vpc.id
    name = "Test-EKS-SG-NodeGroup"
    description = "Security group for worker nodes in Cluster"

    egress {
      from_port   = 0
      to_port     = 0
      protocol    = "-1"
      cidr_blocks = ["0.0.0.0/0"]
    }

    tags = {
      "Name" = "Test-EKS-NodeGroup-SG"
    }
}

resource "aws_security_group_rule" "test-eks_sg_nodes_internal" {

    security_group_id = aws_security_group.test-eks_sg_nodes.id
    source_security_group_id = aws_security_group.test-eks_sg_nodes.id

    type = "ingress"
    from_port = 0
    to_port = 65535
    protocol = "-1"
    description = "Allow nodes to communicate with each other"
}

resource "aws_security_group_rule" "test-eks_sg_nodes_inbound" {

    security_group_id = aws_security_group.test-eks_sg_nodes.id
    source_security_group_id = aws_security_group.test-eks_sg_controlplane.id

    type = "ingress"
    from_port = 1025
    to_port = 65535
    protocol = "tcp"
    description = "Allow worker Kubelets and pods to receive communication from the cluster control plane"   
}

resource "aws_security_group_rule" "test-eks_sg_nodes_ssh_inbound" {

    security_group_id = aws_security_group.test-eks_sg_nodes.id
    source_security_group_id = aws_security_group.test-eks_sg_controlplane.id

    type = "ingress"
    from_port = 443
    to_port = 443
    protocol = "tcp"
    description = "Allow ssh worker Kubelets and pods to receive communication from the cluster control plane"   
}