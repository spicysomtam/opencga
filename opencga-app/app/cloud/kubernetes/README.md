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

You will need a kubernetes cluster (eg AKS); the hadoop cluster is optional but required for prod deployments (eg HDInsight in Azure). You can deploy these for Azure from the `cloud/arm-kubernetes` folder.

You will need to run the build for Opencga, preferably selecting a tagged release (or use the SNAPSHOT release in the `develop` branch but this won't have public opencga docker images that can be pulled from docker hub).

Switch to the `build/cloud/kubernetes` folder; the Helm charts will have the correct `AppVersion` set for the Opencga release (hence why you need to build Opencga and use the build configuration).

You will need to prepare a `values.yaml` file; you can use the defaults in the `charts\<chart>` folders (append all the `values.yaml` files together). Ideally this `values.yaml` will have configuration for all the charts, but this difficult to get right initially, so copy one of the deployments (eg iva) and use that as a basis for building up the configuration.

When deploying you can deploy everything (`all`) or just one of the charts; probably best to start off with a small chart like `iva`. Note that the `opencga` is a large complex chart so not a good place to start.

As mentioned earlier you will need a kubernetes cluster, and have setup credentials/context to the kubernetes cluster.

# Deploying

Here are the args you can pass to `setup-k8s.sh`:
```
$ cd build/cloud/kubernetes
$  ./setup-k8s.sh 
Missing param --context

Deploy required Helm charts for a fully working OpenCGA installation
 - solr-operator
 - zk-operator
 - mongodb-operator
 - opencga-nginx
 - opencga
 - iva

Usage:   setup-k8s.sh --context <context> [options]

Options:
   * -c     --context             STRING     Kubernetes context
     -n     --namespace           STRING     Kubernetes namespace
   * -f     --values              FILE       Helm values file
     -o     --outdir              DIRECTORY  Output directory where to write the generated manifests. Default: $PWD
            --name-suffix         STRING     Helm deployment name suffix. e.g. '-test' : opencga-nginx-test, opencga-test, iva-test
            --what                STRING     What to deploy. [nginx, iva, opencga, all]. Default: all
            --opencga-conf-dir    DIRECTORY  OpenCGA configuration folder. Default: build/conf/ 
            --keep-tmp-files      FLAG       Do not remove any temporary file generated in the outdir
            --dry-run             FLAG       Simulate an installation.
     -h     --help                FLAG       Print this help
            --verbose             FLAG       Verbose mode. Print debugging messages about the progress.

```
As advised, you will need a pre configured `values.yaml` file and have the kubernetes context set up. In this simple example I am using `minikube`.

Follow these steps; we deploy `iva` as an example into minikube:
```
$ ./setup-k8s.sh -c minikube --what iva --values values.yaml 
# Deploy kubernetes
# Configuring context minikube
Switched to context "minikube".
NAME       STATUS   AGE
minikube   Active   126m
Context "minikube" modified.
# Deploy IVA iva
history.go:56: [debug] getting history for release iva
Release "iva" does not exist. Installing it now.
install.go:200: [debug] Original chart version: ""
install.go:217: [debug] CHART PATH: /media/data/amunro-git/spicysomtam/opencga/build/cloud/kubernetes/charts/iva

client.go:134: [debug] creating 8 resource(s)
wait.go:48: [debug] beginning wait for 8 resources with timeout of 10m0s
ready.go:268: [debug] PersistentVolumeClaim is not bound: minikube/pvc-iva-ivaconf
```

Note this may not go well first time; you may need to debug the deployment, see where its failing, and then update your `values.yaml` or the Helm chart.

```
$ helm list
NAME    NAMESPACE       REVISION        UPDATED                                 STATUS  CHART           APP VERSION
iva     minikube        1               2023-08-04 13:13:04.790202126 +0100 BST failed  iva-0.1.0       2.9.2      

$ kubectl get all
NAME                       READY   STATUS    RESTARTS   AGE
pod/iva-76c7785c48-cngqm   0/1     Pending   0          39s
pod/iva-76c7785c48-j6x86   0/1     Pending   0          39s

NAME          TYPE        CLUSTER-IP     EXTERNAL-IP   PORT(S)   AGE
service/iva   ClusterIP   10.96.122.59   <none>        80/TCP    39s

NAME                  READY   UP-TO-DATE   AVAILABLE   AGE
deployment.apps/iva   0/2     2            0           39s

NAME                             DESIRED   CURRENT   READY   AGE
replicaset.apps/iva-76c7785c48   2         2         0       39s

$ kubectl describe po iva-76c7785c48-cngqm
Name:             iva-76c7785c48-cngqm
Namespace:        minikube
Priority:         0
Service Account:  iva
Node:             <none>
Labels:           app=iva
                  pod-template-hash=76c7785c48
Annotations:      <none>
Status:           Pending
IP:               
IPs:              <none>
Controlled By:    ReplicaSet/iva-76c7785c48
Init Containers:
  init:
    Image:      opencb/iva-app:2.9.2
    Port:       <none>
    Host Port:  <none>
    Command:
      /bin/sh
    Args:
      -c
      ( [ -f /opt/ivaconf/config.js ] && [ "true" != "true" ] ) && echo Configuration already exists || echo "Copy configuration. Overwrite = true" && cp -r /usr/local/apache2/htdocs/iva/conf/* /opt/ivaconf && echo "// Section added by Kubernetes deployment" >> /opt/ivaconf/config.js && echo "//  2023-08-04 13:13:04.796463949 +0100 BST m=+0.077991288" >> /opt/ivaconf/config.js && echo "opencga.host = \"http://opencga.local/opencga\";" >> /opt/ivaconf/config.js&& echo "cellbase.host = \"http://ws.opencb.org/cellbase/\";" >> /opt/ivaconf/config.js && echo "cellbase.version = \"v4\";" >> /opt/ivaconf/config.js
    Environment:  <none>
    Mounts:
      /opt/ivaconf from ivaconf (rw)
      /var/run/secrets/kubernetes.io/serviceaccount from kube-api-access-s84gb (ro)
Containers:
  iva:
    Image:      opencb/iva-app:2.9.2
    Port:       80/TCP
    Host Port:  0/TCP
    Command:
      /bin/sh
    Args:
      -c
      cp -r /opt/ivaconf/* /usr/local/apache2/htdocs/iva/conf && httpd-foreground
    Limits:
      cpu:     1
      memory:  2Gi
    Requests:
      cpu:        100m
      memory:     128Mi
    Environment:  <none>
    Mounts:
      /opt/ivaconf/ from ivaconf (rw)
      /var/run/secrets/kubernetes.io/serviceaccount from kube-api-access-s84gb (ro)
Conditions:
  Type           Status
  PodScheduled   False 
Volumes:
  ivaconf:
    Type:       PersistentVolumeClaim (a reference to a PersistentVolumeClaim in the same namespace)
    ClaimName:  pvc-iva-ivaconf
    ReadOnly:   false
  kube-api-access-s84gb:
    Type:                    Projected (a volume that contains injected data from multiple sources)
    TokenExpirationSeconds:  3607
    ConfigMapName:           kube-root-ca.crt
    ConfigMapOptional:       <nil>
    DownwardAPI:             true
QoS Class:                   Burstable
Node-Selectors:              agentpool=default
                             kubernetes.io/os=linux
                             kubernetes.io/role=agent
Tolerations:                 node.kubernetes.io/not-ready:NoExecute op=Exists for 300s
                             node.kubernetes.io/unreachable:NoExecute op=Exists for 300s
Events:
  Type     Reason            Age   From               Message
  ----     ------            ----  ----               -------
  Warning  FailedScheduling  50s   default-scheduler  0/1 nodes are available: pod has unbound immediate PersistentVolumeClaims. preemption: 0/1 nodes are available: 1 No preemption victims found for incoming pod..
  Warning  FailedScheduling  45s   default-scheduler  0/1 nodes are available: 1 node(s) didn't match Pod's node affinity/selector. preemption: 0/1 nodes are available: 1 Preemption is not helpful for scheduling..
```
