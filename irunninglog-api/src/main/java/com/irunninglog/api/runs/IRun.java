package com.irunninglog.api.runs;

public interface IRun {

    long getId();

    IRun setId(long id);

    String getName();

    IRun setName(String name);

    String getStartTime();

    IRun setStartTime(String time);

    String getDistance();

    IRun setDistance(String distance);

    int getDuration();

    IRun setDuration(int duration);

    String getShoes();

    IRun setShoes(String gear);

}
