package com.irunninglog.spring.profile;

import com.irunninglog.api.Gender;
import com.irunninglog.api.Unit;
import com.irunninglog.spring.data.RouteEntity;
import com.irunninglog.spring.data.RunEntity;
import com.irunninglog.spring.data.ShoeEntity;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalDate;

@SuppressWarnings({"unused", "WeakerAccess"})
@Entity
@Table(name = "user_entity")
public final class ProfileEntity {

    @javax.persistence.Id
    @javax.persistence.GeneratedValue(strategy=GenerationType.TABLE)
    private long id;

    @javax.persistence.Column(name="username", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name="first_name", nullable = false)
    private String firstName;

    @Column(name="last_name", nullable = false)
    private String lastName;

    @Column(name="weekly", nullable = false)
    private double weeklyTarget;

    @Column(name="monthly", nullable = false)
    private double monthlyTarget;

    @Column(name="yearly", nullable = false)
    private double yearlyTarget;

    @Column(name="week_start", nullable = false)
    @Enumerated(EnumType.STRING)
    private DayOfWeek weekStart;

    @Column(name="preferred_units", nullable = false)
    @Enumerated(EnumType.STRING)
    private Unit preferredUnits;

    @Column(nullable = false)
    private LocalDate birthday;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public ShoeEntity getDefaultShoe() {
        return defaultShoe;
    }

    public void setDefaultShoe(ShoeEntity defaultShoe) {
        this.defaultShoe = defaultShoe;
    }

    public RouteEntity getDefaultRoute() {
        return defaultRoute;
    }

    public void setDefaultRoute(RouteEntity defaultRoute) {
        this.defaultRoute = defaultRoute;
    }

    public RunEntity getDefaultRun() {
        return defaultRun;
    }

    public void setDefaultRun(RunEntity defaultRun) {
        this.defaultRun = defaultRun;
    }

}
