# Bank Service

A Web Admin Portal for the whole bank. 
Create customers, watch their transactions in real-time, and maybe something else. Work in progress. 

#### See [Architectural Plans](Architectural%20Plans.md)
 
---

## How to run
Copy-paste commands in order:
1. Build `./mvnw clean install -DskipTests`
2. Launch
    - Backend: `java -jar spring-boot-app/target/spring-boot-app-*.jar`
    - UI: `java -jar ui/target/ui-*.jar`
    

Notes:
 - use `mvnw.cmd` for Windows
 - JDK 11 required
