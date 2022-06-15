# dev tools

## install redis-cache

Using helm

```bash
helm repo add bitnami https://charts.bitnami.com/bitnami
helm install student-cache bitnami/redis --values redis-cache.yaml
```

proxy redis-cache

```bash
kubectl port-forward service/student-cache-redis-master 6379:6379
```

## install redis-commander
Setup redis-commander:

```bash
kubectl apply -f redis-commander-deployment.yaml
```

proxy redis-commander

```bash
kubectl port-forward deployment/redis-commander 8081:8081
```

## install mariadb

```bash
helm install maria bitnami/mariadb
```

Hent passordet:

```bash
kubectl get secret --namespace mariadb maria-mariadb -o jsonpath="{.data.mariadb-root-password}" | base64 -d
```