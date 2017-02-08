package com.irunninglog.spring.workout;

import com.irunninglog.api.Privacy;
import com.irunninglog.spring.data.RouteEntity;
import com.irunninglog.spring.data.RunEntity;
import com.irunninglog.spring.data.ShoeEntity;
import com.irunninglog.spring.jpa.AbstractEntityWithProfile;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "workout_entity")
@SuppressWarnings("ALL")
public final class WorkoutEntity extends AbstractEntityWithProfile {

    @Column(nullable = false)
    private double distance;

    @Column(nullable = false)
    private long duration;

    @Column(nullable = false, name = "ddate")
    private LocalDate date;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Privacy privacy;

    @ManyToOne
    private RouteEntity route;

    @ManyToOne
    private RunEntity run;

    @ManyToOne
    private ShoeEntity shoe;

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
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

    public void setPrivacy(Privacy privacy) {
        this.privacy = privacy;
    }

    public RouteEntity getRoute() {
        return route;
    }

    public void setRoute(RouteEntity route) {
        this.route = route;
    }

    public RunEntity getRun() {
        return run;
    }

    public void setRun(RunEntity run) {
        this.run = run;
    }

    public ShoeEntity getShoe() {
        return shoe;
    }

    public void setShoe(ShoeEntity shoe) {
        this.shoe = shoe;
    }

}
