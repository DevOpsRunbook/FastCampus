# AWS 웹 콘솔을 활용한 AWS EKS 생성 

## EKS Cluster 및 Nodegroup이 실행될 대상 Subnet에는 반드시 해당 TAG가 있어야함
- TAG명 : kubernetes.io/cluster/<EKS Cluster명>
- TAG값 : shared

## kubectl install
```
curl -o kubectl https://amazon-eks.s3.us-west-
2.amazonaws.com/1.21.2/2021-07-05/bin/linux/amd64/kubectl

chmod +x ./kubectl && mv ./kubectl /usr/local/bin/

kubectl versino
```

## aws cli
```
# 1. AWS 계정 Access Key 설
aws configure

# 2. Kubectl 사용을 위한 Kubeconfig 설정
aws eks update-kubeconfig --region <Region명> --name <EKS명>
```
