package com.irunninglog.spring.data;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.irunninglog.api.data.IShoe;
import com.irunninglog.api.data.IShoes;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Scope("prototype")
final class Shoes implements IShoes {

    private final List<IShoe> shoeList = new ArrayList<>();

    @Override
    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, defaultImpl = Shoe.class)
    public List<IShoe> getShoes() {
        return shoeList;
    }

}
