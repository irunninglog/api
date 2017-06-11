package com.irunninglog.spring;

import com.irunninglog.api.IRequest;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Scope("prototype")
public final class DefaultRequest implements IRequest {

    private Map<String, Object> map;

    @Override
    public IRequest setMap(Map<String, Object> map) {
        this.map = map;
        return this;
    }

    @Override
    public Map<String, Object> getMap() {
        return map;
    }

}
