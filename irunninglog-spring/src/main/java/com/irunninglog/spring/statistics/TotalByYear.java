package com.irunninglog.spring.statistics;

import com.irunninglog.api.statistics.ITotalByYear;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
final class TotalByYear implements ITotalByYear {

    private int year;
    private String total;
    private int percentage;

    @Override
    public int getYear() {
        return year;
    }

    @Override
    public String getTotal() {
        return total;
    }

    @Override
    public int getPercentage() {
        return percentage;
    }

    @Override
    public ITotalByYear setYear(int year) {
        this.year = year;
        return this;
    }

    @Override
    public ITotalByYear setTotal(String total) {
        this.total = total;
        return this;
    }

    @Override
    public ITotalByYear setPercentage(int percentage) {
        this.percentage = percentage;
        return this;
    }

}
