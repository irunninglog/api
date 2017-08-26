touch /opt/irunninglog/api/start

java -Dinstance=IRUNNINGLOGAPI -Dstrava=${IRL_STRAVA} -jar /opt/irunninglog/api/irunninglog.jar > /dev/null 2>&1 &
