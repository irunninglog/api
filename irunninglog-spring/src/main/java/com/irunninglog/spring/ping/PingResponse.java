package com.irunninglog.spring.ping;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.irunninglog.api.ping.IPingResponse;
import com.irunninglog.spring.AbstractResponse;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
final class PingResponse extends AbstractResponse<Ping, PingResponse> implements IPingResponse<Ping, PingResponse> {

    @Override
    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, defaultImpl = Ping.class)
    public Ping getBody() {
        return body();
    }

}
