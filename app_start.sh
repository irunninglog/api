touch /opt/irunninglog/api/start

java -Dlogback.configurationFile=/opt/irunninglog/api/logback.xml -Dinstance=IRUNNINGLOGAPI -Dstrava="1|1" -jar /opt/irunninglog/api/irunninglog.jar > /dev/null 2>&1 &
