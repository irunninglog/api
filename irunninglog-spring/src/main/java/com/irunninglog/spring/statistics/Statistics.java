package com.irunninglog.spring.statistics;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.irunninglog.api.statistics.IStatistics;
import com.irunninglog.api.statistics.ISummary;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
final class Statistics implements IStatistics {

    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, defaultImpl = Summary.class)
    private ISummary summary;

    @Override
    public ISummary getSummary() {
        return summary;
    }

    @Override
    public IStatistics setSummary(ISummary summary) {
        this.summary = summary;
        return this;
    }

}
