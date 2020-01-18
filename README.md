# Bank Service

A Web Admin Portal for the whole bank. 
Create customers, watch their transactions in real-time, and maybe something else. Work in progress. 

#### See [Architectural Plans](Architectural%20Plans.md)
 
---

## How to run

The UI will be accessible under [`localhost`](http://localhost).

Choose your way below.

### Docker
The easiest one-click way to do it. Will take a while.

1. [Install Docker](https://www.docker.com/products/docker-desktop)
2. Run [`docker-compose up`](docker-compose.yml). 
It will download all the libraries (into your `~/.m2` directory) and start everything.

### JDK 11
The fastest way but requires some typing.

Copy-paste commands in order:
1. Build `./mvnw clean install -Dmaven.test.skip=true`. Use `mvnw.cmd` for Windows
2. Launch
    - Backend: `java -jar spring-boot-app/target/spring-boot-app-*.jar`
    - UI (may fail and ask you run a `mvn` command first): `java -jar ui/target/ui-*.jar`

### IDE
The best way. Requires to set up the project.
1. Configure JDK 11 and Maven
2. Run Spring Boot applications (backend and ui)