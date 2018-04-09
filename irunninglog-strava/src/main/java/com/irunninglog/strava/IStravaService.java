package com.irunninglog.strava;

import com.irunninglog.api.runs.IRun;
import com.irunninglog.api.security.AuthnException;
import com.irunninglog.api.security.IUser;

import java.util.List;

public interface IStravaService {

    IUser userFromCode(String code) throws AuthnException;

    IUser userFromToken(String token) throws AuthnException;

    IStravaAthlete athlete(IUser user);

    List<IRun> runs(IUser user);

    List<IStravaShoe> shoes(IUser user);

    IRun create(IUser user, IRun run);

    IRun update(IUser user, IRun run);

}
