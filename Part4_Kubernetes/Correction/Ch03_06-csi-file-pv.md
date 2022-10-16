# Chapter3 > 6번 강의 정정내용 입니다.

Chapter3 > 6번 강의 : AWS EFS CSI Driver를 활용한 File Storage PV 생성 강의 중 정정할 내용에 대해 다음과 같이 명시하였습니다.

## 1. Persistent Volume(PV) 언급된 내용중 정정할 내용
**기존 강의내용** 
* 강의 타임라인 : 46:32 ~ 47:05
> PV의 ReadWriteOnce는 POD하나에만 연결되고, 두개 이상의 POD에는 연결이 안된다.


**정정된 내용**
> POD가 아니라 Node기준으로 연결이되는 것이며, Node내부에 있는 POD는 볼륨을 Mount해서 사용하는 것이다.


**정정된 내용 검증**
(1) 구성되어 있는 검증용 K8s Resource 현황 확인
* POD, PersistentVolumeClaim, PersistentVolume, NODE 현황 확인
```
$ kubectl get po
$ kubectl get pvc
$ kubectl get pv
$ kubectl get no
```
![1_k8s-resources](https://devopsrunbook-fastcampus.s3.ap-northeast-2.amazonaws.com/FastCampus/Part4_Kubernetes/Chapter3/6-EFS-PV/1_k8s-resources.png)

(2) POD에 Mount된 Volume 상세내역
* PersistentVolumeClaim 맵핑 내역 및 Mount된 Volume 현황
> 여기서는 POD가 ip-172-32-2-60 노드에서 실행중임을 알 수 있다.
```
$ kubectl get po <POD명> -o yaml | grep -C5 vol
$ kubectl describe po <POD명>
```
![2_pod-vol-mount.png](https://devopsrunbook-fastcampus.s3.ap-northeast-2.amazonaws.com/FastCampus/Part4_Kubernetes/Chapter3/6-EFS-PV/2_pod-vol-mount.png)
![3_pod-describe](https://devopsrunbook-fastcampus.s3.ap-northeast-2.amazonaws.com/FastCampus/Part4_Kubernetes/Chapter3/6-EFS-PV/3_pod-describe.png)

(3) PersistentVolumeClaim(PVC) 상세내역
* PersistentVolume 맵핑 내역 및 EKS Worker Node 현황
> 여기서는 PersistentVolume이 ip-172-32-2-60 노드로 선택
```
$ kubectl describe pvc <PVC명>
```
![4_pvc-describe](https://devopsrunbook-fastcampus.s3.ap-northeast-2.amazonaws.com/FastCampus/Part4_Kubernetes/Chapter3/6-EFS-PV/4_pvc-describe.png)

(4) PersistentVolume(PV) 상세내역
* PersistentVolume 상세 내역 및 EBS Volume 맵핑 현황
> 여기서는 PersistentVolume이 vol-06d8fd91e006505c6 볼륨으로 맵핑
```
$ kubectl describe pv <PV명>
```
![5_pv-describe](https://devopsrunbook-fastcampus.s3.ap-northeast-2.amazonaws.com/FastCampus/Part4_Kubernetes/Chapter3/6-EFS-PV/5_pv-describe.png)

(5) EBS 볼륨 상세내역
* (4)번에서 확인된 Volume-ID로 AWS 볼륨에서 검색하면 맵핑된 EBS볼륨의 상세 내역을 다음과 같이 출력한다. 
> 여기서는 PersistentVolume에서 확인한 vol-06d8fd91e006505c6로 검색
> 
> 해당 볼륨이 Attach된 EC2 VM 인스턴스의 ID를 확인 가능
> 
> 여기서 확인할 수 있는 EC2 VM 인스턴스의 ID는 i-0c1e9e24e8c2d25af 이다.
![6_aws-ebs](https://devopsrunbook-fastcampus.s3.ap-northeast-2.amazonaws.com/FastCampus/Part4_Kubernetes/Chapter3/6-EFS-PV/6_aws-ebs.png)


(6) EC2 VM 상세내역
* (5)번에서 확인된 EC2 VM 인스턴스의 ID로 AWS 인스턴트에서 검색하면 맵핑된 EC2 VM을 다음과 같이 출력한다.
> 여기서는 EBS볼륨에서 확인한 i-0c1e9e24e8c2d25af로 검색
> 
> EBS볼륨이 Attach된 EC2 VM 인스턴스를 확인
> 
> 여기서 확인 할 수 있는 EC2 VM 인스턴스의 프라이빗 IP DNS 이름은 ip-172-32-2-60 노드임을 알 수 있다.
![7_aws-ec2](https://devopsrunbook-fastcampus.s3.ap-northeast-2.amazonaws.com/FastCampus/Part4_Kubernetes/Chapter3/6-EFS-PV/7_aws-ec2.png)

### 여기까지 확인한 결과 POD에 Mount된 PersistentVolume(PV)은 POD를 실행시키는 EKS Worker Node에 Attach된 EBS Volume임을 확인 할 수 있다.


(7) 가설1 : POD를 Restart하면 다른 EKS Worker Node로 스케쥴링이 되고 PV Mount가 바뀔 것이다?
* 확인을 위해 POD를 배포할 때 사용한 Deployment를 Rollout Restart한다.
```
$ kubectl get deploy
$ kubectl get po -o wide
$ kubectl rollout restart deploy <Deployment명>
$ kubectl get po -o wide
$ kubectl describe po <POD명>
```
> Deployment의 Rollout Restart를 몇번을 해도 PV가 Mount된 ip-172-32-2-60 노드에만 스케쥴링이 되는 것을 알 수 있다.
![8_pod-restart](https://devopsrunbook-fastcampus.s3.ap-northeast-2.amazonaws.com/FastCampus/Part4_Kubernetes/Chapter3/6-EFS-PV/8_pod-restart.png)
![9_pod-restart-describe](https://devopsrunbook-fastcampus.s3.ap-northeast-2.amazonaws.com/FastCampus/Part4_Kubernetes/Chapter3/6-EFS-PV/9_pod-restart-describe.png)

(8) 가설2 : POD가 실행중이고 및 PV가 Attach된 EKS Worker Node에 스케쥴링 못하도록 막는다면?
* 확인을 위해 해당 노드에 Cordon을 적용한다.
* Cordon 적용후, POD를 배포할 때 사용한 Deployment를 Rollout Restart한다.
```
$ kubectl get no
$ kubectl cordon <POD가 기동중인 EKS Worker Node명>
$ kubectl get deploy
$ kubectl get no
$ kubectl rollout restart deploy <Deployment명>
$ kubectl get po
$ kubectl describe po <POD명>
```
> Deployment의 Rollout Restart를 해서 새로 생성된 POD부터는 Pending 상태가 된것을 확인 할 수 있다.
>
> POD의 이벤트 내역을 보면 현재 Node Affinity Conflict 상태로 Pending이 걸린것을 확인 할 수 있다.
![10_node-cordon](https://devopsrunbook-fastcampus.s3.ap-northeast-2.amazonaws.com/FastCampus/Part4_Kubernetes/Chapter3/6-EFS-PV/10_node-cordon.png)
![11_pod-cordon-describe](https://devopsrunbook-fastcampus.s3.ap-northeast-2.amazonaws.com/FastCampus/Part4_Kubernetes/Chapter3/6-EFS-PV/11_pod-cordon-describe.png)

(9) Cordon 상태의 노드를 다시 Uncordon을 통해 POD 스케쥴링이 되도록 만들면 어떻게 될까?
* 확인을 위해 해당 노드에 Uncordon을 적용한다.
* Uncordon 적용후, POD의 상태를 확인한다.
```
$ kubectl get no
$ kubectl uncordon <Cordon이 적용된 EKS Worker Node명>
$ kubectl get no
$ kubectl get deploy
$ kubectl get po
$ kubectl get po -o wide
```
> 노드를 Uncordon하면 그 즉시 기존에 Pending 상태인 POD가 정상적으로 Running이 되는 것을 확인 할 수 있다.
>
> POD가 스케쥴링된 노드는 기존에 실행되고 PV Mount가 된 ip-172-32-2-60 노드임을 확인 할 수 있다.
>
> POD의 이벤트 내역을 보면, Successfully assigned <POD명> to <Node명>이 명시가 되면서, 원래 실행된 노드에 정상적으로 스케쥴링이 된것을 확인 할 수 있다.
![12_pod-uncordon](https://devopsrunbook-fastcampus.s3.ap-northeast-2.amazonaws.com/FastCampus/Part4_Kubernetes/Chapter3/6-EFS-PV/12_pod-uncordon.png)
![13_pod-uncordon-describe](https://devopsrunbook-fastcampus.s3.ap-northeast-2.amazonaws.com/FastCampus/Part4_Kubernetes/Chapter3/6-EFS-PV/13_pod-uncordon-describe.png)

## 결론

* 위에 있는 내용을 정리하면, Persistent Volume(PV)은 POD가 아닌 EKS Worker Node에 EBS Volume이 Attach되고 POD에는 PV가 Mount되기 때문에, Node에 연결 됨을 확인하여 이를 강의 내용에서 정정합니다.