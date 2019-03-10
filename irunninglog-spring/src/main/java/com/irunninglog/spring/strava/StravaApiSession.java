package com.irunninglog.spring.strava;

import com.irunninglog.api.athletes.IAthlete;
import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.progress.ProgressThresholds;
import com.irunninglog.api.runs.IRun;
import com.irunninglog.api.shoes.IShoe;
import com.irunninglog.date.ApiDate;
import com.irunninglog.math.ApiMath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

@Component
@Scope("prototype")
class StravaApiSession {

    private static final Logger LOG = LoggerFactory.getLogger(StravaApiSession.class);
    private static final long DELAY_FULL = 30;
    private static final long DELAY_POLL = 5;

    private final IFactory factory;
    private final RestTemplate restTemplate;
    private final ApiMath apiMath;
    private final ApiDate apiDate;
    private final AtomicReference<IAthlete> athleteAtomicReference = new AtomicReference<>();
    private final AtomicReference<Set<String>> shoeIdsAtomicReference = new AtomicReference<>();
    private final AtomicReference<List<IShoe>> shoesAtomicReference = new AtomicReference<>();
    private final AtomicReference<List<IRun>> runsAtomicReference = new AtomicReference<>();
    private final AtomicReference<String> statsAtomicReference = new AtomicReference<>();
    private final HttpHeaders httpHeaders = new HttpHeaders();

    @Autowired
    StravaApiSession(IFactory factory, RestTemplate restTemplate, ApiMath apiMath, ApiDate apiDate) {
        this.factory = factory;
        this.restTemplate = restTemplate;
        this.apiMath = apiMath;
        this.apiDate = apiDate;
    }

    IAthlete athlete() {
        return athleteAtomicReference.get();
    }

    List<IShoe> shoes() {
        return shoesAtomicReference.get();
    }

    List<IRun> runs() {
        return runsAtomicReference.get();
    }

    void load(String token) {
        long start = System.currentTimeMillis();

        LOG.info("load");

        httpHeaders.add("Authorization", token);

        shoeIdsAtomicReference.set(Collections.emptySet());
        shoesAtomicReference.set(Collections.emptyList());
        runsAtomicReference.set(Collections.emptyList());
        loadAthlete();

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(3);

        // Load initial state "immediately"
        executor.schedule(this::loadAllButAthlete, DELAY_POLL, TimeUnit.MILLISECONDS);

        // Poll for modifications every 5 minutes
        executor.scheduleWithFixedDelay(this::poll, 30, 30, TimeUnit.SECONDS);

        // Refresh everything each half-hour
        executor.scheduleWithFixedDelay(this::loadAll, DELAY_FULL, DELAY_FULL, TimeUnit.MINUTES);

        LOG.info("load:{}", System.currentTimeMillis() - start);
    }

    private void poll() {
        long start = System.currentTimeMillis();

        try {
            boolean update = Boolean.FALSE;

            HttpEntity<String> httpEntity = new HttpEntity<>(null, httpHeaders);

            ResponseEntity<String> stats = restTemplate.exchange("https://www.strava.com/api/v3/athletes/" + athleteAtomicReference.get().getId() + "/stats",
                    HttpMethod.GET, httpEntity, String.class);

            String oldValue = statsAtomicReference.get();
            String newValue = stats.getBody();

            statsAtomicReference.set(newValue);

            if (oldValue == null && newValue == null) {
                LOG.error("Didnt' get a stats value; something is wrong");
            } if (oldValue == null && newValue != null) {
                LOG.info("No old value; assuming this is the first update");
            } else if (oldValue != null && !oldValue.equals(newValue)) {
                LOG.info("Detected changes; reloading everything");

                update = Boolean.TRUE;
            } else {
                LOG.info("No changes detected");
            }

            if (update) {
                loadAll();
            }
        } catch (Exception ex) {
            LOG.error("Unable to poll for updates", ex);
        } finally {
            LOG.info("poll:{}", System.currentTimeMillis() - start);
        }
    }

    private void loadAll() {
        load(Boolean.TRUE);
    }

    private void loadAllButAthlete() {
        load(Boolean.FALSE);
    }

    private void load(boolean loadAthlete) {
        long start = System.currentTimeMillis();

        try {
            LOG.info("load:{}", loadAthlete);

            if (loadAthlete) {
                loadAthlete();
            }

            loadShoes();

            loadRuns();
        } catch (Exception ex) {
            LOG.error("Unable to load", ex);
        } finally {
            LOG.info("load:{}", System.currentTimeMillis() - start);
        }
    }

