package com.irunninglog.spring.report;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.irunninglog.api.report.IDataSet;
import com.irunninglog.api.report.IGetDataSetResponse;
import com.irunninglog.spring.AbstractResponse;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
final class GetDataSetResponse extends AbstractResponse<IDataSet, GetDataSetResponse> implements IGetDataSetResponse<GetDataSetResponse> {

    @Override
    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, defaultImpl = DataSet.class)
    public IDataSet getBody() {
        return body();
    }

}
