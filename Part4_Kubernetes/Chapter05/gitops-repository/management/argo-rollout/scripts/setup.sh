# Namespace 생성
kubectl create namespace argo-rollouts

# Argo Rollout 설치
kubectl kustomize ../manifests/ | kubectl apply -f -

# 리눅스에서의 Argo Rollout 플러그인 설치
#curl -LO https://github.com/argoproj/argo-rollouts/releases/latest/download/kubectl-argo-rollouts-darwin-amd64
#chmod +x ./kubectl-argo-rollouts-darwin-amd64
#sudo mv ./kubectl-argo-rollouts-darwin-amd64 /usr/local/bin/kubectl-argo-rollouts

# MAC OS에서의 Argo Rollout 플러그인 설치
brew install argoproj/tap/kubectl-argo-rollouts

# Argo Rollout Dashboard 기동 (http://localhost:3100)
kubectl argo rollouts dashboard
