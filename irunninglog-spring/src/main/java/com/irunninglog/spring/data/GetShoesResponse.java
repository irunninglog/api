package com.irunninglog.spring.data;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.irunninglog.api.data.IGetShoesResponse;
import com.irunninglog.spring.AbstractResponse;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
final class GetShoesResponse extends AbstractResponse<Shoes, GetShoesResponse> implements IGetShoesResponse<Shoes, GetShoesResponse> {

    @Override
    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, defaultImpl = Shoes.class)
    public Shoes getBody() {
        return body();
    }

}
