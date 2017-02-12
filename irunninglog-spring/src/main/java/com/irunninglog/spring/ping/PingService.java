package com.irunninglog.spring.ping;

import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.ping.IPing;
import com.irunninglog.api.ping.IPingService;
import com.irunninglog.spring.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

@ApiService
final class PingService implements IPingService {

    private final IFactory factory;

    @Autowired
    public PingService(IFactory factory) {
        this.factory = factory;
    }

    @Override
    public IPing ping() {
        return factory.get(IPing.class).setTimestamp(LocalDateTime.now().toString());
    }

}
