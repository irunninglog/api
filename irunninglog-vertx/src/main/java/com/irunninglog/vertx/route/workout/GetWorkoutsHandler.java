package com.irunninglog.vertx.route.workout;

import com.irunninglog.service.Endpoint;
import com.irunninglog.service.ResponseStatus;
import com.irunninglog.service.ResponseStatusException;
import com.irunninglog.vertx.route.AbstractRouteHandler;
import com.irunninglog.vertx.route.RouteHandler;
import com.irunninglog.workout.GetWorkoutsRequest;
import com.irunninglog.workout.GetWorkoutsResponse;
import io.vertx.core.Vertx;
import io.vertx.ext.web.RoutingContext;

import java.util.regex.Pattern;

@RouteHandler(endpoint = Endpoint.GetWorkouts)
public final class GetWorkoutsHandler extends AbstractRouteHandler<GetWorkoutsRequest, GetWorkoutsResponse> {

    private static final Pattern PATTERN1 = Pattern.compile("^\\bworkouts\\b$");
    private static final Pattern PATTERN2 = Pattern.compile("^\\bworkouts\\b/(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])-(19|20)\\d\\d$");

    public GetWorkoutsHandler(Vertx vertx) {
        super(vertx, GetWorkoutsResponse.class);
    }

    @Override
    protected GetWorkoutsRequest request(RoutingContext routingContext) {
        String profileId = routingContext.pathParam("profileid");

        logger.info("profile:get:{}", profileId);

        String path = routingContext.normalisedPath().substring(routingContext.normalisedPath().indexOf("workouts"));
        if (!PATTERN1.matcher(path).matches() && !PATTERN2.matcher(path).matches()) {
            logger.error("Not a match {}", path);

            throw new ResponseStatusException(ResponseStatus.NotFound);
        }

        String [] tokens = path.split("/");
        String date = null;
        if (tokens.length == 2) {
            date = tokens[1];
        }

        return new GetWorkoutsRequest().setId(Long.parseLong(profileId)).setDate(date);
    }

}
