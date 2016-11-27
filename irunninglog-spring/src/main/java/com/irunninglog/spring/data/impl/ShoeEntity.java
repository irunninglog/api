package com.irunninglog.spring.data.impl;

import com.irunninglog.spring.jpa.DateConverter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@SuppressWarnings("unused")
@Table(name = "shoe_entity")
public final class ShoeEntity extends AbstractDataEntity {

    @Column(name="start_date")
    @Convert(converter = DateConverter.class)
    private LocalDate startDate;

    @Column(name="max_value", nullable = false)
    private double max;

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

}
