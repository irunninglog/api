package com.irunninglog.spring.strava;

import com.irunninglog.api.athletes.IAthlete;
import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.runs.IRun;
import com.irunninglog.api.security.AuthnException;
import com.irunninglog.api.security.IUser;
import com.irunninglog.api.shoes.IShoe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public final class StravaService {

    private static final Logger LOG = LoggerFactory.getLogger(StravaService.class);

    private final IFactory factory;
    private final StravaSessionCache cache;
    private final RestTemplate restTemplate;
    private final String id;
    private final String secret;

    @Autowired
    public StravaService(Environment environment, IFactory factory, StravaSessionCache cache, RestTemplate restTemplate) {
        this.factory = factory;
        this.cache = cache;
        this.restTemplate = restTemplate;

        String config = environment.getProperty("strava");
        if (config != null && config.contains("|")) {
            LOG.info("Successfully loaded Strava configs");
            String [] tokens = config.split("\\|");
            id = tokens[0];
            secret = tokens[1];
        } else {
            LOG.warn("Unable to find Strava configs; using defaults");
            id = "id";
            secret = "secret";
        }
    }

    public IUser userFromCode(String code) throws AuthnException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
        map.add("client_id", id);
        map.add("client_secret", secret);
        map.add("code", code);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(map, headers);

        ResponseEntity<DataObjectAccessToken> responseEntity = restTemplate.exchange("https://www.strava.com/oauth/token", HttpMethod.POST, requestEntity, DataObjectAccessToken.class);
        DataObjectAccessToken token = responseEntity.getBody();

        if (token == null) {
            throw new AuthnException("Unable to read token");
        }

        return factory.get(IUser.class)
                .setId(token.getAthlete().getId())
                .setUsername(token.getAthlete().getEmail())
                .setToken(token.getAccessToken());
    }

    public IUser userFromToken(String token) {
        IAthlete athlete = cache.create(token).athlete();

        return factory.get(IUser.class)
                .setId(athlete.getId())
                .setUsername(athlete.getEmail())
                .setToken(token);
    }

    public IAthlete athlete(IUser user) {
        return cache.get(user.getToken()).athlete();
    }

    public List<IRun> runs(IUser user) {
        return cache.get(user.getToken()).runs();
    }

    public List<IShoe> shoes(IUser user) {
        return cache.get(user.getToken()).shoes();
    }

}
