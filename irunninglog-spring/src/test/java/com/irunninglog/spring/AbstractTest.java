package com.irunninglog.spring;

import com.irunninglog.api.Gender;
import com.irunninglog.api.Privacy;
import com.irunninglog.api.Unit;
import com.irunninglog.spring.data.*;
import com.irunninglog.spring.profile.IProfileEntityRepository;
import com.irunninglog.spring.profile.ProfileEntity;
import com.irunninglog.spring.security.AuthorityEntity;
import com.irunninglog.spring.security.IAuthorityEntityRepository;
import com.irunninglog.spring.security.IUserEntityRepository;
import com.irunninglog.spring.security.UserEntity;
import com.irunninglog.spring.workout.IWorkoutEntityRepository;
import com.irunninglog.spring.workout.WorkoutEntity;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.DayOfWeek;
import java.time.LocalDate;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {com.irunninglog.spring.context.ContextConfiguration.class})
public abstract class AbstractTest implements ApplicationContextAware {

    private ApplicationContext applicationContext;
    private PasswordEncoder passwordEncoder;
    private IProfileEntityRepository profileEntityRepository;
    private IGoalEntityRepository goalEntityRepository;
    private IShoeEntityRepository shoeEntityRepository;
    private IRouteEntityRespository routeEntityRespository;
    private IRunEntityRepository runEntityRepository;
    private IWorkoutEntityRepository workoutEntityRepository;
    private IUserEntityRepository userEntityRepository;
    private IAuthorityEntityRepository authorityEntityRepository;

    static {
        System.setProperty("env", "application.properties");
    }

    @Before
    public final void before() {
        passwordEncoder = applicationContext.getBean(PasswordEncoder.class);
        profileEntityRepository = applicationContext.getBean(IProfileEntityRepository.class);
        goalEntityRepository = applicationContext.getBean(IGoalEntityRepository.class);
        shoeEntityRepository = applicationContext.getBean(IShoeEntityRepository.class);
        runEntityRepository = applicationContext.getBean(IRunEntityRepository.class);
        routeEntityRespository = applicationContext.getBean(IRouteEntityRespository.class);
        workoutEntityRepository = applicationContext.getBean(IWorkoutEntityRepository.class);
        userEntityRepository = applicationContext.getBean(IUserEntityRepository.class);
        authorityEntityRepository = applicationContext.getBean(IAuthorityEntityRepository.class);

        afterBefore(applicationContext);
    }

    protected void afterBefore(ApplicationContext applicationContext) {
        // Empty for subclasses
    }

    @After
    public final void after() {
        goalEntityRepository.deleteAll();
        workoutEntityRepository.deleteAll();
        shoeEntityRepository.deleteAll();
        runEntityRepository.deleteAll();
        routeEntityRespository.deleteAll();
        userEntityRepository.deleteAll();
        authorityEntityRepository.deleteAll();
    }

    @Override
    public final void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    protected ProfileEntity saveProfile(String email, String password, String ... authorities) {
        ProfileEntity profileEntity = new ProfileEntity();
        profileEntity.setEmail(email);
        profileEntity.setPassword(passwordEncoder.encode(password));
        profileEntity.setFirstName("Allan");
        profileEntity.setLastName("Lewis");
        profileEntity.setBirthday(LocalDate.now());
        profileEntity.setGender(Gender.Male);
        profileEntity.setWeekStart(DayOfWeek.MONDAY);
        profileEntity.setPreferredUnits(Unit.English);
        profileEntity = profileEntityRepository.save(profileEntity);

        UserEntity userEntity = userEntityRepository.findOne(profileEntity.getId());
        for (String authority : authorities) {
            AuthorityEntity authorityEntity = new AuthorityEntity();
            authorityEntity.setName(authority);
            authorityEntity = authorityEntityRepository.save(authorityEntity);
            userEntity.getAuthorities().add(authorityEntity);
        }

        userEntityRepository.save(userEntity);

        return profileEntity;
    }


    protected WorkoutEntity saveWorkout(LocalDate localDate, ProfileEntity entity) {
        return saveWorkout(localDate, 0, entity);
    }

    protected WorkoutEntity saveWorkout(LocalDate localDate, double distance, ProfileEntity profileEntity) {
        WorkoutEntity entity = new WorkoutEntity();
        entity.setPrivacy(Privacy.Private);
        entity.setDate(localDate);
        entity.setDistance(distance);
        entity.setProfile(profileEntity);

        return workoutEntityRepository.save(entity);
    }

    protected GoalEntity saveGoal(LocalDate start, LocalDate end, boolean dashboard, ProfileEntity entity) {
        return saveGoal("Goal", start, end, dashboard, entity);
    }

    protected GoalEntity saveGoal(String name, LocalDate start, LocalDate end, boolean dashboard, ProfileEntity entity) {
        GoalEntity goal = new GoalEntity();
        goal.setStartDate(start);
        goal.setEndDate(end);
        goal.setName(name);
        goal.setDescription(null);
        goal.setDashboard(dashboard);
        goal.setProfile(entity);

        return goalEntityRepository.save(goal);
    }

    protected ShoeEntity saveShoe(String name, boolean dashboard, ProfileEntity entity) {
        return saveShoe(name, null, dashboard, entity);
    }

    protected ShoeEntity saveShoe(String name, LocalDate date, boolean dashboard, ProfileEntity entity) {
        ShoeEntity shoeEntity = new ShoeEntity();
        shoeEntity.setName(name);
        shoeEntity.setDashboard(dashboard);
        shoeEntity.setProfile(entity);
        shoeEntity.setStartDate(date);

        return shoeEntityRepository.save(shoeEntity);
    }

    protected RunEntity saveRun(String name, boolean dashboard, ProfileEntity profileEntity) {
        RunEntity entity = new RunEntity();
        entity.setName(name);
        entity.setDashboard(dashboard);
        entity.setProfile(profileEntity);

        return runEntityRepository.save(entity);
    }

    protected RouteEntity saveRoute(String name, boolean dashboard, ProfileEntity profileEntity) {
        RouteEntity entity = new RouteEntity();
        entity.setName(name);
        entity.setDashboard(dashboard);
        entity.setProfile(profileEntity);

        return routeEntityRespository.save(entity);
    }

}
