package com.irunninglog.spring.profile;

import com.irunninglog.api.profile.IGetProfileRequest;
import com.irunninglog.spring.AbstractProfileIdRequest;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
final class GetProfileRequest extends AbstractProfileIdRequest<IGetProfileRequest> implements IGetProfileRequest {

    public GetProfileRequest() {
        super();

        logger.debug("Created an instance {}", hashCode());
    }

}
