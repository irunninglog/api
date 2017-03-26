package com.irunninglog.api.data;

public interface IShoe extends IData<IShoe> {

    IShoe setStartDate(String startDate);

    IShoe setMax(String max);

    String getStartDate();

    String getMax();

}
