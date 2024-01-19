# RSO: Expenses microservice

## Build and run commands
```bash
mvn clean package
docker build -t expenses .   
docker tag expenses tomaz12345/expenses:v1.2
docker push tomaz12345/expenses:v1.2
docker network ls  
docker network rm rso
docker network create rso
docker run -d --name pg-expenses -e POSTGRES_USER=dbuser -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=expenses -p 5432:5432 --network rso postgres:13 #docker inspect pg-expenses
docker run -p 8080:8080 --network rso -e KUMULUZEE_DATASOURCES0_CONNECTIONURL=jdbc:postgresql://pg-expenses:5432/expenses expenses
```
Available at: localhost:8080/v1/expenses

## GraphQL
```graphql
query MyQuery {
  allExpenses(pagination: {offset: 0, limit: 10},
    						sort: {fields: [{field: "kind", order: ASC}]}) {
    result {
    	expenseId
    	description
      dateOccurrence
    }
    pagination {
      offset
      limit
      total
    }
  }
}

query MyQuery {
  expenses(id: 1) {
    dateOccurrence
    description
    expenseId
    kind
  }
}

mutation addImageMetadata {
  addExpense(expense: {dateOccurrence: "2023-11-27T16:36:38Z", description: "Uploaded via GraphQL"}){
    expenseId
    kind
    description
  }
}
```
Available at localhost:8080/graphiql/

## Kubernetes
```bash
kubectl version
kubectl --help
kubectl get nodes
kubectl get services 
kubectl get deployments
kubectl get pods --output=wide
```
```bash
kubectl create -f k8s\expenses-deployment.yaml 
kubectl apply -f k8s\expenses-deployment.yaml 
```
```bash
kubectl --namespace default scale deployment expenses-deployment --replicas 0
kubectl logs expenses-deployment----
```
```bash
kubectl apply -f .\expences\k8s\expenses-deployment.yaml
kubectl apply -f .\statistics\k8s\statistics-deployment.yaml
kubectl apply -f .\expences\k8s\ingress.yaml
```
```bash
kubectl delete --all deployments
kubectl delete --all pods
kubectl delete --all services
kubectl delete --all ingress
```

```bash
kubectl create secret generic pg-pass --from-literal=password=mypassword
kubectl get secrets
kubectl describe secret pg-pass
```
```bash
kubectl get service expenses --watch
```