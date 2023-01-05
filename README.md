# Resource Eater
Java based application which consumes/eat CPU and memory, Helpful for testing, debugging. I created this application to test various scenario of K8's HPA. 

# APIs
## Eat CPU resource
```sh
GET /eat/cpu
```

## Eat memory resource
```sh
GET /eat/memory
```


## Stop eating cpu resource
```sh
GET /eat/cpu/stop
```

## Stop eating memory resource
```sh
GET /eat/memory/stop
```

# k8 files
I have add relevant k8 resource files in the repository which helps to test various scenario of HPA 

# DockerImage
There is prebuild docker image which you can use directly in your project or you can build yourself using Dockerfile commited in the repository.

```sh
docker pull hemant24/resource-eater
```

# Deployment

## AWS eks
I have used eksctl to create, test and destroy the AWS eks cluster. The relevant files are in folder eksctl

```sh
brew upgrade eksctl && { brew link --overwrite eksctl; } || { brew tap weaveworks/tap; brew install weaveworks/tap/eksctl; }
```

```sh
eksctl create cluster -f cluster.yaml
```

```sh
eksctl delete cluster -f cluster.yaml
```

### Deploy metric server
```sh
kubectl apply -f https://github.com/kubernetes-sigs/metrics-server/releases/latest/download/components.yaml

kubectl get deployment metrics-server -n kube-system
```
## Kind
Relevant files are in folder kind
```sh
kind create cluster  --config kind-config.yaml
```

You need to patch metric server, as metioned [here](https://gist.github.com/sanketsudake/a089e691286bf2189bfedf295222bd43)

```sh
kubectl patch -n kube-system deployment metrics-server --type=json \
  -p '[{"op":"add","path":"/spec/template/spec/containers/0/args/-","value":"--kubelet-insecure-tls"}]'
```
