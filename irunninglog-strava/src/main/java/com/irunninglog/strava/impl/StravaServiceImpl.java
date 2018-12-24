package com.irunninglog.strava.impl;

import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.runs.IRun;
import com.irunninglog.api.security.IUser;
import com.irunninglog.date.ApiDate;
import com.irunninglog.math.ApiMath;
import com.irunninglog.strava.*;
import javastrava.api.v3.auth.model.Token;
import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.model.StravaActivityUpdate;
import javastrava.api.v3.model.reference.StravaActivityType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
final class StravaServiceImpl implements IStravaService {

    private final IFactory factory;
    private final IStravaTokenExchange exchange;
    private final IStravaSessionCache cache;
    private final ApiMath apiMath;
    private final ApiDate apiDate;

    @Autowired
    StravaServiceImpl(IFactory factory, IStravaTokenExchange exchange, IStravaSessionCache cache, ApiMath apiMath, ApiDate apiDate) {
        this.factory = factory;
        this.exchange = exchange;
        this.cache = cache;
        this.apiMath = apiMath;
        this.apiDate = apiDate;
    }

    @Override
    public IUser userFromCode(String code) {
        Token token = exchange.token(code);

        return factory.get(IUser.class)
                .setId(token.getAthlete().getId())
                .setUsername(token.getAthlete().getEmail())
                .setToken(token.getToken());
    }

    @Override
    public IUser userFromToken(String token) {
        IStravaAthlete athlete = cache.create(token).athlete();

        return factory.get(IUser.class)
                .setId(athlete.getId())
                .setUsername(athlete.getEmail())
                .setToken(token);
    }

    @Override
    public IStravaAthlete athlete(IUser user) {
        IStravaAthlete stravaAthlete = cache.get(user.getToken()).athlete();

        return factory.get(IStravaAthlete.class)
                .setId(stravaAthlete.getId())
                .setFirstname(stravaAthlete.getFirstname())
                .setLastname(stravaAthlete.getLastname())
                .setEmail(stravaAthlete.getEmail())
                .setAvatar(stravaAthlete.getAvatar());
    }

    @Override
    public List<IRun> runs(IUser user) {
        return cache.get(user.getToken()).activities();
    }

    @Override
    public List<IStravaShoe> shoes(IUser user) {
        IStravaAthlete stravaAthlete = cache.get(user.getToken()).athlete();

        List<IStravaShoe> shoes = new ArrayList<>(stravaAthlete.getShoes().size());
        for (IStravaShoe gear : stravaAthlete.getShoes()) {
            IStravaShoe shoe = cache.get(user.getToken()).gear(gear.getId());

            if (shoe != null) {
                shoes.add(shoe);
            }
        }

        return shoes;
    }

    @Override
    public IRun create(IUser user, IRun run) {
        StravaActivity before = runToActivity(run);

        StravaActivity after = cache.get(user.getToken()).create(before);

        return activityToRun(after);
    }

    @Override
    public IRun update(IUser user, IRun run) {
        return activityToRun(cache.get(user.getToken()).update(run.getId(), runToActivityUpdate(run)));
    }

    private StravaActivityUpdate runToActivityUpdate(IRun run) {
        StravaActivityUpdate update = new StravaActivityUpdate();
        update.setName(run.getName());
        update.setGearId(run.getShoes());
        update.setType(StravaActivityType.RUN);
        return update;
    }

    private StravaActivity runToActivity(IRun run) {
        StravaActivity activity = new StravaActivity();
        activity.setDistance(apiMath.meters(new BigDecimal(run.getDistance())).floatValue());
        activity.setMovingTime(run.getDuration());
        activity.setElapsedTime(run.getDuration());
        activity.setStartDate(apiDate.parseZonedDate(run.getStartTime()));
        activity.setType(StravaActivityType.RUN);
        activity.setName(run.getName());
        // TODO - Set shoes and athlete on activity
//        activity.setAthlete(cache.get(user.getToken()).athlete());
//        if (run.getShoes() != null && !run.getShoes().isEmpty()) {
            //activity.setGear(cache.get(user.getToken()).gear(run.getShoes()));
//        }
        return activity;
    }

    private IRun activityToRun(StravaActivity activity) {
        IRun run = factory.get(IRun.class);
        run.setId(activity.getId());
        run.setName(activity.getName());
        run.setShoes(activity.getGear() == null ? null : activity.getGear().getId());
        run.setDistance(apiMath.format(apiMath.round(apiMath.miles(BigDecimal.valueOf(activity.getDistance()))), ApiMath.FORMAT_PLAIN));
        run.setDuration(activity.getMovingTime() == null ? 0 : activity.getMovingTime());
        run.setStartTime(apiDate.format(activity.getStartDate()));
        return run;
    }

}
