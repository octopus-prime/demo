# demo

[![Build Status](https://travis-ci.org/octopus-prime/demo.svg?branch=master)](https://travis-ci.org/octopus-prime/demo)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=octopus-prime_demo&metric=alert_status)](https://sonarcloud.io/dashboard?id=octopus-prime_demo)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=octopus-prime_demo&metric=coverage)](https://sonarcloud.io/dashboard?id=octopus-prime_demo)

## microk8s

### install

```
sudo snap install microk8s --channel=edge --classic
sudo iptables -P FORWARD ACCEPT
microk8s.enable dns ingress registry helm jaeger
microk8s.helm init --override spec.selector.matchLabels.'name'='tiller',spec.selector.matchLabels.'app'='helm' --output yaml | sed 's@apiVersion: extensions/v1beta1@apiVersion: apps/v1@' | microk8s.kubectl apply -f -
```

### remove

```
microk8s.reset
sudo snap remove microk8s
```

### alias

```
alias h=microk8s.helm
alias k=microk8s.kubectl
```

### command

```
h ls
k get all -o wide
```
