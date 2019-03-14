package com.irunninglog.spring.strava;

import com.irunninglog.api.athletes.IAthlete;
import com.irunninglog.api.runs.IRun;
import com.irunninglog.api.shoes.IShoe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StravaMockRestTemplate extends RestTemplate {

    private static final Logger LOG = LoggerFactory.getLogger(StravaMockRestTemplate.class);

    private DataObjectDetailedAthlete stravaAthlete;
    private final Map<String, DataObjectDetailedGear> stravaShoes = new HashMap<>();
    private final List<DataObjectSummaryActivity> stravaActivities = new ArrayList<>();

    public void setAthlete(IAthlete athlete) {
        stravaAthlete = new DataObjectDetailedAthlete();
        stravaAthlete.setId(athlete.getId());
        stravaAthlete.setFirstName(athlete.getFirstname());
        stravaAthlete.setLastName(athlete.getLastname());
        stravaAthlete.setEmail(athlete.getEmail());
        stravaAthlete.setProfileMedium(athlete.getAvatar());
    }

    public void setRuns(IRun... runs) {
        for (IRun run : runs) {
            DataObjectSummaryActivity activity = new DataObjectSummaryActivity();
            activity.setDistance(Float.parseFloat(run.getDistance().replace(" mi", "")));
            activity.setGearId(run.getShoes());
            activity.setId(run.getId());
            activity.setMovingTime(run.getDuration());
            activity.setType("Run");
            activity.setStartDate(run.getStartTime());

            stravaActivities.add(activity);
        }
    }

    public void setShoes(IShoe... shoes) {
        for (IShoe shoe : shoes) {
            DataObjectDetailedGear stravaShoe = new DataObjectDetailedGear();
            stravaShoe.setId(shoe.getId());
            stravaShoe.setName(shoe.getName());
            stravaShoe.setPrimary(shoe.isPrimary());
            stravaShoe.setBrandName(shoe.getBrand());
            stravaShoe.setModelName(shoe.getModel());
            stravaShoe.setDescription(shoe.getDescription());
            stravaShoe.setDistance(Float.parseFloat(shoe.getDistance().replace(" mi", "")));

            stravaAthlete.getShoes().add(stravaShoe);
            stravaShoes.put(shoe.getId(), stravaShoe);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> ResponseEntity<T> exchange(String url, HttpMethod method, HttpEntity<?> requestEntity, Class<T> responseType, Object... uriVariables) throws RestClientException {
        LOG.info("exchange:{}:{}:{}:{}", url, stravaAthlete, stravaShoes.size(), stravaActivities.size());

        if ("https://www.strava.com/api/v3/athlete".equals(url)) {
            return new ResponseEntity(stravaAthlete, HttpStatus.OK);
        } else if (url.startsWith("https://www.strava.com/api/v3/gear/")) {
            String [] tokens = url.split("/");
            String key = tokens[tokens.length - 1];
            return new ResponseEntity(stravaShoes.get(key), HttpStatus.OK);
        } else if (url.equals("https://www.strava.com/api/v3/athlete/activities?page=1&per_page=200")) {
            return new ResponseEntity(stravaActivities.toArray(new DataObjectSummaryActivity[0]), HttpStatus.OK);
        }  else if (url.equals("https://www.strava.com/api/v3/athlete/activities?page=2&per_page=200")) {
            return new ResponseEntity(new DataObjectSummaryActivity[0], HttpStatus.OK);
        } else if (url.equals("https://www.strava.com/oauth/token")) {
            DataObjectAccessToken token = new DataObjectAccessToken();
            token.setAthlete(stravaAthlete);
            token.setAccessToken("token");
            return new ResponseEntity(token, HttpStatus.OK);
        } else {
            throw new IllegalArgumentException(url);
        }
    }

    public void clear() {
        stravaAthlete = null;
        stravaShoes.clear();
        stravaActivities.clear();
    }

}
