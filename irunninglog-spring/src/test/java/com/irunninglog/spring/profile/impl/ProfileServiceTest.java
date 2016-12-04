package com.irunninglog.spring.profile.impl;

import com.irunninglog.Gender;
import com.irunninglog.Unit;
import com.irunninglog.profile.IProfileService;
import com.irunninglog.profile.ProfileRequest;
import com.irunninglog.service.ResponseStatusException;
import com.irunninglog.spring.AbstractTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.DayOfWeek;
import java.time.LocalDate;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

@SuppressWarnings("SpringJavaAutowiredMembersInspection")
public class ProfileServiceTest extends AbstractTest {

    @Autowired
    private IProfileService profileService;
    @Autowired
    private IProfileEntityRepository profileEntityRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private long goodId;
    private long badId;

    @Before
    public void before() {
        ProfileEntity profileEntity = new ProfileEntity();
        profileEntity.setEmail("allan@irunninglog.com");
        profileEntity.setPassword(passwordEncoder.encode("psssword"));
        profileEntity.setFirstName("Allan");
        profileEntity.setLastName("Lewis");
        profileEntity.setBirthday(LocalDate.now());
        profileEntity.setGender(Gender.Male);
        profileEntity.setWeekStart(DayOfWeek.MONDAY);
        profileEntity.setPreferredUnits(Unit.English);

        profileEntity = profileEntityRepository.save(profileEntity);
        goodId = profileEntity.getId();
        badId = profileEntity.getId() + 1;
    }

    @After
    public void after() {
        profileEntityRepository.deleteAll();
    }

    @Test
    public void good() {
        ProfileRequest request = new ProfileRequest().setId(goodId);
        assertNotNull(profileService.get(request));
    }

    @Test
    public void bad() {
        try {
            profileService.get(new ProfileRequest().setId(badId));
            fail("Should have thrown");
        } catch (ResponseStatusException ex) {
            assertTrue(Boolean.TRUE);
        }
    }

}
