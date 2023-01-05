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
There prebuild docker image which you can use directly in your project or you can build yourself using Dockerfile commited in the repository.

```sh
docker pull hemant24/resource-eater
```

