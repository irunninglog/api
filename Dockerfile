FROM openjdk:9-jre-alpine
EXPOSE 8080
COPY ./irunninglog-main/target/irunninglog.jar irunninglog.jar
ENTRYPOINT ["java","-jar","irunninglog.jar"]
