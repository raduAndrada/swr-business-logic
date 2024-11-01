FROM openjdk:21-jdk
MAINTAINER andraduradu
VOLUME /tmp
EXPOSE 8900
COPY ./target/*.jar swr-static-data.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=prod","-jar","/swr-static-data.jar"]