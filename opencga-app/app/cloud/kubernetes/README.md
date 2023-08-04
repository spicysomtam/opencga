# Introduction

This deploys the following components in kubernetes using helm charts. You can choose to deploy everything or individual parts of it. 

The solution is designed for Azure AKS; you can probably modify it for other kubernetes clusters, and even use Minikube, etc, for testing, etc. Note that the deployments focus on using Azure Azurefiles (aka Windows shares) for shared storage, etc, so you may need a different solution for other clouds/kubernetes clusters.

Components you can deploy:
* cert-manager
* iva
* mongodb-operator
* nginx ingress
* opencga (see below for more details)
* solr-operator (not solr the app see below)
* mongodump/restore utilities

The opencga helm chart is a large chart and deploys the following components:
* mongodb as a replicaset (opencga catalog)
* solr (including zookeeper)
* opencga-rest
* opencga-master

# Preparation

You will need a kubernetes cluster and a hadoop cluster (eg AKS and HDInsight in Azure). You can deploy these for Azure from the `cloud/arm-kubernetes` folder.

You will need to run the build for Opencga, preferably selecting a tagged release (or use the SNAPSHOT release in `deveop` branch but this won't have public opencga docker images).

Switch to the `build/cloud/kubernetes` folder; the Helm charts will have the correct `AppVersion` set for the Opencga release (hence why you need to build Opencga).