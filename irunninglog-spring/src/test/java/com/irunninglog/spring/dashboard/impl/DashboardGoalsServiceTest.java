package com.irunninglog.spring.dashboard.impl;

import com.irunninglog.spring.data.impl.GoalEntity;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@SuppressWarnings("SpringJavaAutowiredMembersInspection")
public class DashboardGoalsServiceTest extends AbstractDashboardServicesTest {

    @Autowired
    private DashboardGoalsService goalsService;

    @Test
    public void noGoals() {
        assertEquals(0, goalsService.goals(profileEntity).size());
    }

    @Test
    public void oneGoal() {
        GoalEntity one = new GoalEntity();
        one.setName("One");
        one.setProfile(profileEntity);

        GoalEntity two = new GoalEntity();
        two.setName("Two");
        two.setProfile(profileEntity);
        two.setDashboard(Boolean.TRUE);

        goalEntityRepository.save(one);
        goalEntityRepository.save(two);

        assertEquals(1, goalsService.goals(profileEntity).size());
        assertTrue(goalsService.goals(profileEntity).iterator().next().getTextOne().contains("All dates"));
    }

    @Test
    public void startDate() {
        save(LocalDate.now(), null);

        assertTrue(goalsService.goals(profileEntity).iterator().next().getTextOne().contains("From"));
    }

    @Test
    public void endDate() {
        save(null, LocalDate.now());

        assertTrue(goalsService.goals(profileEntity).iterator().next().getTextOne().contains("Through"));
    }

    @Test
    public void both() {
        save(LocalDate.now(), LocalDate.now());

        assertTrue(goalsService.goals(profileEntity).iterator().next().getTextOne().contains("through"));
    }

    private void save(LocalDate start, LocalDate end) {
        GoalEntity goal = new GoalEntity();
        goal.setStartDate(start);
        goal.setEndDate(end);
        goal.setName("Goal");
        goal.setDashboard(Boolean.TRUE);
        goal.setProfile(profileEntity);

        goalEntityRepository.save(goal);
    }

}
