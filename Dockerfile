FROM gcr.io/google-appengine/openjdk:11
EXPOSE 8080
COPY ./irunninglog-main/target/irunninglog.jar irunninglog.jar
ENTRYPOINT ["java","-jar","irunninglog.jar"]
