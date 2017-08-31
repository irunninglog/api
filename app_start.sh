touch /opt/irunninglog/api/start

export IRL_STRAVA=`cat /home/ec2-user/.strava`

java -Dlogback.configurationFile=/opt/irunninglog/api/logback.xml -Dinstance=IRUNNINGLOGAPI -Dstrava=${IRL_STRAVA} -jar /opt/irunninglog/api/irunninglog.jar > /dev/null 2>&1 &
