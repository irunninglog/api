package com.irunninglog.spring.report;

import com.irunninglog.api.report.IDataSet;
import com.irunninglog.api.report.IMultiSet;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Scope("prototype")
final class MultiSet implements IMultiSet {

    private final List<IDataSet> data = new ArrayList<>();

    @Override
    public List<IDataSet> getData() {
        return data;
    }

}
