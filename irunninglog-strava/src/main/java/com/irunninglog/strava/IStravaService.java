package com.irunninglog.strava;

import com.irunninglog.api.security.AuthnException;
import com.irunninglog.api.security.IUser;
import com.irunninglog.api.shoes.IShoe;

import java.util.List;

public interface IStravaService {

    IUser userFromCode(String code) throws AuthnException;

    IUser userFromToken(String token) throws AuthnException;

    IStravaAthlete athlete(IUser user);

    List<IStravaRun> runs(IUser user);

    List<IShoe> shoes(IUser user);

}
