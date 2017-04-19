package com.irunninglog.main;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.irunninglog.api.Privacy;
import com.irunninglog.api.Unit;
import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.mapping.IMapper;
import com.irunninglog.api.security.IAuthenticationService;
import com.irunninglog.spring.context.ContextConfiguration;
import com.irunninglog.spring.data.*;
import com.irunninglog.spring.profile.IProfileEntityRepository;
import com.irunninglog.spring.profile.ProfileEntity;
import com.irunninglog.spring.workout.IWorkoutEntityRepository;
import com.irunninglog.spring.workout.WorkoutEntity;
import com.irunninglog.vertx.ServerVerticle;
import io.vertx.core.Verticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientRequest;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.UnsupportedEncodingException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Collection;

@RunWith(VertxUnitRunner.class)
public abstract class AbstractTest {

    private final Integer offset = -1 * ZonedDateTime.now().getOffset().getTotalSeconds() / 60;

    private IProfileEntityRepository profileEntityRepository;
    private IWorkoutEntityRepository workoutEntityRepository;
    private IRouteEntityRespository routeEntityRespository;
    private IRunEntityRepository runEntityRepository;
    private IShoeEntityRepository shoeEntityRepository;

    protected ApplicationContext applicationContext;

    private Vertx vertx;

    @Before
    public final void before(TestContext context) throws Exception {
        System.setProperty("dataSource", "org.h2.Driver|jdbc:h2:mem:test;DB_CLOSE_DELAY=-1|sa");
        System.setProperty("jpa", "update|org.hibernate.dialect.H2Dialect");
        System.setProperty("jwt", "issuer|secret");

        applicationContext = new AnnotationConfigApplicationContext(ContextConfiguration.class);

        profileEntityRepository = applicationContext.getBean(IProfileEntityRepository.class);

        workoutEntityRepository = applicationContext.getBean(IWorkoutEntityRepository.class);
        routeEntityRespository = applicationContext.getBean(IRouteEntityRespository.class);
        runEntityRepository = applicationContext.getBean(IRunEntityRepository.class);
        shoeEntityRepository = applicationContext.getBean(IShoeEntityRepository.class);

        vertx = Vertx.vertx();

        Async async = context.async();

        IFactory factory = applicationContext.getBean(IFactory.class);
        IMapper mapper = applicationContext.getBean(IMapper.class);

        ServerVerticle serverVerticle = new ServerVerticle(8889,
                context.asyncAssertSuccess(event -> async.complete()),
                factory,
                mapper,
                applicationContext.getBean(IAuthenticationService.class));

        vertx.deployVerticle(serverVerticle, context.asyncAssertSuccess());

        for (Verticle verticle : verticles(applicationContext)) {
            vertx.deployVerticle(verticle, context.asyncAssertSuccess());
        }

        async.awaitSuccess(5000);

        afterBefore(context);
    }

    protected abstract Collection<Verticle> verticles(ApplicationContext applicationContext);

    protected abstract void afterBefore(TestContext context) throws Exception;

    @After
    public void after(TestContext context) {
        workoutEntityRepository.deleteAll();

        routeEntityRespository.deleteAll();
        runEntityRepository.deleteAll();
        shoeEntityRepository.deleteAll();

        profileEntityRepository.deleteAll();

        vertx.close(context.asyncAssertSuccess());
    }

    protected ProfileEntity save(String email) {
        ProfileEntity entity = new ProfileEntity();
        entity.setId(-1);
        entity.setUsername(email);
        entity.setPreferredUnits(Unit.ENGLISH);
        entity.setWeekStart(DayOfWeek.MONDAY);
        entity.setWeeklyTarget(0);
        entity.setMonthlyTarget(0);
        entity.setYearlyTarget(0);
        entity.setDefaultRun(null);
        entity.setDefaultRoute(null);
        entity.setDefaultShoe(null);

        entity = profileEntityRepository.save(entity);

        return entity;
    }

