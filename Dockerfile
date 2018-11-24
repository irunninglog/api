FROM gcr.io/google-appengine/openjdk:8
EXPOSE 8080
COPY ./irunninglog-main/target/irunninglog.jar irunninglog.jar
ENTRYPOINT ["java", "-Dstrava=2|2","-jar","irunninglog.jar"]
