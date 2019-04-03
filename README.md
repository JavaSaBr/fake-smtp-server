# Fake SMTP Server

*Simple SMTP Server which stores all received emails in an in-memory database and renders the emails in a web interface*

## Introduction

The root of this project comes from [kurzdigital/fake-smtp-server](https://github.com/kurzdigital/fake-smtp-server).
It is extended by a delete function for the emails and the it is secured behind a login.

## Prerequisites

1. docker, docker-compose
2. JDK 11

## Getting started

### Start everything

```bash
./gradlew build
docker build -t fake-smtp-server .
```

### Configuration

#### Environment variables

    SERVER-PORT         Port for the Web-UI, Default: 5080
    
    APP_USER            Username for the Web-UI, Default: admin
    APP_PASSWORD        Password for the Web-UI, Default: admin
    
    MYSQL_USER          Username for the h2 user, Default: admin
    MYSQL_PASSWORD      Password for the h2 user, Default: Test1234
    
    SMTP_PORT           Port for the SMTP-Server, Default: 5025
    SMTP_USER           Username for the SMTP-Server, Default: admin (optional)
    SMTP_PASSWORD       Password for the SMTP-Server, Default: admin (optional)
    
    MAX_NUMBER_EMAILS   Max. amount of emails stored in the h2, Default: 1000
     
#### Property Configuration

        
To bind the fakesmtp to another address remove the `#` at the `fakesmtp`-Section for `bindAddress` and add the desired address
    
## Next Tasks

- Refactoring (remove unnecessary gradle tasks, ...)
- Connect keycloak as authentication
