touch /opt/irunninglog/api/start

java -Dinstance=IRUNNINGLOGAPI -Dstrava="1|1" -jar /opt/irunninglog/api/irunninglog.jar > /dev/null 2>&1 &
