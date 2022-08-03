# Kubernetes 설정 부록
## 1. kubectl 및 eksctl 설치
### 1.1 kubectl 다운로드 및 설치 확인
(1) MacOS 설치
```
$ curl -o kubectl https://s3.us-west-2.amazonaws.com/amazon-eks/1.22.6/2022-03-09/bin/darwin/amd64/kubectl
$ chmod +x ./kubectl
$ mkdir -p $HOME/bin && cp ./kubectl $HOME/bin/kubectl && export PATH=$HOME/bin:$PATH
$ kubectl version --short --client
```
(2) Linux 설치
```
$ curl -o kubectl https://s3.us-west-2.amazonaws.com/amazon-eks/1.22.6/2022-03-09/bin/linux/amd64/kubectl
$ chmod +x ./kubectl
$ mkdir -p $HOME/bin && cp ./kubectl $HOME/bin/kubectl && export PATH=$PATH:$HOME/bin
$ kubectl version --short --client
```
(3) Windows 설치
1. Windows 운영 체제에 kubectl을 설치합니다.
```
$ curl -o kubectl.exe https://s3.us-west-2.amazonaws.com/amazon-eks/1.22.6/2022-03-09/bin/windows/amd64/kubectl.exe
```
2. C:\bin과 같이, 명령줄 이진 파일용 새 디렉터리를 생성합니다.
3. kubectl.exe 이진 파일을 새 디렉터리로 복사합니다. 
4. 사용자 또는 시스템 PATH 환경 변수를 편집하여 PATH에 새 디렉터리를 추가합니다.
5. PowerShell 터미널을 닫고 새 PATH 변수를 가져오기 위해 새 터미널을 엽니다.
6. 버전 확인을 합니다.
```
$ kubectl version --short --client
```
### 1.2 eksctl 다운로드 및 설치 확인
(1) MacOS 설치
```
$ brew tap weaveworks/tap
$ brew install weaveworks/tap/eksctl
$ eksctl version
```
(2) Linux 설치
```
$ curl --silent --location "https://github.com/weaveworks/eksctl/releases/latest/download/eksctl_$(uname -s)_amd64.tar.gz" | tar xz -C /tmp
$ sudo mv /tmp/eksctl /usr/local/bin
$ eksctl version
```
(3) Windows 설치
1. Windows 시스템에 아직 Chocolatey가 설치되어 있지 않은 경우, [Chocolatey 설치](https://chocolatey.org/install "Chocolatey")를 참조하세요.
2. eksctl을 설치합니다.
```
$ choco install -y eksctl 
```
3. 다음 명령으로 설치가 제대로 되었는지 테스트합니다.
```
$ eksctl version
```

## 2. aws eks로 kubeconfig 설정
### 2.1 aws eks 명령어를 통해 kubeconfig 로컬에 다운로드
```
$ aws eks update-kubeconfig --name <사용자의 EKS Cluster명>
```
홈디렉토리/.kube/ 경로에서 config 파일이 생성됨을 확인
```
$ cat ~/.kube/config
```

## 3. kubectl 동작 확인
### 3.1 kubectl로 노드 및 전체 네임스페이스 확인
```
$ kubectl get nodes
$ kubectl get namespaces
```
### 3.2 kubectl로 서비스 및 전체 pod 확인
```
$ kubectl get service -A
$ kubectl get pod -A
```

## 4. aws load balancer controller 설치
### 4.1 IAM Policy 설정
현재 README.md와 같은 경로중 ingress-loadbalancer-controller 디렉토리로 이동후 설정
```
$ cd ingress-loadbalancer-controller
$ aws iam create-policy \
    --policy-name AWSLoadBalancerControllerIAMPolicy \
    --policy-document file://iam_policy.json
```
### 4.2 eksctl을 통한 IAM Role 설정
```
$ eksctl utils associate-iam-oidc-provider --region=ap-northeast-2 \
  --cluster=<사용자의 EKS Cluster명> --approve
$ eksctl create iamserviceaccount \
  --cluster=<사용자의 EKS Cluster명> \
  --namespace=kube-system \
  --name=aws-load-balancer-controller \
  --role-name "AmazonEKSLoadBalancerControllerRole" \
  --attach-policy-arn=arn:aws:iam::<사용자의 12자리 AWS 계정 ID>:policy/AWSLoadBalancerControllerIAMPolicy \
  --approve
```
### 4.3 AWS Load Balancer Controller Helm Chart 설치
(1) eks-charts 리포지토리 추가
```
$ helm repo add eks https://aws.github.io/eks-charts
```
(2) 최신 Charts가 적용되도록 로컬 리포지토리를 업데이트
```
$ helm repo update
```
(3) 등록된 Helm Charts로 Ingress Load Balancer Controller 배포
```
$ helm install aws-load-balancer-controller eks/aws-load-balancer-controller \
  -n kube-system \
  --set clusterName=<사용자의 EKS Cluster명> \
  --set serviceAccount.create=false \
  --set serviceAccount.name=aws-load-balancer-controller 
```
### 4.4 설치 결과 확인
컨트롤러 설치여부 확인
```
$ kubectl get deployment -n kube-system aws-load-balancer-controller
```

## 5. Ingress 구성 및 테스트
### 5.1 Ingress 적용을 위한 예제 Deployment 및 Service 배포
현재 README.md와 같은 경로중 ingress-loadbalancer-controller > example 디렉토리로 이동후 진행
```
$ cd ingress-loadbalancer-controller/example
$ kubectl apply -f deployment.yaml
$ kubectl apply -f service.yaml
```
### 5.2 Ingress 매니페스트 설명
ingress.yaml 파일 기준 설명
```
...
kind: Ingress
metadata:
  name: ingress-2048
  annotations:
    alb.ingress.kubernetes.io/scheme: internet-facing # Public(Internet)과 연결가능 한 Subnet에 LB 생성
    alb.ingress.kubernetes.io/target-type: ip # POD의 IP를 LB의 Target으로 등록. LB에 도달하는 트래픽은 POD로 직접 라우팅됨
spec:
  ingressClassName: alb # Ingress 생성시 자동으로 AWS ALB(Application Load Balancer)가 생성되도록 적용
...
```
### 5.3 Ingress 매니페스트 배포
```
$ kubectl apply -f ingress.yaml
```
### 5.4 Ingress 생성 및 AWS ALB 생성 확인
```
$ kubectl get ingress/ingress-2048
```
### 5.5 Ingress를 통한 서비스 접근 확인
5.4에서 출력된 결과중 ADDRESS에 출력된 주소를 웹브라우저에 입력해서 접근 확인
```
NAME           CLASS   HOSTS   ADDRESS                        PORTS   AGE
ingress-2048   alb     *       <AWS ALB의 Public DNS 주소>      80      29s
```