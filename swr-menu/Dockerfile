FROM openjdk:21-jdk
MAINTAINER andraduradu
VOLUME /tmp
EXPOSE 8901
COPY ./target/*.jar swr-menu.jar
ENTRYPOINT ["java","-jar","/swr-menu.jar"]