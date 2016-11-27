package com.irunninglog.spring.profile.impl;

import com.irunninglog.Gender;
import com.irunninglog.Unit;
import com.irunninglog.spring.jpa.DateConverter;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalDate;

@Entity
@Table(name = "user_entity")
public final class ProfileEntity {

    @javax.persistence.Id
    @javax.persistence.GeneratedValue(strategy=GenerationType.TABLE)
    private long id;

    @javax.persistence.Column(name="username", nullable = false, unique = true)
    private String email;

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
    @Convert(converter = DateConverter.class)
    private LocalDate birthday;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

}