    private void loadAthlete() {
        HttpEntity<String> httpEntity = new HttpEntity<>(null, httpHeaders);

        ResponseEntity<StravaApiDataAthlete> responseEntity = restTemplate.exchange("https://www.strava.com/api/v3/athlete", HttpMethod.GET, httpEntity, StravaApiDataAthlete.class);

        StravaApiDataAthlete stravaAthlete = responseEntity.getBody();
        if (stravaAthlete != null) {
            athleteAtomicReference.set(factory.get(IAthlete.class)
                    .setId(stravaAthlete.getId())
                    .setEmail(stravaAthlete.getEmail())
                    .setFirstname(stravaAthlete.getFirstName())
                    .setLastname(stravaAthlete.getLastName())
                    .setAvatar(stravaAthlete.getProfileMedium()));

            Set<String> shoeIds = new HashSet<>();
            for (StravaApiDataShoe shoe : stravaAthlete.getShoes()) {
                shoeIds.add(shoe.getId());
            }

            shoeIdsAtomicReference.set(shoeIds);
        }
    }

    private void loadShoes() {
        long start = System.currentTimeMillis();

        LOG.info("loadShoes");

        List<StravaApiDataShoe> stravaShoes = new ArrayList<>();
        for (String id : shoeIdsAtomicReference.get()) {
            HttpEntity<String> httpEntity = new HttpEntity<>(null, httpHeaders);

            stravaShoes.add(restTemplate.exchange("https://www.strava.com/api/v3/gear/" + id, HttpMethod.GET, httpEntity, StravaApiDataShoe.class).getBody());
        }

        List<IShoe> shoes = new ArrayList<>();
        for (StravaApiDataShoe apiDataShoe : stravaShoes) {
            shoes.add(factory.get(IShoe.class)
                    .setId(apiDataShoe.getId())
                    .setName(apiDataShoe.getName())
                    .setBrand(apiDataShoe.getBrandName())
                    .setModel(apiDataShoe.getModelName())
                    .setDescription(apiDataShoe.getDescription())
                    .setPrimary(apiDataShoe.isPrimary())
                    .setPercentage(apiMath.percentage(BigDecimal.valueOf(804672.0F), BigDecimal.valueOf(apiDataShoe.getDistance())).intValue())
                    .setProgress(apiMath.progress(apiMath.percentage(BigDecimal.valueOf(804672.0F), BigDecimal.valueOf(apiDataShoe.getDistance())), new ProgressThresholds(40, 80, ProgressThresholds.ProgressMode.LOW_GOOD)))
                    .setDistance(apiMath.format(apiMath.round(apiMath.miles(BigDecimal.valueOf(apiDataShoe.getDistance()))), ApiMath.FORMAT_FORMATTED_MILEAGE)));
        }

        shoes.sort((o1, o2) ->  Integer.compare(o2.getPercentage(), o1.getPercentage()));

        shoesAtomicReference.set(Collections.unmodifiableList(shoes));

        LOG.info("loadShoes:{}", shoes.size());

        LOG.info("loadShoes:{}", System.currentTimeMillis() - start);
    }

    private void loadRuns() {
        long start = System.currentTimeMillis();

        LOG.info("loadRuns");

        int page = 1;
        int count = -1;

        List<IRun> runs = new ArrayList<>();
        while (count != 0) {
            StravaApiDataActivity[] activities = loadActivities(page);
            for (StravaApiDataActivity activity : activities) {
                if ("run".equalsIgnoreCase(activity.getType())) {
                    runs.add(factory.get(IRun.class)
                            .setId(activity.getId())
                            .setName(activity.getName())
                            .setShoes(activity.getGearId())
                            .setDistance(apiMath.format(BigDecimal.valueOf(activity.getDistance()), ApiMath.FORMAT_PLAIN))
                            .setDuration(activity.getMovingTime())
                            .setStartTime(activity.getStartDate()));
                }
            }

            count = activities.length;

            page++;
        }

        runs.sort((o1, o2) -> apiDate.parseZonedDate(o2.getStartTime()).compareTo(apiDate.parseZonedDate(o1.getStartTime())));

        LOG.info("loadRuns:{}", runs.size());

        runsAtomicReference.set(Collections.unmodifiableList(runs));

        LOG.info("loadRuns:{}", System.currentTimeMillis() - start);
    }

    private StravaApiDataActivity[] loadActivities(int page) {
        HttpEntity<String> httpEntity = new HttpEntity<>(null, httpHeaders);

        ResponseEntity<StravaApiDataActivity[]> responseEntity = restTemplate.exchange("https://www.strava.com/api/v3/athlete/activities?page=" + page + "&per_page=" + 200, HttpMethod.GET, httpEntity, StravaApiDataActivity[].class);

        return responseEntity.getBody();
    }

}
