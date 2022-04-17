# Namespace 생성
kubectl create namespace argocd

# ArgoCD 설치
kubectl kustomize ../manifests/| kubectl apply -n argocd -f -

# argocd-server의 type을 LoadBalancer로 변경
kubectl patch svc argocd-server -n argocd -p '{"spec": {"type": "LoadBalancer"}}'

# ArgoCD Web UI 접속시 admin 패스워드 확인 방법
kubectl -n argocd get secret argocd-initial-admin-secret -o jsonpath="{.data.password}" | base64 -d; echo
