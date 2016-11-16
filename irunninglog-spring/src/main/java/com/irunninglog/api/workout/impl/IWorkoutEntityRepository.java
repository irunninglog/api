package com.irunninglog.api.workout.impl;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;

@SuppressWarnings("JpaQlInspection")
public interface IWorkoutEntityRepository extends CrudRepository<WorkoutEntity, Long> {

    @Query(value = "select sum(w.distance) as sum from WorkoutEntity w where w.user.id = :profileId and w.shoe.id = :shoeId")
    BigDecimal shoeMileage(@Param("profileId") long profileId, @Param("shoeId") long shoeId);

    @Query(value = "select sum(w.distance) as sum from WorkoutEntity w where w.user.id = :profileId")
    BigDecimal sumAll(@Param("profileId") long profileId);

    @Query(value = "select sum(w.distance) as sum from WorkoutEntity w where w.date >= :startDate and w.user.id = :profileId")
    BigDecimal sumFrom(@Param("startDate") LocalDate startDate, @Param("profileId") long profileId);

    @Query(value = "select sum(w.distance) as sum from WorkoutEntity w where w.date <= :endDate and w.user.id = :profileId")
    BigDecimal sumTo(@Param("endDate") LocalDate endDate, @Param("profileId") long profileId);

    @Query(value = "select sum(w.distance) as sum from WorkoutEntity w where w.date >= :startDate and w.date <= :endDate and w.user.id = :profileId")
    BigDecimal sumBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, @Param("profileId") long profileId);


}
