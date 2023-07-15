# AWS EBS CSI Driver 설치 방법

(1) IAM Role 및 EKS내 서비스 어카운트 생성
* 명령어
```
$ eksctl create iamserviceaccount \
  --name ebs-csi-controller-sa \
  --namespace kube-system \
  --cluster test-eks-cluster \
  --attach-policy-arn arn:aws:iam::aws:policy/service-role/AmazonEBSCSIDriverPolicy \
  --approve \
  --role-only \
  --role-name AmazonEKS_EBS_CSI_DriverRole
```

(2) eksctl 애드온을 통한 설치
* 명령어
```
$ eksctl create addon --name aws-ebs-csi-driver --cluster test-eks-cluster \
    --service-account-role-arn arn:aws:iam::<AWS ID Number>:role/AmazonEKS_EBS_CSI_DriverRole --force
```

(3) eksctl 애드온을 통한 설치 상태 확인
* 명령어
```
$ eksctl get addon --name aws-ebs-csi-driver --cluster test-eks-cluster
```

(4) AWS EBS CSI Driver 설치 확인
* 명령어
```
$ kubectl get pods -n kube-system | grep ebs-csi-controller
```