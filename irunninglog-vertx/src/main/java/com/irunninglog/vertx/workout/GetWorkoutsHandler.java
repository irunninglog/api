package com.irunninglog.vertx.workout;

import com.irunninglog.api.Endpoint;
import com.irunninglog.api.ResponseStatus;
import com.irunninglog.api.ResponseStatusException;
import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.mapping.IMapper;
import com.irunninglog.api.workout.IGetWorkoutsRequest;
import com.irunninglog.api.workout.IGetWorkoutsResponse;
import com.irunninglog.vertx.AbstractProfileIdRouteHandler;
import com.irunninglog.vertx.RouteHandler;
import io.vertx.core.Vertx;
import io.vertx.ext.web.RoutingContext;

import java.util.regex.Pattern;

@RouteHandler(endpoint = Endpoint.WORKOUTS_GET)
public final class GetWorkoutsHandler extends AbstractProfileIdRouteHandler<IGetWorkoutsRequest, IGetWorkoutsResponse> {

    private static final Pattern PATTERN1 = Pattern.compile("^\\bworkouts\\b$");
    private static final Pattern PATTERN2 = Pattern.compile("^\\bworkouts\\b/(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])-(19|20)\\d\\d$");

    public GetWorkoutsHandler(Vertx vertx, IFactory factory, IMapper mapper) {
        super(vertx, factory, mapper, IGetWorkoutsRequest.class, IGetWorkoutsResponse.class);
    }

    @Override
    protected void populateRequest(IGetWorkoutsRequest request, RoutingContext routingContext) {
        super.populateRequest(request, routingContext);

        String path = routingContext.normalisedPath().substring(routingContext.normalisedPath().indexOf("workouts"));
        if (!PATTERN1.matcher(path).matches() && !PATTERN2.matcher(path).matches()) {
            logger.error("Not a match {}", path);

            throw new ResponseStatusException(ResponseStatus.NOT_FOUND);
        }

        String [] tokens = path.split("/");
        String date = null;
        if (tokens.length == 2) {
            date = tokens[1];
        }

        request.setDate(date);
    }

}
