package com.irunninglog.spring.dashboard;

import com.irunninglog.api.Gender;
import com.irunninglog.api.Privacy;
import com.irunninglog.api.Unit;
import com.irunninglog.spring.AbstractTest;
import com.irunninglog.spring.data.IGoalEntityRepository;
import com.irunninglog.spring.data.IShoeEntityRepository;
import com.irunninglog.spring.date.DateService;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.DayOfWeek;
import java.time.LocalDate;

@SuppressWarnings("SpringJavaAutowiredMembersInspection")
abstract class AbstractDashboardServicesTest extends AbstractTest {

    @Autowired
    protected IWorkoutEntityRepository workoutEntityRepository;
    @Autowired
    protected IAuthorityEntityRepository authorityEntityRepository;
    @Autowired
    protected IUserEntityRepository userEntityRepository;
    @Autowired
    protected IShoeEntityRepository shoeEntityRepository;
    @Autowired
    protected IGoalEntityRepository goalEntityRepository;
    @Autowired
    protected IProfileEntityRepository profileEntityRepository;
    @Autowired
    protected DateService dateService;
    @Autowired
    protected PasswordEncoder passwordEncoder;

    ProfileEntity profileEntity;

    @Before
    public void before() {
        profileEntity = new ProfileEntity();
        profileEntity.setEmail("allan@irunninglog.com");
        profileEntity.setFirstName("First");
        profileEntity.setLastName("Last");
        profileEntity.setPassword(passwordEncoder.encode("password"));
        profileEntity.setWeekStart(DayOfWeek.MONDAY);
        profileEntity.setPreferredUnits(Unit.English);
        profileEntity.setBirthday(LocalDate.now());
        profileEntity.setGender(Gender.Male);

        profileEntity = profileEntityRepository.save(profileEntity);

        AuthorityEntity authorityEntity = new AuthorityEntity();
        authorityEntity.setName("MYPROFILE");
        authorityEntity = authorityEntityRepository.save(authorityEntity);

        UserEntity userEntity = userEntityRepository.findOne(profileEntity.getId());
        userEntity.getAuthorities().add(authorityEntity);
        userEntityRepository.save(userEntity);
    }

    @After
    public void after() {
        goalEntityRepository.deleteAll();
        workoutEntityRepository.deleteAll();
        shoeEntityRepository.deleteAll();
        userEntityRepository.deleteAll();
        authorityEntityRepository.deleteAll();
    }

    void saveWorkout(LocalDate localDate) {
        saveWorkout(localDate, 0);
    }

    void saveWorkout(LocalDate localDate, double distance) {
        WorkoutEntity entity = new WorkoutEntity();
        entity.setPrivacy(Privacy.Private);
        entity.setDate(localDate);
        entity.setDistance(distance);
        entity.setProfile(profileEntity);

        workoutEntityRepository.save(entity);
    }

}
