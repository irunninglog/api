package com.irunninglog.strava;

import com.irunninglog.api.security.AuthnException;
import com.irunninglog.api.security.IUser;

import java.util.List;

public interface IStravaService {

    IUser userFromCode(String code) throws AuthnException;

    IUser userFromToken(String token) throws AuthnException;

    IStravaAthlete athlete(IUser user);

    List<IStravaRun> runs(IUser user);

    List<IStravaShoe> shoes(IUser user);

}