    protected WorkoutEntity saveWorkout(ProfileEntity profile, LocalDate date, double distance, long duration, RouteEntity route, RunEntity run, ShoeEntity shoe) {
        WorkoutEntity entity = new WorkoutEntity();
        entity.setProfile(profile);
        entity.setDate(date);
        entity.setDistance(distance);
        entity.setDuration(duration);
        entity.setRoute(route);
        entity.setRun(run);
        entity.setShoe(shoe);
        entity.setPrivacy(Privacy.PRIVATE);

        return workoutEntityRepository.save(entity);
    }

    protected RouteEntity saveRoute(ProfileEntity profileEntity, String name, boolean dashboard) {
        RouteEntity entity = new RouteEntity();
        entity.setProfile(profileEntity);
        entity.setName(name);
        entity.setDashboard(dashboard);

        return routeEntityRespository.save(entity);
    }

    protected RunEntity saveRun(ProfileEntity profileEntity, String name, boolean dashboard) {
        RunEntity entity = new RunEntity();
        entity.setId(-1);
        entity.setProfile(profileEntity);
        entity.setName(name);
        entity.setDashboard(dashboard);

        return runEntityRepository.save(entity);
    }

    protected ShoeEntity saveShoe(ProfileEntity profileEntity, String name, boolean dashboard) {
        return saveShoe(profileEntity, name, dashboard, null);
    }

    protected ShoeEntity saveShoe(ProfileEntity profileEntity, String name, boolean dashboard, LocalDate start) {
        ShoeEntity entity = new ShoeEntity();
        entity.setProfile(profileEntity);
        entity.setName(name);
        entity.setDescription(null);
        entity.setMax(0);
        entity.setDashboard(dashboard);
        entity.setStartDate(start);

        return shoeEntityRepository.save(entity);
    }

    protected int get(TestContext context, String path, String token) {
        HttpClient client = vertx.createHttpClient();
        Async async = context.async();
        HttpClientRequest req = client.get(8889, "localhost", path);
        if (token != null && !token.trim().isEmpty()) {
            req.putHeader("Authorization", token);
            req.putHeader("iRunningLog-Utc-Offset", offset.toString());
        }

        final int[] responseCode = new int[1];
        req.handler(resp -> {
            responseCode[0] = resp.statusCode();
            async.complete();
        });
        req.end();

        async.awaitSuccess(5000);

        return responseCode[0];
    }

    protected int post(TestContext context, String path, String token) {
        HttpClient client = vertx.createHttpClient();
        Async async = context.async();
        HttpClientRequest req = client.post(8889, "localhost", path);
        if (token != null && !token.trim().isEmpty()) {
            req.putHeader("Authorization", token);
        }

        final int[] responseCode = new int[1];
        req.handler(resp -> {
            responseCode[0] = resp.statusCode();
            async.complete();
        });
        req.end();

        async.awaitSuccess(5000);

        return responseCode[0];
    }

    protected int put(TestContext context, String path, String token, String body) {
        HttpClient client = vertx.createHttpClient();
        Async async = context.async();
        HttpClientRequest req = client.put(8889, "localhost", path);
        if (token != null && !token.trim().isEmpty()) {
            req.putHeader("Authorization", token);
        }

        final int[] responseCode = new int[1];
        req.handler(resp -> {
            responseCode[0] = resp.statusCode();
            async.complete();
        });
        req.end(body);

        async.awaitSuccess(5000);

        return responseCode[0];
    }

    protected int delete(TestContext context, String path, String token) {
        HttpClient client = vertx.createHttpClient();
        Async async = context.async();
        HttpClientRequest req = client.delete(8889, "localhost", path);
        if (token != null && !token.trim().isEmpty()) {
            req.putHeader("Authorization", token);
            req.putHeader("iRunningLog-Utc-Offset", offset.toString());
        }

        final int[] responseCode = new int[1];
        req.handler(resp -> {
            responseCode[0] = resp.statusCode();
            async.complete();
        });
        req.end();

        async.awaitSuccess(5000);

        return responseCode[0];
    }

    protected String token(String username) throws UnsupportedEncodingException {
        Algorithm algorithm = Algorithm.HMAC256("secret");
        String token = JWT.create()
                .withIssuer("issuer")
                .withSubject(username)
                .sign(algorithm);

        return "Bearer " + token;
    }

}
