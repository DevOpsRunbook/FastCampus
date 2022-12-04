# EBS CSI Driver 관련 질의응답 모음집입니다.

EBS CSI Driver 실습시 질의주신 내용에 대한 응답 모음집입니다. 

## 1. EBS CSI Driver 실습 환경 구성 방법
**Question:** Kubernetes(AWS EKS)에서 Persistent Volume이 생성 안될 때 생성하는 방법

**Answer:** 다음과 같은 방법으로 진행

(1) IAM Role 및 EKS내 서비스 어카운트 생성
* 명령어
```
$ eksctl create iamserviceaccount \
  --name ebs-csi-controller-sa \
  --namespace kube-system \
  --cluster <EKS Cluster명> \
  --attach-policy-arn arn:aws:iam::aws:policy/service-role/AmazonEBSCSIDriverPolicy \
  --approve \
  --role-only \
  --role-name AmazonEKS_EBS_CSI_DriverRole
```

(2) eksctl 애드온을 통한 설치
* 명령어
```
$ eksctl create addon --name aws-ebs-csi-driver --cluster <EKS 클러스터명> --service-account-role-arn arn:aws:iam::<AWS 계정 ID>:role/AmazonEKS_EBS_CSI_DriverRole --force
```

(3) eksctl 애드온을 통한 설치 상태 확인
* 명령어
```
$ eksctl get addon --name aws-ebs-csi-driver --cluster <EKS 클러스터명>
```

(4) AWS EBS CSI Driver 설치 확인
* 명령어
```
$ kubectl get pods -n kube-system | grep ebs-csi-controller
```