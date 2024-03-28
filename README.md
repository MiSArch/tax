# Catalog Service

The tax service provides the bounded context `Tax`. It is responsible for managing tax rates and their versions.

## Documentation

Detailed information about the catalog service can be found in the [documentation](https://misarch.github.io/docs/docs/dev-manuals/services/catalog).


## Getting started

A development version of the catalog service can be started using docker compose:

```bash
docker-compose -f docker-compose.dev.yml up --build
```
A GraphiQL interface is available at http://localhost:8080/graphiql to interact with the service.

> [!NOTE]
> Running the service locally through the IDE is neither recommended nor supported.