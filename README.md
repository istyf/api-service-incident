# Incident

## Leverantör
Sundsvalls Kommun

## Beskrivning
Tar emot felanmälan, tillhandahåller data för nya felanmälningar samt status för inkomna felanmälningar.

Aktuell version: integration mot ISYcase för SBK-relaterade felanmälningar - i övrigt så skickas felanmälan vidare via e-post till kundtjänst (digilogt)

## Tekniska detaljer

### Konfiguration

Konfiguration sker i filen `src/main/resources/application.properties` genom att sätta nedanstående properties till önskade värden:

|Property|Beskrivning|
|---|---|
|`email.email-name`|Namn på avsändare för utgående e-post
|`email.email-address`|E-postadress för avsändare för utgående e-post
|`email.msva-email-address`|E-postadress för felanmälningar till MSVA
|`email.msva-email-subject`|Standardvärde för mailtitel för felanmälningar till MSVA
|`email.email-recipient-address`|E-postadress för generella felanmälningar
|`integration.messaging.client.url`|API-URL till messaging-tjänsten
|`integration.messaging.oauth2.token-uri`|URL för att hämta OAuth2-token för messaging-tjänsten
|`integration.messaging.oauth2.client-id`|OAuth2-klient-id för messaging-tjänsten
|`integration.messaging.oauth2.client-secret`|OAuth2-klient-nyckel messaging-tjänsten
|`integration.lifebuoy.url`|API-URL till ISYcase
|`integration.lifebuoy.apikey`|Api-nyckel för ISYcase

### Paketera och starta tjänsten

Paketera tjänsten som en körbar JAR-fil genom:

```
mvn package
```

Starta med:

```
java -jar target/api-service-incident-<VERSION>.jar
```

### Bygga och starta tjänsten med Docker

Bygg en Docker-image av tjänsten:

```
mvn spring-boot:build-image
```

Starta en Docker-container:

```
docker run -i --rm -p 8080:8080 evil.sundsvall.se/ms-incident:latest
```

## Status
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=Sundsvallskommun_api-service-incident&metric=alert_status)](https://sonarcloud.io/summary/overall?id=Sundsvallskommun_api-service-incident)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=Sundsvallskommun_api-service-incident&metric=reliability_rating)](https://sonarcloud.io/summary/overall?id=Sundsvallskommun_api-service-incident)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=Sundsvallskommun_api-service-incident&metric=security_rating)](https://sonarcloud.io/summary/overall?id=Sundsvallskommun_api-service-incident)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=Sundsvallskommun_api-service-incident&metric=sqale_rating)](https://sonarcloud.io/summary/overall?id=Sundsvallskommun_api-service-incident)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=Sundsvallskommun_api-service-incident&metric=vulnerabilities)](https://sonarcloud.io/summary/overall?id=Sundsvallskommun_api-service-incident)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=Sundsvallskommun_api-service-incident&metric=bugs)](https://sonarcloud.io/summary/overall?id=Sundsvallskommun_api-service-incident)

## 
Copyright (c) 2021 Sundsvalls kommun
