# Spring Boot App for Riot API

This is a Spring Boot learning project to interact with Riot API in Kotlin.

## Prerequisites

- [Generate your own Riot API](https://developer.riotgames.com/)
- [JDK 21](https://sdkman.io/jdks#tem)
- Docker runtime e.g. [Docker Desktop](https://www.docker.com/products/docker-desktop/) setup automatically brings a runtime

## IntelliJ - Getting Started

1. Set up your environment variables for IntelliJ `Application`
```bash
RIOT_APIKEY="Your development API key"
```
2. Run the Spring Boot `Application`

## Sample endpoint execution

My APIs query parameters use the [Game Constants](https://developer.riotgames.com/docs/lol#working-with-lol-apis_game-constants) from Riot's documentation

```bash
# Open API Spec - Swagger UI
curl -X GET --location "http://localhost:8080/swagger"

# Open API Spec - YAML
curl -X GET --location "http://localhost:8080/api-docs"


curl -X GET --location "http://localhost:8080/api/v1/accounts/YakumoUchiha/EUW"
```

## TODO

- Response status support for different issues
- Match up comparisons
- Default summoner?
- Advanced OpenApi Spec
- Do some data storage? so we prevent long listed API calls
- Visualization?
