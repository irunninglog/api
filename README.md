# irunninglog-java

[![Build Status](https://travis-ci.org/irunninglog/api.svg?branch=master)](https://travis-ci.org/irunninglog/api.svg?branch=master)

## iRunningLog Java (Spring + Vert.x) API

### Kubernetes Setup

#### Set Configs 
```
gcloud config set compute/zone us-central1-b
gcloud config set project mimetic-setup-212600
```

#### Create Cluster
`gcloud container clusters create irunninglog-api --num-nodes=1`

#### Get Required Credentials
`gcloud container clusters get-credentials irunninglog-api`

#### Create Strava Secret
Create local text file 'strava' with contents of the Strava secret

`kubectl create secret generic strava-secret --from-file=strava`

#### Create API Load Balancer Service
`kubectl apply -f service.yaml`

#### Create API Deployment
`kubectl apply -f deployment.yaml`

#### Check POD Status
`kubectl get pods`

```
NAME                              READY     STATUS    RESTARTS   AGE
irunninglog-api-cf54bf6cf-lj7vl   1/1       Running   0          14s
```
#### Check Service Status
`kubectl get service irunninglog-api -o yaml`

```
apiVersion: v1
kind: Service
metadata:
  annotations:
    kubectl.kubernetes.io/last-applied-configuration: |
      {"apiVersion":"v1","kind":"Service","metadata":{"annotations":{},"name":"irunninglog-api","namespace":"default"},"spec":{"ports":[{"port":80,"protocol":"TCP","targetPort":8080}],"selector":{"app":"irunninglog-api"},"type":"LoadBalancer"}}
  creationTimestamp: 2018-11-24T21:14:11Z
  name: irunninglog-api
  namespace: default
  resourceVersion: "747"
  selfLink: /api/v1/namespaces/default/services/irunninglog-api
  uid: e37d5222-f02d-11e8-b586-42010af00104
spec:
  clusterIP: 10.31.254.224
  externalTrafficPolicy: Cluster
  ports:
  - nodePort: 31470
    port: 80
    protocol: TCP
    targetPort: 8080
  selector:
    app: irunninglog-api
  sessionAffinity: None
  type: LoadBalancer
status:
  loadBalancer:
    ingress:
    - ip: 35.232.61.143
```
