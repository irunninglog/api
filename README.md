# irunninglog-java

[![Build Status](https://travis-ci.org/irunninglog/api.svg?branch=master)](https://travis-ci.org/irunninglog/api.svg?branch=master)

## iRunningLog Java (Spring + Vert.x) API

### Kubernetes Setup

#### Set Configs 
`gcloud config set compute/zone us-central1-b`
`gcloud config set project mimetic-setup-212600`

#### Create Cluster
`gcloud container clusters create irunninglog-api --num-nodes=1`

#### Get Required Credentials
`gcloud container clusters get-credentials irunninglog-api`

#### Create API Deployment
`kubectl apply -f deployment.yaml`

#### Create API Load Balancer Service
`kubectl apply -f service.yaml`
