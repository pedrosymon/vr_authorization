FROM amazoncorretto:11-alpine-jdk
MAINTAINER vr.com.br
COPY target/authorizator-1.0.0.jar
ENTRYPOINT ["java","-jar","/authorizator-1.0.0.jar"]