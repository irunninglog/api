package com.irunninglog.strava.impl;

import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.runs.IRun;
import com.irunninglog.math.ApiMath;
import com.irunninglog.strava.IStravaRemoteApi;
import com.irunninglog.strava.IStravaShoe;
import javastrava.api.v3.auth.model.Token;
import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.model.StravaActivityUpdate;
import javastrava.api.v3.model.StravaAthlete;
import javastrava.api.v3.model.StravaStatistics;
import javastrava.api.v3.rest.API;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
@Scope("prototype")
class StravaRemoteApiImpl implements IStravaRemoteApi {

    private API api;
    private final HttpHeaders headers = new HttpHeaders();

    private final RestTemplate restTemplate = new RestTemplate();
    private final IFactory factory;
    private final ApiMath apiMath;

    StravaRemoteApiImpl(IFactory factory, ApiMath apiMath) {
        this.factory = factory;
        this.apiMath = apiMath;
    }

    @Override
    public List<IRun> activities(int page, int max) {
        HttpEntity<String> httpEntity = new HttpEntity<>(null, headers);

        ResponseEntity<StravaSummaryActivity[]> responseEntity = restTemplate.exchange("https://www.strava.com/api/v3/athlete/activities?page=" + page + "&per_page=" + max, HttpMethod.GET, httpEntity, StravaSummaryActivity[].class);

        List<IRun> runs = new ArrayList<>();
        if (responseEntity.getBody() != null) {
            for (StravaSummaryActivity activity : responseEntity.getBody()) {
                if (activity.getType().equalsIgnoreCase("run")) {
                    IRun run = factory.get(IRun.class);
                    run.setId(activity.getId());
                    run.setName(activity.getName());
                    run.setShoes(activity.getGear_id());
                    run.setDistance(apiMath.format(new BigDecimal(activity.getDistance()), ApiMath.FORMAT_PLAIN));
                    run.setDuration(activity.getMoving_time());
                    run.setStartTime(activity.getStart_date());
                    runs.add(run);
                }
            }
        }

        return runs;
    }

    @Override
    public IStravaShoe gear(String id) {
        HttpEntity<String> httpEntity = new HttpEntity<>(null, headers);

        ResponseEntity<StravaDetailedGear> responseEntity = restTemplate.exchange("https://www.strava.com/api/v3/gear/" + id, HttpMethod.GET, httpEntity, StravaDetailedGear.class);

        StravaDetailedGear gear = responseEntity.getBody();

        IStravaShoe shoe = null;
        if (gear != null) {
            shoe = factory.get(IStravaShoe.class);
            shoe.setId(gear.getId());
            shoe.setName(gear.getName());
        }

        return shoe;
    }

    @Override
    public final StravaAthlete getAuthenticatedAthlete() {
        return api.getAuthenticatedAthlete();
    }

    @Override
    public final StravaStatistics statistics(Integer id) {
        return api.statistics(id);
    }

    @Override
    public final StravaActivity create(StravaActivity activity) {
        return api.createManualActivity(activity);
    }

    @Override
    public final StravaActivity update(long id, StravaActivityUpdate update) {
        return api.updateActivity((int) id, update);
    }

    @Override
    public final void setToken(String token) {
        Token apiToken = new Token();
        apiToken.setToken(token);
        api = new API(apiToken);

        headers.add("Authorization", token);
    }

}
