package com.irunninglog.api.data;

public interface IData<T extends IData> {

    T setId(long id);

    T setName(String name);

    T setDescription(String description);

    T setDashboard(boolean dashboard);

    String getName();

}
