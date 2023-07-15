# AWS Load Balancer Controller 설치 방법

(1) IAM Policy 생성
* 명령어
```
$ aws iam create-policy \
    --policy-name AWSLoadBalancerControllerIAMPolicy \
    --policy-document file://iam_policy.json
```

(2) IAM Role 및 EKS내 서비스 어카운트 생성
* 명령어
```
$ eksctl create iamserviceaccount \
  --cluster=test-eks-cluster \
  --namespace=kube-system \
  --name=aws-load-balancer-controller \
  --role-name "AmazonEKSLoadBalancerControllerRole" \
  --attach-policy-arn=arn:aws:iam::<AWS ID Number>:policy/AWSLoadBalancerControllerIAMPolicy \
  --override-existing-serviceaccounts \
  --approve
```

(3) Helm Repository 등록 및 정보 업데이트
* 명령어
```
$ helm repo add eks https://aws.github.io/eks-charts
$ helm repo update
$ helm repo list
```

(4) Helm Release 배포
* 명령어
```
$ helm install aws-load-balancer-controller eks/aws-load-balancer-controller \
  -n kube-system \
  --set clusterName=test-eks-cluster \
  --set serviceAccount.create=false \
  --set serviceAccount.name=aws-load-balancer-controller
```

(5) Helm Release 배포 확인 및 K8s 리소스 생성 확인
* 명령어
```
$ helm list -n kube-system
$ kubectl get deployment -n kube-system aws-load-balancer-controller
```