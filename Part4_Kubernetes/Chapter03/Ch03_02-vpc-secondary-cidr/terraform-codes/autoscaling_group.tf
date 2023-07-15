#resource "aws_autoscaling_group" "test-autoscaling-group" {
#  # availability_zones        = ["ap-northeast-2a", "ap-northeast-2c"]
#  capacity_rebalance        = "true"
#  default_cooldown          = "300"
#  desired_capacity          = "1"
#  force_delete              = "false"
#  health_check_grace_period = "15"
#  health_check_type         = "EC2"
#  max_instance_lifetime     = "0"
#  max_size                  = "1"
#  metrics_granularity       = "1Minute"
#  min_size                  = "1"
#
#  mixed_instances_policy {
#    instances_distribution {
#      on_demand_allocation_strategy            = "prioritized"
#      on_demand_base_capacity                  = "0"
#      on_demand_percentage_above_base_capacity = "100"
#      spot_allocation_strategy                 = "lowest-price"
#      spot_instance_pools                      = "2"
#    }
#
#    launch_template {
#      launch_template_specification {
#        launch_template_id   = "<launch_template_id>"
#        launch_template_name = "<launch_template_name>"
#        version              = "1"
#      }
#
#      override {
#        instance_type = "t3a.medium"
#      }
#    }
#  }
#
#  name                    = "<name>"
#  protect_from_scale_in   = "false"
#  service_linked_role_arn = "<service_linked_role_arn>"
#
#  tag {
#    key                 = "eks:cluster-name"
#    propagate_at_launch = "true"
#    value               = "test-eks-cluster"
#  }
#
#  tag {
#    key                 = "kubernetes.io/cluster/test-eks-cluster"
#    propagate_at_launch = "true"
#    value               = "owned"
#  }
#
#  tag {
#    key                 = "eks:nodegroup-name"
#    propagate_at_launch = "true"
#    value               = "test-eks-nodegroup"
#  }
#
#  tag {
#    key                 = "k8s.io/cluster-autoscaler/test-eks-cluster"
#    propagate_at_launch = "true"
#    value               = "owned"
#  }
#
#  tag {
#    key                 = "k8s.io/cluster-autoscaler/enabled"
#    propagate_at_launch = "true"
#    value               = "true"
#  }
#
#  termination_policies      = ["AllocationStrategy", "OldestLaunchTemplate", "OldestInstance"]
#  vpc_zone_identifier       = ["<Subnet ID 1>", "<Subnet ID 2>"]
#  wait_for_capacity_timeout = "10m"
#}
