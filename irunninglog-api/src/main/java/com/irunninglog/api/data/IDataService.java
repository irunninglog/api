package com.irunninglog.api.data;

public interface IDataService {

    IShoes shoes(long profileId);

    IRuns runs(long profileId);

    IRoutes routes(long profileId);

}
