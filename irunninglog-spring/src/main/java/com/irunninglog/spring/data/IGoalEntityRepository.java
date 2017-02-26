package com.irunninglog.spring.data;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

@SuppressWarnings("JpaQlInspection")
// Needs to be public
public interface IGoalEntityRepository extends CrudRepository<GoalEntity, Long> {

    @Query(value = "select g from GoalEntity g where g.dashboard = true and g.profile.id = :id")
    List<GoalEntity> dashboardGoals(@Param("id") long id);

}
