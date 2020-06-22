# demo

[![Build Status](https://travis-ci.org/octopus-prime/demo.svg?branch=master)](https://travis-ci.org/octopus-prime/demo)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=octopus-prime_demo&metric=alert_status)](https://sonarcloud.io/dashboard?id=octopus-prime_demo)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=octopus-prime_demo&metric=coverage)](https://sonarcloud.io/dashboard?id=octopus-prime_demo)

## microk8s

### install

```
sudo snap install microk8s --classic
microk8s.enable dns ingress registry helm3 jaeger dashboard
```

### remove

```
microk8s.reset
sudo snap remove microk8s
```

### alias

```
alias h=microk8s.helm3
alias k=microk8s.kubectl
```

### command

```
h ls
k get all -o wide
```

## demo

### docker

```
./gradlew dockerEnvUp dockerDemoUp
./gradlew dockerEnvDown dockerDemoDown
```

| service           | url |
| ---               | --- |
| kunde-service     | http://localhost:8001/kunde-api/swagger-ui.html |
| produkt-service   | http://localhost:8002/produkt-api/swagger-ui.html |
| rechnung-service  | http://localhost:8003/rechnung-api/swagger-ui.html |
| tracing-service   | http://localhost:9411/zipkin |
| monitoing-service | http://localhost:3000 |

### kubernetes

```
./gradlew helmEnvUp helmDemoUp
./gradlew helmEnvDown helmDemoDown
```

| service           | url |
| ---               | --- |
| demo              | https://localhost/demo/index.html |
| swagger           | https://localhost/swagger-ui.html |
| tracing           | https://localhost |
| monitoing         | https://localhost:16443/api/v1/namespaces/kube-system/services/monitoring-grafana/proxy |
| dashboard         | https://localhost:16443/api/v1/namespaces/kube-system/services/https:kubernetes-dashboard:/proxy/ |
