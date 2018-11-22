package com.irunninglog.strava.impl;

import com.irunninglog.strava.IStravaRemoteApi;
import com.irunninglog.strava.IStravaSession;
import javastrava.api.v3.model.*;
import javastrava.api.v3.model.reference.StravaActivityType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

@Component
@Scope("prototype")
final class StravaSessionImpl implements IStravaSession {

    private static final Logger LOG = LoggerFactory.getLogger(StravaSessionImpl.class);
    private static final long DELAY_FULL = 30;
    private static final long DELAY_POLL = 5;

    private final IStravaRemoteApi api;

    private final AtomicReference<StravaAthlete> athlete = new AtomicReference<>();
    private final AtomicReference<StravaStatistics> statistics = new AtomicReference<>();
    private final AtomicReference<List<StravaActivity>> activities = new AtomicReference<>();
    private final AtomicReference<Map<String, StravaGear>> shoes = new AtomicReference<>();

    @Autowired
    StravaSessionImpl(IStravaRemoteApi api) {
        this.api = api;
    }

    @Override
    public StravaAthlete athlete() {
        return athlete.get();
    }

    @Override
    public List<StravaActivity> activities() {
        return new ArrayList<>(activities.get());
    }

    @Override
    public StravaGear gear(String id) {
        return shoes.get().get(id);
    }

    @Override
    public void load(String token) {
        long start = System.currentTimeMillis();

        LOG.info("load");

        api.setToken(token);

        loadAthlete();
        activities.set(new ArrayList<>());
        shoes.set(new HashMap<>());

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(3);

        // Load initial state "immediately"
        executor.schedule(this::loadAllButAthlete, DELAY_POLL, TimeUnit.MILLISECONDS);

        // Poll for modifications every 5 minutes
        executor.scheduleWithFixedDelay(this::poll, DELAY_POLL, DELAY_POLL, TimeUnit.MINUTES);

        // Refresh everything each half-hour
        executor.scheduleWithFixedDelay(this::loadAll, DELAY_FULL, DELAY_FULL, TimeUnit.MINUTES);

        LOG.info("load:{}", System.currentTimeMillis() - start);
    }

    private void poll() {
        long start = System.currentTimeMillis();

        try {
            boolean update = Boolean.FALSE;

            StravaStatistics old = statistics.get();

            statistics.set(api.statistics(athlete.get().getId()));

            if (!old.equals(statistics.get())) {
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
            LOG.info("reloadAll");

            if (loadAthlete) {
                loadAthlete();
            }

            loadShoes();

            loadActivities();
        } catch (Exception ex) {
            LOG.error("Unable to reload all", ex);
        } finally {
            LOG.info("reloadAll:{}", System.currentTimeMillis() - start);
        }
    }

    private void loadActivities() {
        long start = System.currentTimeMillis();

        LOG.info("loadActivities");

        int page = 1;
        int count = -1;

        List<StravaActivity> activityList = new ArrayList<>();
        while (count != 0) {
            StravaActivity[] array = api.listAuthenticatedAthleteActivities(page, 200);

            count = array.length;
            for (StravaActivity activity : array) {
                if (activity.getType() == StravaActivityType.RUN) {
                    activityList.add(activity);
                }
            }

            page++;
        }

        activities.set(activityList);

        LOG.info("loadActivities:{}", activities.get().size());

        LOG.info("loadActivities:{}", System.currentTimeMillis() - start);
    }

    private void loadAthlete() {
        long start = System.currentTimeMillis();

        LOG.info("loadAthlete");

        athlete.set(api.getAuthenticatedAthlete());
        statistics.set(api.statistics(athlete.get().getId()));

        LOG.info("loadAthlete:{}:{}", athlete.get(), statistics.get());

        LOG.info("loadAthlete:{}", System.currentTimeMillis() - start);
    }

    private void loadShoes() {
        long start = System.currentTimeMillis();

        LOG.info("loadShoes");

        Map<String, StravaGear> map = new HashMap<>();

        for (StravaGear gear : athlete.get().getShoes()) {
            map.put(gear.getId(), api.getGear(gear.getId()));
        }

        shoes.set(map);

        LOG.info("loadShoes:{}", shoes.get().size());

        LOG.info("loadShoes:{}", System.currentTimeMillis() - start);
    }

    @Override
    public StravaActivity create(StravaActivity activity) {
        return api.create(activity);
    }

    @Override
    public StravaActivity update(int id, StravaActivityUpdate update) {
        return api.update(id, update);
    }

}
