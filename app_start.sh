touch /opt/irunninglog/start

java -Dinstance=IRUNNINGLOGAPI -Dstrava="1|1" -jar /opt/irunninglog/irunninglog.jar > /dev/null 2>&1 &
