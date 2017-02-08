package com.irunninglog.api.data;

public interface IShoe extends IData<IShoe> {

    IShoe setStartDate(String s);

    IShoe setMax(String max);

    String getStartDate();

}
