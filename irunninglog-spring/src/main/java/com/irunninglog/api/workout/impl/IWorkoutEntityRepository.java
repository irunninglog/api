package com.irunninglog.api.workout.impl;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

@SuppressWarnings("JpaQlInspection")
public interface IWorkoutEntityRepository extends CrudRepository<WorkoutEntity, Long> {

    @Query(value = "select sum(w.distance) as sum from WorkoutEntity w where w.user.id = :profileId and w.shoe.id = :shoeId")
    BigDecimal shoeMileage(@Param("profileId") long profileId, @Param("shoeId") long shoeId);

}
