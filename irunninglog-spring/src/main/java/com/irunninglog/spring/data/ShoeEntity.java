package com.irunninglog.spring.data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "shoe_entity")
@SuppressWarnings("WeakerAccess")
// Needs to be public
public final class ShoeEntity extends AbstractDataEntity {

    @Column(name="start_date")
    private LocalDate startDate;

    @Column(name="max_value", nullable = false)
    private double max;

    public ShoeEntity() {
        super();

        logger.debug("Created an instance {}", hashCode());
    }

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
