package com.irunninglog.strava.impl;

import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.runs.IRun;
import com.irunninglog.api.security.AuthnException;
import com.irunninglog.api.security.IUser;
import com.irunninglog.strava.IStravaAthlete;
import com.irunninglog.strava.IStravaService;
import com.irunninglog.strava.IStravaShoe;
import com.irunninglog.strava.IStravaTokenExchange;
import javastrava.api.v3.auth.model.Token;
import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.model.StravaActivityUpdate;
import javastrava.api.v3.model.StravaAthlete;
import javastrava.api.v3.model.StravaGear;
import javastrava.api.v3.model.reference.StravaActivityType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
final class StravaServiceImpl implements IStravaService {

    private final IFactory factory;
    private final IStravaTokenExchange exchange;
    private final StravaSessionCache cache;

    @Autowired
    StravaServiceImpl(IFactory factory, IStravaTokenExchange exchange, StravaSessionCache cache) {
        this.factory = factory;
        this.exchange = exchange;
        this.cache = cache;
    }

    @Override
    public IUser userFromCode(String code) throws AuthnException {
        Token token = exchange.token(code);

        return factory.get(IUser.class)
                .setId(token.getAthlete().getId())
                .setUsername(token.getAthlete().getEmail())
                .setToken(token.getToken());
    }

    @Override
    public IUser userFromToken(String token) throws AuthnException {
        StravaAthlete athlete = cache.create(token).athlete();

        return factory.get(IUser.class)
                .setId(athlete.getId())
                .setUsername(athlete.getEmail())
                .setToken(token);
    }

    @Override
    public IStravaAthlete athlete(IUser user) {
        StravaAthlete stravaAthlete = cache.get(user.getToken()).athlete();

        return factory.get(IStravaAthlete.class)
                .setId(stravaAthlete.getId())
                .setFirstname(stravaAthlete.getFirstname())
                .setLastname(stravaAthlete.getLastname())
                .setEmail(stravaAthlete.getEmail())
                .setAvatar(stravaAthlete.getProfileMedium());
    }

    @Override
    public List<IRun> runs(IUser user) {
        return cache.get(user.getToken()).activities().stream().map(this::fromStravaActivity).collect(Collectors.toList());
    }

    // TODO - Date and time functions should move to API package
    private IRun fromStravaActivity(StravaActivity stravaActivity) {
        return factory.get(IRun.class)
                .setId(stravaActivity.getId())
                .setStartTime(stravaActivity.getStartDate().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME))
                .setDistance(DecimalFormat.getInstance().format(BigDecimal.valueOf(stravaActivity.getDistance()).setScale(1, RoundingMode.DOWN)))
                .setDuration(stravaActivity.getMovingTime())
                .setShoes(stravaActivity.getGearId());
    }

    @Override
    public List<IStravaShoe> shoes(IUser user) {
        StravaAthlete stravaAthlete = cache.get(user.getToken()).athlete();

        List<IStravaShoe> shoes = new ArrayList<>(stravaAthlete.getShoes().size());
        for (StravaGear gear : stravaAthlete.getShoes()) {
            StravaGear full = cache.get(user.getToken()).gear(gear.getId());

            if (full != null) {
                shoes.add(factory.get(IStravaShoe.class)
                        .setId(full.getId())
                        .setName(full.getName().replace(full.getBrandName() + " ", "").replace(full.getModelName() + " ", ""))
                        .setBrand(full.getBrandName())
                        .setModel(full.getModelName())
                        .setDescription(full.getDescription())
                        .setDistance(full.getDistance())
                        .setPrimary(full.getPrimary()));
            }
        }

        return shoes;
    }

    @Override
    public IRun create(IUser user, IRun run) {
        return activityToRun(cache.get(user.getToken()).create(runToActivity(user, run)));
    }

    @Override
    public IRun update(IUser user, IRun run) {
        return activityToRun(cache.get(user.getToken()).update(run.getId(), runToActivityUpdate(run)));
    }

    private StravaActivityUpdate runToActivityUpdate(IRun run) {
        throw new UnsupportedOperationException("runToActivityUpdate: " + run);
    }

    private StravaActivity runToActivity(IUser user, IRun run) {
        StravaActivity activity = new StravaActivity();
        activity.setType(StravaActivityType.RUN);
        activity.setAthlete(cache.get(user.getToken()).athlete());
        activity.setName(run.getName());
        if (run.getShoes() != null && !run.getShoes().isEmpty()) {
            activity.setGear(cache.get(user.getToken()).gear(run.getShoes()));
        }

        return activity;
    }

    private IRun activityToRun(StravaActivity run) {
        throw new UnsupportedOperationException("activityToRun: " + run);
    }

}
