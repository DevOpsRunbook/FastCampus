# Swagger 관련 질의응답 모음집입니다.

Swagger 실습시 질의주신 내용에 대한 응답 모음집입니다. 

## 1. Swagger 실습 환경 구성 방법
**Question:** AWS ECR 사전 구성 방법 및 AWS ELB 로드밸런서를 통한 Swagger UI 접속 방법

**Answer:** 다음과 같은 방법으로 진행

(1) AWS ECR Repository 구성
* 실습을 위한 AWS ECR Repository의 경우, AWS Management Console에서 미리 4개의 Repository를 생성함
* 생성시 다음과 같은 이름으로 생성 (product-composite, product, recommendation, review)
![00_aws-ecr-repositories](https://devopsrunbook-fastcampus.s3.ap-northeast-2.amazonaws.com/FastCampus/Part6_MSA/Ch03_05/00_aws-ecr-repositories.png)

(2) Gradle을 이용해 마이크로서비스를 모두 빌드
* 4개의 실습용 마이크로서비스를 모두 빌드
* 경로 : Part6_MSA/Chapter03/
* 명령어
```
$ gradle clean build
```
* 명령어 수행 예시
![01_microservice-gradle-build](https://devopsrunbook-fastcampus.s3.ap-northeast-2.amazonaws.com/FastCampus/Part6_MSA/Ch03_05/01_microservice-gradle-build.png)

(3) Product-Composite 마이크로서비스를 Docker 컨테이너 이미지 빌드 및 Push
* 경로 : Part6_MSA/Chapter03/microservices/product-composite-service
* 명령어
```
$ docker build -t <AWS 계정 ID>.dkr.ecr.ap-northeast-2.amazonaws.com/product-composite:1.0 ./
$ docker push <AWS 계정 ID>.dkr.ecr.ap-northeast-2.amazonaws.com/product-composite:1.0
```
* 명령어 수행 예시
![02_product-composite-build-push](https://devopsrunbook-fastcampus.s3.ap-northeast-2.amazonaws.com/FastCampus/Part6_MSA/Ch03_05/02_product-composite-build-push.png)

* Push된 컨테이너 이미지를 AWS ECR Repository에서 확인
![03_ecr-product-composite-image](https://devopsrunbook-fastcampus.s3.ap-northeast-2.amazonaws.com/FastCampus/Part6_MSA/Ch03_05/03_ecr-product-composite-image.png)

(4) Product 마이크로서비스를 Docker 컨테이너 이미지 빌드 및 Push
* 경로 : Part6_MSA/Chapter03/microservices/product-service
* 명령어
```
$ docker build -t <AWS 계정 ID>.dkr.ecr.ap-northeast-2.amazonaws.com/product:1.0 ./
$ docker push <AWS 계정 ID>.dkr.ecr.ap-northeast-2.amazonaws.com/product:1.0
```
* 명령어 수행 예시
![04_product-build-push](https://devopsrunbook-fastcampus.s3.ap-northeast-2.amazonaws.com/FastCampus/Part6_MSA/Ch03_05/04_product-build-push.png)

* Push된 컨테이너 이미지를 AWS ECR Repository에서 확인
![05_ecr-product-image](https://devopsrunbook-fastcampus.s3.ap-northeast-2.amazonaws.com/FastCampus/Part6_MSA/Ch03_05/05_ecr-product-image.png)

(5) Recommendation 마이크로서비스를 Docker 컨테이너 이미지 빌드 및 Push
* 경로 : Part6_MSA/Chapter03/microservices/recommendation-service
* 명령어
```
$ docker build -t <AWS 계정 ID>.dkr.ecr.ap-northeast-2.amazonaws.com/recommendation:1.0 ./
$ docker push <AWS 계정 ID>.dkr.ecr.ap-northeast-2.amazonaws.com/recommendation:1.0
```
* 명령어 수행 예시
![06_recommendation-build-push](https://devopsrunbook-fastcampus.s3.ap-northeast-2.amazonaws.com/FastCampus/Part6_MSA/Ch03_05/06_recommendation-build-push.png)

* Push된 컨테이너 이미지를 AWS ECR Repository에서 확인
![07_ecr-recommendation-image](https://devopsrunbook-fastcampus.s3.ap-northeast-2.amazonaws.com/FastCampus/Part6_MSA/Ch03_05/07_ecr-recommendation-image.png)

(6) Review 마이크로서비스를 Docker 컨테이너 이미지 빌드 및 Push
* 경로 : Part6_MSA/Chapter03/microservices/review-service
* 명령어
```
$ docker build -t <AWS 계정 ID>.dkr.ecr.ap-northeast-2.amazonaws.com/review:1.0 ./
$ docker push <AWS 계정 ID>.dkr.ecr.ap-northeast-2.amazonaws.com/review:1.0
```
* 명령어 수행 예시
![08_review-build-push](https://devopsrunbook-fastcampus.s3.ap-northeast-2.amazonaws.com/FastCampus/Part6_MSA/Ch03_05/08_review-build-push.png)

* Push된 컨테이너 이미지를 AWS ECR Repository에서 확인
![09_ecr-review-image](https://devopsrunbook-fastcampus.s3.ap-northeast-2.amazonaws.com/FastCampus/Part6_MSA/Ch03_05/09_ecr-review-image.png)

(7) 컨테이너 이미지 빌드된 목록
* 명령어
```
$ docker images
```
* 명령어 수행 예시
![10_docker-image-list](https://devopsrunbook-fastcampus.s3.ap-northeast-2.amazonaws.com/FastCampus/Part6_MSA/Ch03_05/10_docker-image-list.png)

(8) Kubernetes에 MSA 구성 배포 및 배포 현황 확인
* 경로 : Part6_MSA/Chapter03/kubernetes
* 명령어
```
$ kubectl apply -f ./
$ kubectl get deploy
$ kubectl get pods
$ kubectl get services
```
* 명령어 수행 예시
![16_k8s-apply](https://devopsrunbook-fastcampus.s3.ap-northeast-2.amazonaws.com/FastCampus/Part6_MSA/Ch03_05/16_k8s-apply.png)
![11_k8s-deployments](https://devopsrunbook-fastcampus.s3.ap-northeast-2.amazonaws.com/FastCampus/Part6_MSA/Ch03_05/11_k8s-deployments.png)
![12_k8s-pods](https://devopsrunbook-fastcampus.s3.ap-northeast-2.amazonaws.com/FastCampus/Part6_MSA/Ch03_05/12_k8s-pods.png)
![13_k8s-services](https://devopsrunbook-fastcampus.s3.ap-northeast-2.amazonaws.com/FastCampus/Part6_MSA/Ch03_05/13_k8s-services.png)

* Service의 경우, product-composite Service의 Type이 LoadBalancer이므로 자동으로 AWS ELB LoadBalancer가 생성되었으며, 생성된 결과는 AWS Management Console에서 확인 가능
![14_aws-elb-loadbalancer](https://devopsrunbook-fastcampus.s3.ap-northeast-2.amazonaws.com/FastCampus/Part6_MSA/Ch03_05/14_aws-elb-loadbalancer.png)
> ELB 상태 확인결과, 2개의 EKS Worker Node에 inService 중인 상태인 것으로 확인
> 접속은 ELB의 DNS 도메인주소(product-composite Service의 External IP)를 HTTP프로토콜, 8080포트로 가능

(9) Swagger UI 접속 방법
* Swagger UI는 Product-Composite의 Swagger UI를 통해 API Document 확인이 가능하다.
* URL : http://<8번에서 확인한 ELB의 DNS 도메인주소(product-composite Service의 External IP):8080/swagger-ui/index.html
* 접속 수행 예시
![17_swagger-ui-doc](https://devopsrunbook-fastcampus.s3.ap-northeast-2.amazonaws.com/FastCampus/Part6_MSA/Ch03_05/17_swagger-ui-doc.png)
![15_swagger-ui](https://devopsrunbook-fastcampus.s3.ap-northeast-2.amazonaws.com/FastCampus/Part6_MSA/Ch03_05/15_swagger-ui.png)


