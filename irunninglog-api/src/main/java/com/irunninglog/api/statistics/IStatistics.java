package com.irunninglog.api.statistics;

import java.util.Collection;
import java.util.Map;

public interface IStatistics {

    ISummary getSummary();

    IStatistics setSummary(ISummary summary);

    Collection<ITotalByYear> getYears();

    IStatistics setYears(Collection<ITotalByYear> years);

    Map<String, IDataSet> getDataSets();

    IStatistics setDataSets(Map<String, IDataSet> dataSets);

}
