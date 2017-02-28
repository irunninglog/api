package com.irunninglog.spring.ping;

import com.irunninglog.api.ping.IPing;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@SuppressWarnings("unused")
final class Ping implements IPing {

    private String timestamp;

    @Override
    public IPing setTimestamp(String timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    @Override
    public String getTimestamp() {
        return timestamp;
    }

}
