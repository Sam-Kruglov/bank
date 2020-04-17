FROM maven:3.6.3-jdk-11-slim

WORKDIR /app

#install frontend dependencies
ADD ui/pom.xml ui/
#https://github.com/vaadin/flow/blob/666fe4e83e9ca4d91ce91dad0499437dbcde9f90/flow-server/src/main/java/com/vaadin/flow/server/frontend/FrontendTools.java#L64
RUN cd ui && mvn com.github.eirslett:frontend-maven-plugin:1.7.6:install-node-and-npm -DnodeVersion="v12.14.0"
RUN cd ui && mvn com.vaadin:vaadin-maven-plugin:prepare-frontend
RUN cd ui && mvn com.vaadin:vaadin-maven-plugin:build-frontend

# download maven dependencies. This will only be repeated if poms are changed
ADD pom.xml ./
ADD api/pom.xml api/
ADD domain/pom.xml domain/
ADD domain/read/pom.xml domain/read/
ADD domain/core/pom.xml domain/core/
ADD domain/core/bank-shared-messages/pom.xml domain/core/bank-shared-messages/
ADD domain/core/internal/pom.xml domain/core/internal/
ADD spring-boot-app/pom.xml spring-boot-app/
RUN mvn dependency:go-offline --fail-never

COPY . ./
RUN mvn clean package -Dmaven.test.skip=true

#strip version from all runnable jars
#referencing "*" from docker-compose doesn't work for some reason
RUN mv spring-boot-app/target/spring-boot-app-*.jar backend.jar
RUN mv ui/target/ui-*.jar ui.jar