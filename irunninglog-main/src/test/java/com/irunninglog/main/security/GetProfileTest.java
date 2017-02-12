package com.irunninglog.main.security;

import com.irunninglog.api.Gender;
import com.irunninglog.api.Unit;
import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.mapping.IMapper;
import com.irunninglog.api.profile.IProfileService;
import com.irunninglog.api.security.IAuthenticationService;
import com.irunninglog.spring.context.ContextConfiguration;
import com.irunninglog.spring.profile.IProfileEntityRepository;
import com.irunninglog.spring.profile.ProfileEntity;
import com.irunninglog.spring.security.AuthorityEntity;
import com.irunninglog.spring.security.IAuthorityEntityRepository;
import com.irunninglog.spring.security.IUserEntityRepository;
import com.irunninglog.spring.security.UserEntity;
import com.irunninglog.vertx.endpoint.profile.GetProfileVerticle;
import com.irunninglog.vertx.http.ServerVerticle;
import com.irunninglog.vertx.security.AuthnVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientRequest;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;

@RunWith(VertxUnitRunner.class)
public class GetProfileTest {

    private final static String TOKEN = "Basic YWxsYW5AaXJ1bm5pbmdsb2cuY29tOnBhc3N3b3Jk";

    private Vertx vertx;

    private IProfileEntityRepository profileEntityRepository;
    private IUserEntityRepository userEntityRepository;
    private IAuthorityEntityRepository authorityEntityRepository;
    private PasswordEncoder passwordEncoder;

    private ProfileEntity myprofile;

    @Before
    public final void before(TestContext context) throws IOException {
        System.setProperty("env", "file:///" + new ClassPathResource("application.properties").getFile().getAbsolutePath());

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(ContextConfiguration.class);

        profileEntityRepository = applicationContext.getBean(IProfileEntityRepository.class);
        userEntityRepository = applicationContext.getBean(IUserEntityRepository.class);
        authorityEntityRepository = applicationContext.getBean(IAuthorityEntityRepository.class);
        passwordEncoder = applicationContext.getBean(PasswordEncoder.class);

        myprofile = save("allan@irunninglog.com", "password", "MYPROFILE");

        vertx = Vertx.vertx();

        Async async = context.async();

        IFactory factory = applicationContext.getBean(IFactory.class);
        IMapper mapper = applicationContext.getBean(IMapper.class);

        ServerVerticle verticle = new ServerVerticle(8889,
                context.asyncAssertSuccess(event -> async.complete()),
                factory,
                mapper);

        vertx.deployVerticle(verticle, context.asyncAssertSuccess());

        AuthnVerticle authnVerticle = new AuthnVerticle(applicationContext.getBean(IAuthenticationService.class), factory, mapper);
        vertx.deployVerticle(authnVerticle, context.asyncAssertSuccess());

        GetProfileVerticle profileVerticle = new GetProfileVerticle(factory, mapper, applicationContext.getBean(IProfileService.class));
        vertx.deployVerticle(profileVerticle, context.asyncAssertSuccess());

        async.awaitSuccess(5000);
    }

    @After
    public void after(TestContext context) {
        userEntityRepository.deleteAll();
        authorityEntityRepository.deleteAll();

        vertx.close(context.asyncAssertSuccess());
    }

    @Test
    public void ok(TestContext context) {
        HttpClient client = vertx.createHttpClient();
        Async async = context.async();
        HttpClientRequest req = client.get(8889, "localhost", "/profiles/" + myprofile.getId());
        req.putHeader("Authorization", TOKEN);

        final int[] responseCode = new int[1];
        req.handler(resp -> {
            responseCode[0] = resp.statusCode();
            async.complete();
        });
        req.end();

        async.awaitSuccess(5000);

        context.assertEquals(200, responseCode[0]);
    }

    private ProfileEntity save(String email, String password, String ... authorities) {
        ProfileEntity entity = new ProfileEntity();
        entity.setEmail(email);
        entity.setPassword(passwordEncoder.encode(password));
        entity.setFirstName("First");
        entity.setLastName("Last");
        entity.setBirthday(LocalDate.now());
        entity.setGender(Gender.Male);
        entity.setPreferredUnits(Unit.English);
        entity.setWeekStart(DayOfWeek.MONDAY);

        entity = profileEntityRepository.save(entity);

        UserEntity userEntity = userEntityRepository.findOne(entity.getId());
        for (String authority : authorities) {
            AuthorityEntity authorityEntity = new AuthorityEntity();
            authorityEntity.setName(authority);

            userEntity.getAuthorities().add(authorityEntityRepository.save(authorityEntity));
        }
        userEntityRepository.save(userEntity);

        return entity;
    }

}
