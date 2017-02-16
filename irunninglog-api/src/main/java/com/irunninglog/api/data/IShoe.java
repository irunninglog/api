package com.irunninglog.api.data;

@SuppressWarnings("unused")
public interface IShoe extends IData<IShoe> {

    IShoe setStartDate(String startDate);

    IShoe setMax(String max);

    String getStartDate();

}
