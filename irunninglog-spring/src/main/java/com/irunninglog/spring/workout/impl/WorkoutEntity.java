package com.irunninglog.spring.workout.impl;

import com.irunninglog.Privacy;
import com.irunninglog.spring.data.impl.ShoeEntity;
import com.irunninglog.spring.jpa.AbstractEntityWithUser;
import com.irunninglog.spring.jpa.DateConverter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "workout_entity")
public final class WorkoutEntity extends AbstractEntityWithUser {

    @Column(nullable = false)
    private double distance;

    @Column(nullable = false)
    private long duration;

    @Column(nullable = false, name = "ddate")
    @Convert(converter = DateConverter.class)
    private LocalDate date;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Privacy privacy;

    @ManyToOne
    private ShoeEntity shoe;

    public double getDistance() {
        return distance;
    }

    void setDistance(double distance) {
        this.distance = distance;
    }

    long getDuration() {
        return duration;
    }

    void setDuration(long duration) {
        this.duration = duration;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    Privacy getPrivacy() {
        return privacy;
    }

    void setPrivacy(Privacy privacy) {
        this.privacy = privacy;
    }

    ShoeEntity getShoe() {
        return shoe;
    }

    void setShoe(ShoeEntity shoe) {
        this.shoe = shoe;
    }

}
