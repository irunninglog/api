package com.irunninglog.api.runs;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public interface IRun {

    int getId();

    IRun setId(int id);

    ZonedDateTime getStartTime();

    IRun setStartTime(ZonedDateTime startTime);

    LocalDateTime getStartTimeLocal();

    IRun setStartTimeLocal(LocalDateTime startTimeLocal);

    String getTimezone();

    IRun setTimezone(String timezone);

    float getDistance();

    IRun setDistance(float distance);

    String getShoes();

    IRun setShoes(String gear);
}
