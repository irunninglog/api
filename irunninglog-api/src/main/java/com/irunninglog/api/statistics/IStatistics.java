package com.irunninglog.api.statistics;

import java.util.Collection;

public interface IStatistics {

    ISummary getSummary();

    IStatistics setSummary(ISummary summary);

    Collection<ITotalByYear> getYears();

    IStatistics setYears(Collection<ITotalByYear> years);

    IDataSet getDataSet();

    IStatistics setDataSet(IDataSet dataSet);

}
