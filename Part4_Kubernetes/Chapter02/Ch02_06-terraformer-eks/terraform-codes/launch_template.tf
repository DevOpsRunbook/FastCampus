#resource "aws_launch_template" "tfer--eks-7ebf8675-fb6f-d4e0-8ee6-d035b5f8e765" {
#  block_device_mappings {
#    device_name = "/dev/xvda"
#
#    ebs {
#      delete_on_termination = "true"
#      iops                  = "0"
#      # throughput            = "0"
#      volume_size           = "20"
#      volume_type           = "gp2"
#    }
#  }
#
#  default_version         = "1"
#  disable_api_termination = "false"
#
#  iam_instance_profile {
#    name = "<iam_instance_profile_name>"
#  }
#
#  image_id      = "ami-056d8657d1f2fbc85"
#  instance_type = "t3a.medium"
#
#  metadata_options {
#    http_endpoint = "enabled"
#    http_put_response_hop_limit = "2"
#  }
#
#  name = "<aws_launch_template_name>"
#
#  network_interfaces {
#    device_index       = "0"
#    ipv4_address_count = "0"
#    ipv6_address_count = "0"
#    network_card_index = "0"
#    security_groups    = ["sg-0a7d892dc5718628d"]
#  }
#
#  tags = {
#    "eks:cluster-name"   = "test-eks-cluster"
#    "eks:nodegroup-name" = "test-eks-nodegroup"
#  }
#
#  tags_all = {
#    "eks:cluster-name"   = "test-eks-cluster"
#    "eks:nodegroup-name" = "test-eks-nodegroup"
#  }
#
#  user_data = "TUlNRS1WZXJzaW9uOiAxLjAKQ29udGVudC1UeXBlOiBtdWx0aXBhcnQvbWl4ZWQ7IGJvdW5kYXJ5PSIvLyIKCi0tLy8KQ29udGVudC1UeXBlOiB0ZXh0L3gtc2hlbGxzY3JpcHQ7IGNoYXJzZXQ9InVzLWFzY2lpIgojIS9iaW4vYmFzaApzZXQgLWV4CkI2NF9DTFVTVEVSX0NBPUxTMHRMUzFDUlVkSlRpQkRSVkpVU1VaSlEwRlVSUzB0TFMwdENrMUpTVU0xZWtORFFXTXJaMEYzU1VKQlowbENRVVJCVGtKbmEzRm9hMmxIT1hjd1FrRlJjMFpCUkVGV1RWSk5kMFZSV1VSV1VWRkVSWGR3Y21SWFNtd0tZMjAxYkdSSFZucE5RalJZUkZSSmVVMUVTWGhQUkVWNVRsUmpkMDlXYjFoRVZFMTVUVVJKZUU1cVJYbE9WR04zVDFadmQwWlVSVlJOUWtWSFFURlZSUXBCZUUxTFlUTldhVnBZU25WYVdGSnNZM3BEUTBGVFNYZEVVVmxLUzI5YVNXaDJZMDVCVVVWQ1FsRkJSR2RuUlZCQlJFTkRRVkZ2UTJkblJVSkJVRVl6Q210YVRXeExaMjh6U1d4NVEwTjNRazR2ZW1od2JVODBjakV5ZDJOWVVsQXpXRmxETkVsNGJrbEZhbTAyTVc1S1puQlBNbXc1V0M4dmFIbDRjbEJvUW00S1ZYbG5VamxLWkhGMFVsQjNLM2swYVZwcU1ETTVNRWN6WXpGWVVFODJiVVZqYkRad2JGSlBORk53V0docmVGTXhZVUZIWWsxQlNsSjRWMmt2YmtGSUt3cHJabGt5V0hKa1JGWk9TMUpDY2xsc2JHRmxiRkJtWmpBdmFtaHJXbW95V201eVFsWXpibUZyYlZscmN6RjBlVU5HVTJGak0wOWxkV1kzUlRKbk5ISkZDbTV3ZVhWWWFTdHpRV1JZVUhCVmVuRjBja3BWVFRFckwwcFhVRXN4VlVSSloyUmpVREY0Wlc0MVRGcEVjRVJSVGtKelJ6Vk9iMEZOUzNvMlpXMXFXR0lLWm5aMEsyZHFaVWcwWm5kc1JrNVlZakp6TDJsNUt6RmpTVTltTUVVdlYzcGhRVXRSY1d0TEszWnRibVJCVHpKU1dWTnFUblYyUlhnd2JqTmFhVXh4V1FwT2FHOXRSVE1yV0dWNGVFUnhWbTV4WTBoTlEwRjNSVUZCWVU1RFRVVkJkMFJuV1VSV1VqQlFRVkZJTDBKQlVVUkJaMHRyVFVFNFIwRXhWV1JGZDBWQ0NpOTNVVVpOUVUxQ1FXWTRkMGhSV1VSV1VqQlBRa0paUlVaRFZ6RnVaRkZNUTNOVWNUWnJkbmMxWlhwQ1UwMVJZbEkxUTNsTlFUQkhRMU54UjFOSllqTUtSRkZGUWtOM1ZVRkJORWxDUVZGQ1dWbG5RMUo2UjNWTU1XTlRha28xUTFsMEsxSnlTbHA0VlVWV1pXNTBWRmRUYkRJdlJYWjVRV28zVW1sSU4ycDVUUXBxVjIwdlNsWkhVRm9yYW1obGNEWmlVM041VVdSNFdIYzRWVE14TWxCRWFsWnpZbmhVSzNvM2JYSmlSR05JWWs1T2JqQnZVbEJWTkRWc1NEWjJabGRDQ2xsQ1V6RnpSakpOT1dVM01tdDRTREpFY0ZOMGFtRTBOMVpNZFhSRFZqTlFhQ3RqVjB4c1IyZFJiQzh2VmtjelRWTm5aemRKTjFFMmNuSnFWM2gyV0djS09HMDNWR1Z4ZERaRFEyeElkbXMwTTJZcldXMVNNM1pVTDNVcldWQXdWMFZxVVZwU1luRTJSRnBJTUROQ1QwUkRXVWhqYWs5M1VWaG5kMmRIWjFWbVF3cG5jbnBQTkZneVNEWlBXVmRDVDNoQmFFRjZVMjlCYVVWTGNVaGhMMHN3YzB4aWMzZExSRGhUYWpKWEwycFJWVlozTjBORWVFZzFVR3cwVVdkTGJsTkhDakpvWVRSdFUwbDNNRUpXZDAxYVprNWxLMVY2Tmxkb2NERkZNV281Tkc1d1NsRklXQW90TFMwdExVVk9SQ0JEUlZKVVNVWkpRMEZVUlMwdExTMHRDZz09CkFQSV9TRVJWRVJfVVJMPWh0dHBzOi8vMEI2NzVDNUQ2NUYxNTQ5MkJGRTQzRTU1RkUxNDg3MzEueWw0LmFwLW5vcnRoZWFzdC0yLmVrcy5hbWF6b25hd3MuY29tCks4U19DTFVTVEVSX0ROU19JUD0xMC4xMDAuMC4xMAovZXRjL2Vrcy9ib290c3RyYXAuc2ggdGVzdC1la3MtY2x1c3RlciAtLWt1YmVsZXQtZXh0cmEtYXJncyAnLS1ub2RlLWxhYmVscz1la3MuYW1hem9uYXdzLmNvbS9ub2RlZ3JvdXAtaW1hZ2U9YW1pLTA1NmQ4NjU3ZDFmMmZiYzg1LGVrcy5hbWF6b25hd3MuY29tL2NhcGFjaXR5VHlwZT1PTl9ERU1BTkQsZWtzLmFtYXpvbmF3cy5jb20vbm9kZWdyb3VwPXRlc3QtZWtzLW5vZGVncm91cCxyb2xlPWVrcy1ub2RlZ3JvdXAgLS1tYXgtcG9kcz0xNycgLS1iNjQtY2x1c3Rlci1jYSAkQjY0X0NMVVNURVJfQ0EgLS1hcGlzZXJ2ZXItZW5kcG9pbnQgJEFQSV9TRVJWRVJfVVJMIC0tZG5zLWNsdXN0ZXItaXAgJEs4U19DTFVTVEVSX0ROU19JUCAtLXVzZS1tYXgtcG9kcyBmYWxzZQoKLS0vLy0t"
#}
#
#resource "aws_launch_template" "test-launch-template" {
#
#  name = "test-launch-template"
#
#  block_device_mappings {
#    device_name = "/dev/xvda"
#
#    ebs {
#      delete_on_termination = "true"
#      iops                  = "0"
#      # throughput            = "0"
#      volume_size           = "20"
#      volume_type           = "gp2"
#    }
#  }
#
#  default_version         = "1"
#  disable_api_termination = "false"
#
#  # iam_instance_profile {
#  #   name = "<iam_instance_profile_name>"
#  # }
#
#  image_id      = "ami-056d8657d1f2fbc85"
#  instance_type = "t3a.medium"
#
#  metadata_options {
#    http_endpoint = "enabled"
#    http_put_response_hop_limit = "2"
#  }
#
#
#
#  network_interfaces {
#    device_index       = "0"
#    ipv4_address_count = "0"
#    ipv6_address_count = "0"
#    network_card_index = "0"
#    security_groups    = ["sg-0a7d892dc5718628d"]
#  }
#
#  tags = {
#    "eks:cluster-name"   = "test-eks-cluster"
#    "eks:nodegroup-name" = "test-eks-nodegroup"
#  }
#
#  tags_all = {
#    "eks:cluster-name"   = "test-eks-cluster"
#    "eks:nodegroup-name" = "test-eks-nodegroup"
#  }
#
#  user_data = "TUlNRS1WZXJzaW9uOiAxLjAKQ29udGVudC1UeXBlOiBtdWx0aXBhcnQvbWl4ZWQ7IGJvdW5kYXJ5PSIvLyIKCi0tLy8KQ29udGVudC1UeXBlOiB0ZXh0L3gtc2hlbGxzY3JpcHQ7IGNoYXJzZXQ9InVzLWFzY2lpIgojIS9iaW4vYmFzaApzZXQgLWV4CkI2NF9DTFVTVEVSX0NBPUxTMHRMUzFDUlVkSlRpQkRSVkpVU1VaSlEwRlVSUzB0TFMwdENrMUpTVU0xZWtORFFXTXJaMEYzU1VKQlowbENRVVJCVGtKbmEzRm9hMmxIT1hjd1FrRlJjMFpCUkVGV1RWSk5kMFZSV1VSV1VWRkVSWGR3Y21SWFNtd0tZMjAxYkdSSFZucE5RalJZUkZSSmVVMUVTWGhQUkVWNVRsUmpkMDlXYjFoRVZFMTVUVVJKZUU1cVJYbE9WR04zVDFadmQwWlVSVlJOUWtWSFFURlZSUXBCZUUxTFlUTldhVnBZU25WYVdGSnNZM3BEUTBGVFNYZEVVVmxLUzI5YVNXaDJZMDVCVVVWQ1FsRkJSR2RuUlZCQlJFTkRRVkZ2UTJkblJVSkJVRVl6Q210YVRXeExaMjh6U1d4NVEwTjNRazR2ZW1od2JVODBjakV5ZDJOWVVsQXpXRmxETkVsNGJrbEZhbTAyTVc1S1puQlBNbXc1V0M4dmFIbDRjbEJvUW00S1ZYbG5VamxLWkhGMFVsQjNLM2swYVZwcU1ETTVNRWN6WXpGWVVFODJiVVZqYkRad2JGSlBORk53V0docmVGTXhZVUZIWWsxQlNsSjRWMmt2YmtGSUt3cHJabGt5V0hKa1JGWk9TMUpDY2xsc2JHRmxiRkJtWmpBdmFtaHJXbW95V201eVFsWXpibUZyYlZscmN6RjBlVU5HVTJGak0wOWxkV1kzUlRKbk5ISkZDbTV3ZVhWWWFTdHpRV1JZVUhCVmVuRjBja3BWVFRFckwwcFhVRXN4VlVSSloyUmpVREY0Wlc0MVRGcEVjRVJSVGtKelJ6Vk9iMEZOUzNvMlpXMXFXR0lLWm5aMEsyZHFaVWcwWm5kc1JrNVlZakp6TDJsNUt6RmpTVTltTUVVdlYzcGhRVXRSY1d0TEszWnRibVJCVHpKU1dWTnFUblYyUlhnd2JqTmFhVXh4V1FwT2FHOXRSVE1yV0dWNGVFUnhWbTV4WTBoTlEwRjNSVUZCWVU1RFRVVkJkMFJuV1VSV1VqQlFRVkZJTDBKQlVVUkJaMHRyVFVFNFIwRXhWV1JGZDBWQ0NpOTNVVVpOUVUxQ1FXWTRkMGhSV1VSV1VqQlBRa0paUlVaRFZ6RnVaRkZNUTNOVWNUWnJkbmMxWlhwQ1UwMVJZbEkxUTNsTlFUQkhRMU54UjFOSllqTUtSRkZGUWtOM1ZVRkJORWxDUVZGQ1dWbG5RMUo2UjNWTU1XTlRha28xUTFsMEsxSnlTbHA0VlVWV1pXNTBWRmRUYkRJdlJYWjVRV28zVW1sSU4ycDVUUXBxVjIwdlNsWkhVRm9yYW1obGNEWmlVM041VVdSNFdIYzRWVE14TWxCRWFsWnpZbmhVSzNvM2JYSmlSR05JWWs1T2JqQnZVbEJWTkRWc1NEWjJabGRDQ2xsQ1V6RnpSakpOT1dVM01tdDRTREpFY0ZOMGFtRTBOMVpNZFhSRFZqTlFhQ3RqVjB4c1IyZFJiQzh2VmtjelRWTm5aemRKTjFFMmNuSnFWM2gyV0djS09HMDNWR1Z4ZERaRFEyeElkbXMwTTJZcldXMVNNM1pVTDNVcldWQXdWMFZxVVZwU1luRTJSRnBJTUROQ1QwUkRXVWhqYWs5M1VWaG5kMmRIWjFWbVF3cG5jbnBQTkZneVNEWlBXVmRDVDNoQmFFRjZVMjlCYVVWTGNVaGhMMHN3YzB4aWMzZExSRGhUYWpKWEwycFJWVlozTjBORWVFZzFVR3cwVVdkTGJsTkhDakpvWVRSdFUwbDNNRUpXZDAxYVprNWxLMVY2Tmxkb2NERkZNV281Tkc1d1NsRklXQW90TFMwdExVVk9SQ0JEUlZKVVNVWkpRMEZVUlMwdExTMHRDZz09CkFQSV9TRVJWRVJfVVJMPWh0dHBzOi8vMEI2NzVDNUQ2NUYxNTQ5MkJGRTQzRTU1RkUxNDg3MzEueWw0LmFwLW5vcnRoZWFzdC0yLmVrcy5hbWF6b25hd3MuY29tCks4U19DTFVTVEVSX0ROU19JUD0xMC4xMDAuMC4xMAovZXRjL2Vrcy9ib290c3RyYXAuc2ggdGVzdC1la3MtY2x1c3RlciAtLWt1YmVsZXQtZXh0cmEtYXJncyAnLS1ub2RlLWxhYmVscz1la3MuYW1hem9uYXdzLmNvbS9ub2RlZ3JvdXAtaW1hZ2U9YW1pLTA1NmQ4NjU3ZDFmMmZiYzg1LGVrcy5hbWF6b25hd3MuY29tL2NhcGFjaXR5VHlwZT1PTl9ERU1BTkQsZWtzLmFtYXpvbmF3cy5jb20vbm9kZWdyb3VwPXRlc3QtZWtzLW5vZGVncm91cCxyb2xlPWVrcy1ub2RlZ3JvdXAgLS1tYXgtcG9kcz0xNycgLS1iNjQtY2x1c3Rlci1jYSAkQjY0X0NMVVNURVJfQ0EgLS1hcGlzZXJ2ZXItZW5kcG9pbnQgJEFQSV9TRVJWRVJfVVJMIC0tZG5zLWNsdXN0ZXItaXAgJEs4U19DTFVTVEVSX0ROU19JUCAtLXVzZS1tYXgtcG9kcyBmYWxzZQoKLS0vLy0t"
#}
