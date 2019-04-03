# Fake SMTP Server

*Simple SMTP Server which stores all received emails in an in-memory database*

## Introduction

The root of this project comes from [kurzdigital/fake-smtp-server](https://github.com/kurzdigital/fake-smtp-server).

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
    
    MYSQL_USER          Username for the h2 user, Default: admin
    MYSQL_PASSWORD      Password for the h2 user, Default: Test1234
    
    SMTP_PORT           Port for the SMTP-Server, Default: 5025
    SMTP_USER           Username for the SMTP-Server, Default: admin (optional)
    SMTP_PASSWORD       Password for the SMTP-Server, Default: admin (optional)
    
    MAX_NUMBER_EMAILS   Max. amount of emails stored in the h2, Default: 1000
     
#### HTTP API

/email/count/from/{from} -> get count of received emails from the address
/email/delete -> delete all emails on this server