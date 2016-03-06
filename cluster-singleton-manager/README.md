# Akka Cluster - Cluster Singleton Manager tutorial
Tutorial on how to use an Akka Cluster Singleton Manager to deal with background processing in a service oriented architecture.

## How to run the service
Clone the repository:
```
> git clone https://github.com/DanielaSfregola/akka-tutorials.git
```

Get to the `cluster-singleton-manager` folder:
```
> cd cluster-singleton-manager
```
For each service to run locally, set the port for the API and the remote port for the Akka Cluster Node:
```
> export PORT=5000
> export REMOTE=2551
```
Run the service:
```
> sbt run
```
## Check that the API is running
If the service is running `http://localhost:<PORT>/ping` should return `pong`.

## Usage
Look at [this article](http://danielasfregola.com/2016/02/21/background-processing-with-akka-cluster-singletonclustermanager/) that explains what this service is doing.
