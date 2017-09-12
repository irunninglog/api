package com.irunninglog.spring.statistics;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.irunninglog.api.statistics.IStatistics;
import com.irunninglog.api.statistics.ISummary;
import com.irunninglog.api.statistics.ITotalByYear;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@Scope("prototype")
final class Statistics implements IStatistics {

    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, defaultImpl = Summary.class)
    private ISummary summary;
    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, defaultImpl = TotalByYear.class)
    private Collection<ITotalByYear> years;

    @Override
    public ISummary getSummary() {
        return summary;
    }

    @Override
    public IStatistics setSummary(ISummary summary) {
        this.summary = summary;
        return this;
    }

    @Override
    public Collection<ITotalByYear> getYears() {
        return this.years;
    }

    @Override
    public IStatistics setYears(Collection<ITotalByYear> years) {
        this.years = years;
        return this;
    }

}
