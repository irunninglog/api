package com.irunninglog.strava.impl;

import com.irunninglog.api.factory.IFactory;
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
    private static final long DELAY_FULL = 30; // MINUTES
    private static final long DELAY_POLL = 5;

    private final IFactory factory;

    private String token;
    private final AtomicReference<StravaAthlete> athlete = new AtomicReference<>();
    private final AtomicReference<StravaStatistics> statistics = new AtomicReference<>();
    private final AtomicReference<List<StravaActivity>> activities = new AtomicReference<>();
    private final AtomicReference<Map<String, StravaGear>> shoes = new AtomicReference<>();

    @Autowired
    StravaSessionImpl(IFactory factory) {
        this.factory = factory;
    }

    @Override
    public StravaAthlete athlete() {
        synchronized (athlete) {
            return athlete.get();
        }
    }

    @Override
    public List<StravaActivity> activities() {
        synchronized (athlete) {
            if (activities.get() == null) {
                loadActivities(api(token));
            }

            return activities.get();
        }
    }

    @Override
    public StravaGear gear(String id) {
        synchronized (athlete) {
            if (shoes.get() == null) {
                loadShoes(api(token));
            }

            return shoes.get().get(id);
        }
    }

    @Override
    public void load(String token) {
        long start = System.currentTimeMillis();

        LOG.info("load");

        this.token = token;

        loadAthlete(api(token));

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);

        // Poll for modifications every 5 minutes
        executor.scheduleWithFixedDelay(this::poll, DELAY_POLL, DELAY_POLL, TimeUnit.MINUTES);

        // Refresh everything each half-hour
        executor.scheduleWithFixedDelay(this::reloadAll, DELAY_FULL, DELAY_FULL, TimeUnit.MINUTES);

        LOG.info("load:{}", System.currentTimeMillis() - start);
    }

    private void poll() {
        long start = System.currentTimeMillis();

        try {
            boolean update = Boolean.FALSE;

            synchronized (athlete) {
                StravaStatistics old = statistics.get();

                statistics.set(api(token).statistics(athlete.get().getId()));

                if (!old.equals(statistics.get())) {
                    LOG.info("Detected changes; reloading everything");

                    update = Boolean.TRUE;
                } else {
                    LOG.info("No changes detected");
                }
            }

            if (update) {
                reloadAll();
            }
        } catch (Exception ex) {
            LOG.error("Unable to poll for updates", ex);
        } finally {
            LOG.info("poll:{}", System.currentTimeMillis() - start);
        }
    }

    private void reloadAll() {
        long start = System.currentTimeMillis();

        try {
            LOG.info("reloadAll");

            IStravaRemoteApi api = api(token);

            loadAthlete(api);

            loadShoes(api);

            loadActivities(api);
        } catch (Exception ex) {
            LOG.error("Unable to reload all", ex);
        } finally {
            LOG.info("reloadAll:{}", System.currentTimeMillis() - start);
        }
    }

    private void loadActivities(IStravaRemoteApi api) {
        long start = System.currentTimeMillis();

        LOG.info("loadActivities");

        synchronized (athlete) {
            if (activities.get() == null) {
                activities.set(new ArrayList<>());
            }

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

                activities.get().clear();
                activities.get().addAll(activityList);

                page++;
            }

            LOG.info("loadActivities:{}", activities.get().size());
        }

        LOG.info("loadActivities:{}", System.currentTimeMillis() - start);
    }

    private void loadAthlete(IStravaRemoteApi api) {
        long start = System.currentTimeMillis();

        LOG.info("loadAthlete");

        synchronized (athlete) {
            athlete.set(api.getAuthenticatedAthlete());
            statistics.set(api.statistics(athlete.get().getId()));

            LOG.info("loadAthlete:{}:{}", athlete.get(), statistics.get());
        }

        LOG.info("loadAthlete:{}", System.currentTimeMillis() - start);
    }

    private void loadShoes(IStravaRemoteApi api) {
        long start = System.currentTimeMillis();

        LOG.info("loadShoes");

        synchronized (athlete) {
            if (shoes.get() == null) {
                shoes.set(new HashMap<>());
            }

            for (StravaGear gear : athlete.get().getShoes()) {
                shoes.get().put(gear.getId(), api.getGear(gear.getId()));
            }

            LOG.info("loadShoes:{}", shoes.get().size());
        }

        LOG.info("loadShoes:{}", System.currentTimeMillis() - start);
    }

    private IStravaRemoteApi api(String token) {
        IStravaRemoteApi api = factory.get(IStravaRemoteApi.class);
        api.setToken(token);
        return api;
    }

    @Override
    public StravaActivity create(StravaActivity activity) {
        return api(token).create(activity);
    }

    @Override
    public StravaActivity update(int id, StravaActivityUpdate update) {
        return api(token).update(id, update);
    }

}
