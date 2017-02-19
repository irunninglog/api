package com.irunninglog.spring.data;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.irunninglog.api.data.IGetShoesResponse;
import com.irunninglog.api.data.IShoes;
import com.irunninglog.spring.AbstractResponse;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
final class GetShoesResponse extends AbstractResponse<IShoes, GetShoesResponse> implements IGetShoesResponse<GetShoesResponse> {

    @Override
    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, defaultImpl = Shoes.class)
    public IShoes getBody() {
        return body();
    }

}
