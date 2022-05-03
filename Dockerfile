FROM maven:3.8.3-openjdk-16 as build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean install -Djdk.lang.Process.launchMechanism=vfork

FROM openjdk:16-jdk-slim
COPY --from=build /home/app/target/hmpps-community-api-wiremock-1.0.jar /usr/local/lib/app.jar
ENTRYPOINT ["java","-jar","/usr/local/lib/app.jar"]
