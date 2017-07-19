package com.irunninglog.strava.impl;

import com.irunninglog.strava.IStravaApi;
import javastrava.api.v3.auth.AuthorisationService;
import javastrava.api.v3.auth.impl.retrofit.AuthorisationServiceImpl;
import javastrava.api.v3.auth.model.Token;
import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.model.StravaAthlete;
import javastrava.api.v3.model.StravaGear;
import javastrava.api.v3.model.reference.StravaActivityType;
import javastrava.api.v3.rest.API;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
final class StravaApiImpl implements IStravaApi {

    private final int id;
    private final String secret;

    private StravaAthlete athlete;
    private List<StravaActivity> activities;
    private final Map<String, StravaGear> shoes = new HashMap<>();

    @Autowired
    StravaApiImpl(Environment environment) {
        String config = environment.getRequiredProperty("strava");
        String [] tokens = config.split("\\|");
        id = Integer.parseInt(tokens[0]);
        secret = tokens[1];
    }

    @Override
    public Token token(String code) {
        AuthorisationService service = new AuthorisationServiceImpl();
        return service.tokenExchange(id, secret, code);
    }

    @Override
    public synchronized StravaAthlete athlete(String token) {
        if (athlete == null) {
            athlete = api(token).getAuthenticatedAthlete();
        }

        return athlete;
    }

    @Override
    public synchronized List<StravaActivity> activities(String token) {
        if (activities == null) {
            activities = new ArrayList<>();

            int page = 1;
            int count = -1;

            API api = api(token);

            while (count != 0) {
                StravaActivity[] array = api.listAuthenticatedAthleteActivities(null, null, page, 200);

                count = array.length;
                for (StravaActivity activity : array) {
                    if (activity.getType() == StravaActivityType.RUN) {
                        activities.add(activity);
                    }
                }

                page++;
            }
        }

        return activities;
    }

    @Override
    public synchronized StravaGear gear(String token, String id) {
        StravaGear shoe = shoes.get(id);

        if (shoe == null) {
            shoe = api(token).getGear(id);
            shoes.put(id, shoe);
        }

        return shoe;
    }

    private API api(String token) {
        Token apiToken = new Token();
        apiToken.setToken(token);
        return new API(apiToken);
    }

}
