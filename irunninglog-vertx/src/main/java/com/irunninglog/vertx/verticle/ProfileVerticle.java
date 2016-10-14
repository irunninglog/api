package com.irunninglog.vertx.verticle;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.irunninglog.api.ResponseStatus;
import com.irunninglog.api.ResponseStatusException;
import com.irunninglog.api.profile.IProfileService;
import com.irunninglog.api.profile.ProfileRequest;
import com.irunninglog.api.profile.ProfileResponse;
import com.irunninglog.vertx.Address;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.Json;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ProfileVerticle extends AbstractVerticle {

    private static final Logger LOG = LoggerFactory.getLogger(ProfileVerticle.class);

    private final IProfileService profileService;
    private final ObjectMapper mapper = Json.mapper;

    public ProfileVerticle(IProfileService profileService) {
        this.profileService = profileService;
    }

    @Override
    public void start() throws Exception {
        super.start();

        LOG.info("Profile verticle started");

        vertx.eventBus().<String>consumer(Address.Profile.getAddress()).handler(handler(profileService));
    }

    private Handler<Message<String>> handler(IProfileService profileService) {
        return msg -> vertx.<String>executeBlocking(future -> {
                    try {
                        ProfileRequest request = Json.decodeValue(msg.body(), ProfileRequest.class);
                        ProfileResponse response = profileService.get(request);

                        LOG.info("Successfully got a response {}", response);

                        future.complete(Json.encode(response));
                    } catch (Exception ex) {
                        LOG.error("Caught an exception: [{}]", ex.toString());

                        ProfileResponse response = fromException(ex);

                        LOG.error("Translated exception to response {}", response);

                        future.complete(Json.encode(response));
                    }
                },
                result -> {
                    if (result.succeeded()) {
                        msg.reply(result.result());
                    } else {
                        msg.fail(ResponseStatus.Error.getCode(), ResponseStatus.Error.getMessage());
                    }
                });
    }

    private ProfileResponse fromException(Exception ex) {
        ProfileResponse response = new ProfileResponse();

        if (ex instanceof ResponseStatusException) {
            response.setStatus(((ResponseStatusException) ex).getResponseStatus());
        } else {
            response.setStatus(ResponseStatus.Error);
        }

        return response;
    }

}
