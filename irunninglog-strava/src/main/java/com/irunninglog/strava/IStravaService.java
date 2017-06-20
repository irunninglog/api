package com.irunninglog.strava;

import com.irunninglog.api.security.AuthnException;
import com.irunninglog.api.security.IUser;

public interface IStravaService {

    IUser userFromCode(String code) throws AuthnException;

    IUser userFromToken(String token) throws AuthnException;

    IStravaAthlete athlete(IUser user);

}
