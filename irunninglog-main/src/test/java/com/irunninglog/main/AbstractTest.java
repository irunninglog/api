package com.irunninglog.main;

import com.irunninglog.api.Gender;
import com.irunninglog.api.Privacy;
import com.irunninglog.api.Unit;
import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.mapping.IMapper;
import com.irunninglog.api.security.IAuthenticationService;
import com.irunninglog.spring.context.ContextConfiguration;
import com.irunninglog.spring.data.*;
import com.irunninglog.spring.profile.IProfileEntityRepository;
import com.irunninglog.spring.profile.ProfileEntity;
import com.irunninglog.spring.security.AuthorityEntity;
import com.irunninglog.spring.security.IAuthorityEntityRepository;
import com.irunninglog.spring.security.IUserEntityRepository;
import com.irunninglog.spring.security.UserEntity;
import com.irunninglog.spring.workout.IWorkoutEntityRepository;
import com.irunninglog.spring.workout.WorkoutEntity;
import com.irunninglog.vertx.http.ServerVerticle;
import com.irunninglog.vertx.security.AuthnVerticle;
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
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Base64;
import java.util.Collection;

@RunWith(VertxUnitRunner.class)
public abstract class AbstractTest {

    private final Integer offset = -1 * ZonedDateTime.now().getOffset().getTotalSeconds() / 60;

    private IProfileEntityRepository profileEntityRepository;
    private IUserEntityRepository userEntityRepository;
    private IAuthorityEntityRepository authorityEntityRepository;
    private IWorkoutEntityRepository workoutEntityRepository;
    private IRouteEntityRespository routeEntityRespository;
    private IRunEntityRepository runEntityRepository;
    private IShoeEntityRepository shoeEntityRepository;
    private PasswordEncoder passwordEncoder;

    protected ApplicationContext applicationContext;

    private Vertx vertx;

    @Before
    public final void before(TestContext context) throws Exception {
        System.setProperty("env", "file:///" + new ClassPathResource("application.properties").getFile().getAbsolutePath());

        applicationContext = new AnnotationConfigApplicationContext(ContextConfiguration.class);

        profileEntityRepository = applicationContext.getBean(IProfileEntityRepository.class);
        userEntityRepository = applicationContext.getBean(IUserEntityRepository.class);
        authorityEntityRepository = applicationContext.getBean(IAuthorityEntityRepository.class);
        passwordEncoder = applicationContext.getBean(PasswordEncoder.class);

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
                mapper);

        vertx.deployVerticle(serverVerticle, context.asyncAssertSuccess());

        AuthnVerticle authnVerticle = new AuthnVerticle(applicationContext.getBean(IAuthenticationService.class), factory, mapper);
        vertx.deployVerticle(authnVerticle, context.asyncAssertSuccess());

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

        userEntityRepository.deleteAll();
        authorityEntityRepository.deleteAll();

        vertx.close(context.asyncAssertSuccess());
    }

    protected ProfileEntity save(String email, String password, String ... authorities) {
        ProfileEntity entity = new ProfileEntity();
        entity.setId(-1);
        entity.setEmail(email);
        entity.setPassword(passwordEncoder.encode(password));
        entity.setFirstName("First");
        entity.setLastName("Last");
        entity.setBirthday(LocalDate.now());
        entity.setGender(Gender.MALE);
        entity.setPreferredUnits(Unit.ENGLISH);
        entity.setWeekStart(DayOfWeek.MONDAY);
        entity.setWeeklyTarget(0);
        entity.setMonthlyTarget(0);
        entity.setYearlyTarget(0);
        entity.setDefaultRun(null);
        entity.setDefaultRoute(null);
        entity.setDefaultShoe(null);

        entity = profileEntityRepository.save(entity);

        UserEntity userEntity = userEntityRepository.findOne(entity.getId());
        userEntity.setId(entity.getId());
        userEntity.setPassword(entity.getPassword());
        userEntity.setUsername(entity.getEmail());
        userEntity.setAuthorities(userEntity.getAuthorities());

        for (String authority : authorities) {
            AuthorityEntity authorityEntity = new AuthorityEntity();
            authorityEntity.setId(-1);
            authorityEntity.setName(authority);

            userEntity.getAuthorities().add(authorityEntityRepository.save(authorityEntity));
        }
        userEntityRepository.save(userEntity);

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
        entity.setPrivacy(Privacy.Private);

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

    protected String token(String username, String password) {
        return "Basic " + Base64.getEncoder().encodeToString((username + ":" + password).getBytes());
    }

}
