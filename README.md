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

## Scale down issue discussion

[link](https://stackoverflow.com/questions/58535208/hpa-scale-down-not-happening-properly)

## HPA need 'request' to be defined

If you add container in your pod defination for which 'request' is not defined HPA won't work. To reproduce use following resource-eater.yml instead

```sh

apiVersion: apps/v1
kind: Deployment
metadata:
  name: resource-eater-deployment
  labels:
    app: resource-eater
spec:
  replicas: 1
  selector:
    matchLabels:
      app: resource-eater
  template:
    metadata:
      labels:
        app: resource-eater
    spec:
      containers:
      - name: resource-eater
        image: hemant24/resource-eater:latest
        imagePullPolicy: Always # IfNotPresent
        ports:
        - containerPort: 8080
        resources:
          requests:
            memory: "250Mi"
            cpu: "100m"
          limits:
            memory: "350Mi"
            cpu: "300m"
      - name: resource-eater-2
        image: hemant24/resource-eater:latest
        imagePullPolicy: Always # IfNotPresent
        env:
          - name: SERVER_PORT
            value : "8081"
        ports:
        - containerPort: 8081

---


apiVersion: v1
kind: Service
metadata:
  name: resource-eater
  namespace: default
spec:
  type: NodePort #LoadBalancer
  selector:
    app: resource-eater
  ports:
  - port: 8080
    targetPort: 8080
    nodePort: 30000
    name : eater-1
  - port: 8081
    targetPort: 8081
    nodePort: 30001
    name : eater-2


```