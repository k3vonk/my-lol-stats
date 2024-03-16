# Spring Boot App for Riot API

This is a Spring Boot learning project to interact with Riot API in Kotlin.

## Prerequisites

- [Generate your own Riot API](https://developer.riotgames.com/)

## IntelliJ - Getting Started

1. Set up your environment variables for IntelliJ `Application`
```bash
RIOT_APIKEY="Your development API key"
```
2. Run the Spring Boot `Application`

## Sample endpoint execution

```bash
# Open API Spec - Swagger UI
curl -X GET --location "http://localhost:8080/swagger-ui"

# Open API Spec - YAML
curl -X GET --location "http://localhost:8080/api-docs"


curl -X GET --location "http://localhost:8080/api/v1/accounts/YakumoUchiha/EUW"
```

## TODO

- Filter out Aram/Ranked/Normals matches
- Response status support for different issues
- Get champion played count
- Match up comparisons
- Default summoner?
- Advanced OpenApi Spec
- Do some data storage? so we prevent long listed API calls
- Visualization?
