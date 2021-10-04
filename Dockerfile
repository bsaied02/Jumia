# docker build -t phone-app .
# docker run --rm -it -p 8080:8080 phone-app

FROM openjdk:8-jdk-alpine
COPY target/jumia-services-0.0.1-SNAPSHOT.jar jumia-services-0.0.1-SNAPSHOT.jar
COPY sample.db sample.db
ENTRYPOINT ["java","-jar","/jumia-services-0.0.1-SNAPSHOT.jar"]


