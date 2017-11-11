# SS-platform-research

Language and platform comparison of Search as a Service implementations.

Repo contains sample RESTful implementations with different tech stacks: 
* ssScala -> Scala + Play framework
* ssJava -> Java + Spring Boot

Criteria for evaluation: 
* scalability (measures) - how easy is it to scale for higher QPS
* Existing knowledge at RB
* speed (non-blocking, async, concurrency support)
* Microservice framework, integration with K8s
* Integration with index (client library to talk to ES or SOLR)
* Libraries for re-ranking and query processing


### ssScala vs ssJava
#### Scalability
Please refer to [the use case of Paypal](https://www.paypal-engineering.com/2016/05/11/squbs-a-new-reactive-way-for-paypal-to-build-applications/): 

> With as little as 8 VMs and 2 vCPU each, applications were able to serve over a billion hits a day.

Play wins.


#### RB Knowledge
Most devs at RB know Java, whereas not many of them have used Scala. Besides, the learning curve of Scala is steep. In terms of framework, both have good routing apis. Play's routing DSL is more flexible compared to Spring Boot's regex impl. Both support dependency injection(runtime and compile time) and aspect orented programming which is very useful for implementing single point error handler, security handler and logging. Spring Boot is more easier to bootstrap a service, very clean project structure.

Java wins.


#### Speed
Play(built on top of akka http api) is non-blocking, which means better use of CPU and high throughput. Scala also has better support for writing async programs. Writing async program in Java feels a bit clunky.

Play wins.


#### Deployment and Integration with K8s
Both have good build tools/plugins to dist docker images.

Tie.


#### Integration Solr or ElasticSearch client
Calling Java client, Solr and ElasticSearch native clients are both implemented in Java, from Scala requires more development effort(a abstraction level to convert Java Types of Scala Types).

Java wins.


### Prerequisite for running sample projects
Make sure to have Java 8 installed. You can download it from here: https://java.com/en/download/
````shell
java -version
java version "1.8.0_151"
Java(TM) SE Runtime Environment (build 1.8.0_151-b12)
Java HotSpot(TM) 64-Bit Server VM (build 25.151-b12, mixed mode)
````

You would need sbt(1.0.3) for compiling and running ssScala in dev mode:
````shell
sbt run
````
Go to [http://localhost:9000/api/search/v1/products/cat](http://localhost:9000/api/search/v1/products/cat)

You would need mvn(3.5.2) for compiling and running ssJava in dev mode:
````shell
mvn spring-boot:run
````
Go to [http://localhost:8080/api/search/v1/products/cat](http://localhost:8080/api/search/v1/products/cat)
