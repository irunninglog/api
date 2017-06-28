package com.irunninglog.strava;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public interface IStravaRun {

    long getId();

    IStravaRun setId(long id);

    ZonedDateTime getStartTime();

    IStravaRun setStartTime(ZonedDateTime startTime);

    LocalDateTime getStartTimeLocal();

    IStravaRun setStartTimeLocal(LocalDateTime startTimeLocal);

    String getTimezone();

    IStravaRun setTimezone(String timezone);

    float getDistance();

    IStravaRun setDistance(float distance);

    String getShoes();

    IStravaRun setShoes(String gear);
}
