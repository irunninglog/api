package com.irunninglog.api.data.impl;

import com.irunninglog.jpa.DateConverter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name="goal_entity")
public class GoalEntity extends AbstractDataEntity {

    @Column(name="start_date")
    @Convert(converter = DateConverter.class)
    private LocalDate startDate;

    @Column(name="end_date")
    @Convert(converter = DateConverter.class)
    private LocalDate endDate;

    @Column(nullable = false)
    private double goal;

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public double getGoal() {
        return goal;
    }

    public void setGoal(double goal) {
        this.goal = goal;
    }

}
