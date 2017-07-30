package com.irunninglog.strava.impl;

import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.security.AuthnException;
import com.irunninglog.api.security.IUser;
import com.irunninglog.strava.*;
import javastrava.api.v3.auth.model.Token;
import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.model.StravaAthlete;
import javastrava.api.v3.model.StravaGear;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
final class StravaServiceImpl implements IStravaService {

    private final IFactory factory;
    private final IStravaApi api;
    private final StravaApiCache cache;

    @Autowired
    StravaServiceImpl(IFactory factory, IStravaApi api, StravaApiCache cache) {
        this.factory = factory;
        this.api = api;
        this.cache = cache;
    }

    @Override
    public IUser userFromCode(String code) throws AuthnException {
        Token token = api.token(code);

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
    public List<IStravaRun> runs(IUser user) {
        return cache.get(user.getToken()).activities().stream().map(this::fromStravaActivity).collect(Collectors.toList());
    }

    private IStravaRun fromStravaActivity(StravaActivity stravaActivity) {
        return factory.get(IStravaRun.class)
                .setId(stravaActivity.getId())
                .setStartTime(stravaActivity.getStartDate())
                .setStartTimeLocal(stravaActivity.getStartDateLocal())
                .setTimezone(stravaActivity.getTimezone())
                .setDistance(stravaActivity.getDistance())
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

}
