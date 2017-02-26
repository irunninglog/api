package com.irunninglog.spring.data;

import com.irunninglog.api.data.IRoute;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
final class Route extends AbstractData<IRoute> implements IRoute {

    public Route() {
        super();

        logger.debug("Created an instance {}", hashCode());
    }
}
