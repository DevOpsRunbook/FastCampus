# Nexus 관련 질의응답 모음집입니다.

Nexus 실습시 질의주신 내용에 대한 응답 모음집입니다. 에러에 대한 해결 방법도 같이 명시하였습니다.

## FAQ
**Question:** Nexus Docker Registry(Repository) 대상 Docker Login시 에러메시지 발생 할 때 조치 방법

**Answer:** 아래와 같은 총 5가지 방식을 순서대로 진행하시거나, 이미 진행하신 부분이 있으시면 해당 부분에서 다음 항목을 수행하시면 됩니다.

## 1. docker 컨테이너 정상 유무 확인 및 5000번 포트 정상 기동 확인 여부
```
$ docker ps
CONTAINER ID   IMAGE             COMMAND                  CREATED          STATUS          PORTS                                                                                  NAMES
064230195bdd   sonatype/nexus3   "sh -c ${SONATYPE_DI…"   53 seconds ago   Up 51 seconds   0.0.0.0:5000->5000/tcp, :::5000->5000/tcp, 0.0.0.0:8081->8081/tcp, :::8081->8081/tcp   nexus
```
> 만일 정상 기동이 안되어 있거나, 5000번 포트가 정상 기동이 안된 경우에는 다음과 같이 실행해주시면 되십니다.
```
$ docker run --name nexus -d -p 8081:8081 -p 5000:5000 --name nexus -v /nexus-data:/nexus-data -u root sonatype/nexus3
```


## 2. 로컬에서의 daemon.json 정상 설정 여부
> daemon.json에 insecure repositories가 nexus vm의 public dns 주소 혹은 ip로 설정 되어 있는지 확인 부탁드립니다.
```
$ cat ~/.docker/daemon.json
{
  "builder": {
    "gc": {
      "defaultKeepStorage": "20GB",
      "enabled": true
    }
  },
  "experimental": false,
  "features": {
    "buildkit": false
  },
  "insecure-registries": [
    "ec2-3-36-76-114.ap-northeast-2.compute.amazonaws.com:5000"
  ]
}
```
> 설정이 안되어 있다면 설정해주시고, docker daemon을 restart 해주신다음 다음의 정보를 확인 하는 명령어로 확인하시면 정상 적용 여부를 확인하실수 있습니다.
```
$ docker info
.. 중략 ..
 Experimental: false
 Insecure Registries:
  ec2-3-36-76-114.ap-northeast-2.compute.amazonaws.com:5000
  hubproxy.docker.internal:5000
  127.0.0.0/8
 Live Restore Enabled: false
```

## 3. Nexus VM 내에서 localhost로 직접 로그인 확인
> localhost로 다음과 같이 직접 로그인 확인 부탁드리겠습니다.
```
$ docker login localhost:5000
Username: test
Password:
WARNING! Your password will be stored unencrypted in /home/ubuntu/.docker/config.json.
Configure a credential helper to remove this warning. See
https://docs.docker.com/engine/reference/commandline/login/#credentials-store

Login Succeeded
```

## 4. 로컬 pc에서 옵션을 뺀 url만 명시후 로그인
> 다음과 같이 url만 명시후 로그인 해보시고, username과 password는 직접 작성해보신뒤 되시는 지 확인 부탁드리겠습니다.
```
$ docker login ec2-3-36-76-114.ap-northeast-2.compute.amazonaws.com:5000
Username: test
Password:
Login Succeeded
```

## 5. Jenkins에서 Nexus Registry 로그인 문제 해결방법
## [상세내용링크](detail/Jenkins_Nexus-registry.md)
