package com.irunninglog.spring.dashboard;

import org.junit.Test;
import org.springframework.context.ApplicationContext;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DashboardGoalsServiceTest extends AbstractDashboardServicesTest {

    private DashboardGoalsService goalsService;

    @Override
    protected void afterAfterBefore(ApplicationContext context) {
        goalsService = context.getBean(DashboardGoalsService.class);
    }

    @Test
    public void noGoals() {
        assertEquals(0, goalsService.goals(profileEntity).size());
    }

    @Test
    public void oneGoal() {
        saveGoal("One", null, null, Boolean.FALSE, profileEntity);
        saveGoal("Two", null, null, Boolean.TRUE, profileEntity);

        assertEquals(1, goalsService.goals(profileEntity).size());
        assertTrue(goalsService.goals(profileEntity).iterator().next().getTextOne().contains("All dates"));
    }

    @Test
    public void startDate() {
        saveGoal(LocalDate.now(), null, Boolean.TRUE, profileEntity);

        assertTrue(goalsService.goals(profileEntity).iterator().next().getTextOne().contains("From"));
    }

    @Test
    public void endDate() {
        saveGoal(null, LocalDate.now(), Boolean.TRUE, profileEntity);

        assertTrue(goalsService.goals(profileEntity).iterator().next().getTextOne().contains("Through"));
    }

    @Test
    public void both() {
        saveGoal(LocalDate.now(), LocalDate.now(), Boolean.TRUE, profileEntity);

        assertTrue(goalsService.goals(profileEntity).iterator().next().getTextOne().contains("through"));
    }

}
