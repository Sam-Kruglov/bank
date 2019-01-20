[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=Sam-Kruglov_bank&metric=coverage)](https://sonarcloud.io/dashboard?id=Sam-Kruglov_bank)

# Bank Service

A sample application that allows create or retrieve one of the available entities:
 
 - User (has many bank accounts)
 - Account (has many cards)
 - Card
 
---

## Module system

The codebase is split across separate jars using Maven module system. 
They are all assembled into a fat jar inside the `spring-boot-app` module.
This allows for a very strict separation of concerns, thus a high maintainability.
For example, domain does not see anything and controller only sees services(repositories are not on the classpath).
For more detailed information, please, read the `<description>` tags of any `pom.xml`.

## Instructions to run

- Navigate to the `spring-boot-app` module
- Execute `mvn spring-boot:run`. 
In case you run with an IDE you may want to run `mvn compile` on the project first for code generation.

## When the application is running

- REST API interactive documentation (Swagger) is at `localhost:8080/swagger-ui.html`
- GraphQL API interactive documentation (GraphiQL) is at `localhost:8080/graphiql`

## Send to SonarQube
- execute `mvn clean verify sonar:sonar -Pcoverage` 
and also supply `sonar.` properties: `projectKey`, `login`, `host.url`, `organization`