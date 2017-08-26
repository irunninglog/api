touch /opt/irunninglog/start

java -Dinstance=IRUNNINGLOGAPI -Dstrava="1|1" -jar irunninglog-main/target/irunninglog.jar > /dev/null 2>&1 &
#sudo java -Dinstance=IRUNNINGLOGAPI -Dstrava="1|1" -jar /opt/irunninglog/irunninglog-main/target/irunninglog.jar > /tmp/start.log
