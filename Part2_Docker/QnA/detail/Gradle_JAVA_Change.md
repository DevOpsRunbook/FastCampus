## Gradle에서 사용하는 JAVA 변경 방법
**Question:** 다음과 같이 Gradle 혹은 Gradle Wrapper로 Clean후 Build시 어떠한 방법으로도 진행 안될 때 해결 방법
```
$ gradle clean build
```
![1_gradle_clean_build_fail](https://devopsrunbook-fastcampus.s3.ap-northeast-2.amazonaws.com/FastCampus/Part2_Docker/Chapter08/4_pri-nexus-docker/1_gradle_clean_build_fail.PNG)

```
$ ./gradlew clean build
```
![2_gradlew_clean_build_fail](https://devopsrunbook-fastcampus.s3.ap-northeast-2.amazonaws.com/FastCampus/Part2_Docker/Chapter08/4_pri-nexus-docker/2_gradlew_clean_build_fail.PNG)


**Answer:** Gradle을 사용하는 Linux OS의 JAVA를 OpenJDK가 아닌 Oracle JAVA SE 17 버전으로 변경

(1) Oracle JAVA SE 17 버전 다운로드
```
$  wget https://download.oracle.com/java/17/latest/jdk-17_linux-x64_bin.deb --no-check-certificate
```
![3_oracle_java_download](https://devopsrunbook-fastcampus.s3.ap-northeast-2.amazonaws.com/FastCampus/Part2_Docker/Chapter08/4_pri-nexus-docker/3_oracle_java_download.PNG)

(2) Oracle JAVA SE 17 버전 패키지 설치
```
$ sudo dpkg -i jdk-17_linux-x64_bin.deb
```
![4_oracle_java_install](https://devopsrunbook-fastcampus.s3.ap-northeast-2.amazonaws.com/FastCampus/Part2_Docker/Chapter08/4_pri-nexus-docker/4_oracle_java_install.PNG)


(3) Gradle을 사용하는 Linux OS의 JAVA를 OpenJDK가 아닌 Oracle JAVA SE 17 버전으로 변경
> 여기서는 명령어 실행후, Oracle JAVA SE 17 버전이 있는 selection Number를 입력후, Enter를 누르면 해당 JAVA로 적용이 된다.
```
$ sudo update-alternatives --config java
```
![5_change_oracle_java](https://devopsrunbook-fastcampus.s3.ap-northeast-2.amazonaws.com/FastCampus/Part2_Docker/Chapter08/4_pri-nexus-docker/5_change_oracle_java.PNG)


(4) Oracle JAVA SE 17 버전으로 변경 확인
```
$ java -version
```
![6_oracle_java_version](https://devopsrunbook-fastcampus.s3.ap-northeast-2.amazonaws.com/FastCampus/Part2_Docker/Chapter08/4_pri-nexus-docker/6_oracle_java_version.PNG)

(5) Gradle 혹은 Gradle Wrapper로 Clean후 Build시 정상 수행됨을 확인
```
$ ./gradlew clean build
```
![7_gradlew_clean_build_success](https://devopsrunbook-fastcampus.s3.ap-northeast-2.amazonaws.com/FastCampus/Part2_Docker/Chapter08/4_pri-nexus-docker/7_gradlew_clean_build_success.PNG)