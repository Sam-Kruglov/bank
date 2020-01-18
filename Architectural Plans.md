# Architectural Plans
Represents a TODO list of what I want from this project ideally. 
Every section provides a high to low level requirements.

todo: create proper sections

- Reactive Manifesto compliance
- Security, Google login
- ✅ UI
- ✅ GraphQL API
- ✅ Event Sourcing & CQRS
  - ✅ Separate write domain model populating Event Storage
  - ✅ Separate read domain model for each use case building from the Event Storage into NoSQL
- Maybe Graph/Key-Value databases, cache, distributed locking for interesting use cases
- ✅ No external services for local/test runs, i.e. all storage is in-memory etc
- ✅ Reactive programming end to end
- CI
- Actual live webpage
- CD - containerized deploy configuration
- Monitoring

Tech stack:
- ✅ Kotlin
- ✅ Spring all the way: WebFlux, Boot, Data, Security, etc
- ✅ UI: [Vaadin](https://vaadin.com/). 
  - Chose Because I don't know UI stack and this just uses Java. 
  Might go for an actual front end if have enough time/will to learn
- ✅ GraphQL-Java module with [Kickstart](https://github.com/graphql-java-kickstart) Spring Boot project
  - **todo** Wait for / contribute RSocket support
- ✅ Event Sourcing & CQRS abstraction: [Axon Framework](https://axoniq.io/)
- Axon Server CE as Event Storage and Messaging all-in-one solution
- ✅ MongoDB 
- ✅ `docker-compose.yml` for one-click local run
- CircleCI or similar
- Sonar Cloud as a static analysis tool
- ✅ Automatic code formatting config
- Docker and/or Kubernetes and/or CloudFoundry configurations
- Cloud provider: Pivotal/AWS/Google/Azure/Heroku depends on price
- [Open Tracing](https://opentracing.io/) Jaeger
- [Micrometer](https://micrometer.io/) as a metrics framework, can export to a lot of places
- Dashboard solution: DataDog/Prometheus/AWS CloudWatch/Azure Monitor/New Relic depends on price
