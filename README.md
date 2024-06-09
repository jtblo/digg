# digg-quarkus

## Running the application


```shell script
Build the image with:

 docker build -f src/main/docker/Dockerfile.native-micro-multistage -t quarkus/digg-quarkus .

 Then run the container using:
 
 docker run -i --rm -p 8080:8080 quarkus/digg-quarkus 
```

