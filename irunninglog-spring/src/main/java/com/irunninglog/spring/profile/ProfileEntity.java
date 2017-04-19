package com.irunninglog.spring.profile;

import com.irunninglog.api.Unit;
import com.irunninglog.spring.data.RouteEntity;
import com.irunninglog.spring.data.RunEntity;
import com.irunninglog.spring.data.ShoeEntity;

import javax.persistence.*;
import java.time.DayOfWeek;

@Entity
@Table(name = "irl_entity_profile")
public final class ProfileEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.TABLE)
    private long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name="target_weekly", nullable = false)
    private double weeklyTarget;

    @Column(name="target_monthly", nullable = false)
    private double monthlyTarget;

    @Column(name="target_yearly", nullable = false)
    private double yearlyTarget;

    @Column(name="week_start", nullable = false)
    @Enumerated(EnumType.STRING)
    private DayOfWeek weekStart;

    @Column(name="preferred_units", nullable = false)
    @Enumerated(EnumType.STRING)
    private Unit preferredUnits;

    @ManyToOne
    @JoinColumn(name = "default_shoe_id")
    private ShoeEntity defaultShoe;

    @ManyToOne
    @JoinColumn(name = "default_route_id")
    private RouteEntity defaultRoute;

    @ManyToOne
    @JoinColumn(name = "default_run_id")
    private RunEntity defaultRun;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String email) {
        this.username = email;
    }

    public double getWeeklyTarget() {
        return weeklyTarget;
    }

    public void setWeeklyTarget(double weeklyTarget) {
        this.weeklyTarget = weeklyTarget;
    }

    public  double getMonthlyTarget() {
        return monthlyTarget;
    }

    public void setMonthlyTarget(double monthlyTarget) {
        this.monthlyTarget = monthlyTarget;
    }

    public double getYearlyTarget() {
        return yearlyTarget;
    }

    public void setYearlyTarget(double yearlyTarget) {
        this.yearlyTarget = yearlyTarget;
    }

    public DayOfWeek getWeekStart() {
        return weekStart;
    }

    public void setWeekStart(DayOfWeek weekStart) {
        this.weekStart = weekStart;
    }

    public Unit getPreferredUnits() {
        return preferredUnits;
    }

    public void setPreferredUnits(Unit preferredUnits) {
        this.preferredUnits = preferredUnits;
    }

    ShoeEntity getDefaultShoe() {
        return defaultShoe;
    }

    public void setDefaultShoe(ShoeEntity defaultShoe) {
        this.defaultShoe = defaultShoe;
    }

    RouteEntity getDefaultRoute() {
        return defaultRoute;
    }

    public void setDefaultRoute(RouteEntity defaultRoute) {
        this.defaultRoute = defaultRoute;
    }

    RunEntity getDefaultRun() {
        return defaultRun;
    }

    public void setDefaultRun(RunEntity defaultRun) {
        this.defaultRun = defaultRun;
    }

}
