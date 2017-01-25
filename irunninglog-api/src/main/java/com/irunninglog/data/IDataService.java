package com.irunninglog.data;

public interface IDataService {

    GetShoesResponse shoes(GetDataRequest request);

    GetRunsResponse runs(GetDataRequest request);

    GetRoutesResponse routes(GetDataRequest request);

}
