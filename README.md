# hmpps-community-api-wiremock

The purpose of this tool is to bridge the gap between data inconsistenties in community-api and prison-api in the DEV environment. For services which require a delius case which corresponds to a matching nomis case, this tool can be used to mock out the community-api side.

This tool will simulate the "team" structure of the probation staff, and will return staff and offender details using the same contracts and schemas as they would be returned from community-api. It integrates with prison-api directly, reads information, and produces a matching delius record to be returned, using Faker library to mock out some fields: e.g. Case reference number (CRN).

## Getting started

1. You will need to add your user details to this mock, so that when you log in to a service which consumes delius user details, some user information will be returned. Inside `src/main/resources/data.sql`, add a row to the `staff` table containing your username, and email address (you can leave this blank if you want). You will also need to add yourself to a team inside this file too.
2. In delius, offenders are allocated to staff members. In this mock, you can allocate an offender to a user and/or team by adding to a row to `src/main/resources/caseloads.csv` with the following columns:
    - Nomis ID: The prisoner number identifier of the offender from nomis (Required)
    - Team Code: The code the team to allocate the offender to (Required)
    - Username: The username of the user to allocate the offender to (optional, if not provided, then the offender will be assigned to the team but not allocated to a staff member/user)

## Mapping
1. [ModelMapper](http://modelmapper.org/user-manual/) is used to map between the database layer and the response DTOs. If you need to create a new type of response DTO, you need to define how to map the data to that using model mapper config found in `CommunityApiWiremockConfiguration.java`.


## Docker Installation

This service can be run in docker using the following docker-compose config (with environment variables filled in with appropriate values)

```
  hmpps-community-api-wiremock:
    image: quay.io/hmpps/hmpps-community-api-wiremock:latest
    networks:
      - hmpps
    container_name: hmpps-community-api-wiremock
    environment:
      - HMPPS_AUTH_URL=
      - PRISONER_SEARCH_API_URL=
      - SYSTEM_CLIENT_ID=
      - SYSTEM_CLIENT_SECRET=
    ports:
      - "5000:5000"
    healthcheck:
      test: [ 'CMD', 'curl', '-f', 'http://localhost:5000/actuator/health' ]
```

## Running Locally

### Pre-requisites
    - Java 16
    - Maven
    
### Installation and running
1. From the project root, install the required dependencies using the following command:

```bash
mvn clean install
```

2. The service requires the following environment variables to run, so make sure they are present in your environment:
```bash
HMPPS_AUTH_URL=
PRISONER_SEARCH_API_URL=
SYSTEM_CLIENT_ID=
SYSTEM_CLIENT_SECRET=
```

3. To run the service from the command line, run:

```bash
mvn spring-boot:run
```

### Running tests
Tests can be run by using the following command:
```bash
mvn test
```
