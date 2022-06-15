#!/usr/bin/env bash

docker build -t vanillaslice/multi-client:latest -t vanillaslice/multi-client:$SHA -f ./client/Dockerfile ./client
docker build -t vanillaslice/multi-server:latest -t vanillaslice/multi-server:$SHA -f ./server/Dockerfile ./server
docker build -t vanillaslice/multi-worker:latest -t vanillaslice/multi-worker:$SHA -f ./worker/Dockerfile ./worker

docker push vanillaslice/multi-client:latest
docker push vanillaslice/multi-server:latest
docker push vanillaslice/multi-worker:latest
docker push vanillaslice/multi-client:$SHA
docker push vanillaslice/multi-server:$SHA
docker push vanillaslice/multi-worker:$SHA

kubectl apply -f k8s

kubectl set image deployments/client-deployment client=vanillaslice/multi-client:$SHA
kubectl set image deployments/server-deployment server=vanillaslice/multi-server:$SHA
kubectl set image deployments/worker-deployment worker=vanillaslice/multi-worker:$SHA
