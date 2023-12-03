# RSO: Expenses microservice

## Prerequisites

```bash
docker run -d --name pg-expenses -e POSTGRES_USER=dbuser -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=expenses -p 5432:5432 postgres:13
```

## Build and run commands
```bash
mvn clean package
cd api/target
java -jar expenses-api-1.0.0-SNAPSHOT.jar
```
Available at: localhost:8081/v1/expenses

## Docker commands
```bash
docker build -t expenses .   
docker images
docker run expenses    
docker tag expenses tomaz12345/expenses   
docker push tomaz12345/expenses  
```
```bash
docker network ls  
docker network rm rso
docker network create rso
docker run -d --name pg-expenses -e POSTGRES_USER=dbuser -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=expenses -p 5432:5432 --network rso postgres:13
docker inspect pg-expenses
docker run -p 8081:8081 --network rso -e KUMULUZEE_DATASOURCES0_CONNECTIONURL=jdbc:postgresql://pg-expenses:5432/expenses expenses
```


## Kubernetes
```bash
kubectl version
kubectl --help
kubectl get nodes
kubectl get services 
kubectl get deployments
kubectl get pods
```
```bash
kubectl create -f k8s\expenses-deployment.yaml 
kubectl apply -f k8s\expenses-deployment.yaml 
```
```bash
kubectl --namespace default scale deployment expenses-deployment --replicas 0
kubectl logs expenses-deployment----
kubectl delete pod expenses-deployment----
```

```bash
kubectl create secret generic pg-pass --from-literal=password=mypassword
kubectl get secrets
kubectl describe secret pg-pass
```