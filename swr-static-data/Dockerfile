FROM openjdk:21-jdk
MAINTAINER andraduradu
VOLUME /tmp
EXPOSE 8900
COPY ./target/*.jar swr-static-data.jar
ENTRYPOINT java $JVM_OPTS -jar /swr-static-data.jar