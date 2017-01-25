package com.irunninglog.data;

public final class Shoe extends AbstractData<Shoe> {

    private String startDate;
    private String max;

    public String getStartDate() {
        return startDate;
    }

    public Shoe setStartDate(String startDate) {
        this.startDate = startDate;
        return this;
    }

    public String getMax() {
        return max;
    }

    public Shoe setMax(String max) {
        this.max = max;
        return this;
    }

}
