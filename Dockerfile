FROM maven:3.6.3-jdk-11-slim

WORKDIR /app

# layer one. Download maven dependencies. This will only be repeated if poms are changed
# todo any better way to copy poms?
ADD pom.xml ./
ADD api/pom.xml api/
ADD domain/pom.xml domain/
ADD domain/read/pom.xml domain/read/
ADD domain/core/pom.xml domain/core/
ADD domain/core/bank-shared-messages/pom.xml domain/core/bank-shared-messages/
ADD domain/core/internal/pom.xml domain/core/internal/
ADD spring-boot-app/pom.xml spring-boot-app/
ADD ui/pom.xml ui/
RUN mvn dependency:go-offline --fail-never

#layer two. install npm. command copied from vaadin console output when launch failed
RUN mvn com.github.eirslett:frontend-maven-plugin:1.7.6:install-node-and-npm -DnodeVersion="v12.14.0" --projects .

#layer three. build
#todo still downloads a little bit
COPY . ./
RUN mvn clean package -Dmaven.test.skip=true

#strip version from all runnable jars
#referencing "*" from docker-compose doesn't work for some reason
RUN mv spring-boot-app/target/spring-boot-app-*.jar backend.jar
RUN mv ui/target/ui-*.jar ui.jar