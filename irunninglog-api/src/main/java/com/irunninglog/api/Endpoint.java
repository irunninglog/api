package com.irunninglog.api;

import com.google.common.base.MoreObjects;

public enum Endpoint {

    LOGIN("407a8fc3-ce60-4076-81d6-a7432dd7defb",
            "/authn",
            AccessControl.ALLOW,
            EndpointMethod.POST),
    DASHBOARD_GET("c83068b3-2d60-4578-b981-a6c173441ba4",
            "/profiles/:profileid/dashboard",
            AccessControl.PROFILE,
            EndpointMethod.GET),
    REPORT_MILEAGE_MONTH_GET("3faf5e22-be23-4148-b900-f11abc327b98",
            "/profiles/:profileid/reports/mileagebymonth",
            AccessControl.PROFILE,
            EndpointMethod.GET),
    REPORT_MILEAGE_ROUTE_GET("b29317e2-68a0-4512-8c21-98d5e7fdd937",
            "/profiles/:profileid/reports/mileagebyroute",
            AccessControl.PROFILE,
            EndpointMethod.GET),
    REPORT_MILEAGE_RUN_GET("e6cadd0a-bce9-437d-a81f-d63f27415777",
            "/profiles/:profileid/reports/mileagebyrun",
            AccessControl.PROFILE,
            EndpointMethod.GET),
    REPORT_MILEAGE_SHOE_GET("1e099ab5-4d70-4407-bd6b-931b2e7f3081",
            "/profiles/:profileid/reports/mileagebyshoe",
            AccessControl.PROFILE,
            EndpointMethod.GET),
    PROFILE_GET("fff11faa-bf42-442c-ac31-eb338c7cf81e",
            "/profiles/:profileid",
            AccessControl.PROFILE,
            EndpointMethod.GET),
    ROUTES_GET("cd36eb96-9552-4590-a33b-f67f41cfe64c",
            "/profiles/:profileid/routes",
            AccessControl.PROFILE,
            EndpointMethod.GET),
    RUNS_GET("05c28b1c-030f-4d6d-abe8-3d7d65daf665",
            "/profiles/:profileid/runs",
            AccessControl.PROFILE,
            EndpointMethod.GET),
    SHOES_GET("1f1b6a8f-07a5-46a3-a481-5ab2e01be51e",
            "/profiles/:profileid/shoes",
            AccessControl.PROFILE,
            EndpointMethod.GET),
    WORKOUTS_GET("8f639fb2-f4ac-431e-83cb-25c957d0c835",
            "/profiles/:profileid/workouts*",
            AccessControl.PROFILE,
            EndpointMethod.GET),
    PING("6678d445-030f-4aad-b360-6304588c07b6",
            "/ping",
            AccessControl.ANONYMOUS,
            EndpointMethod.GET),
    FORBIDDEN("a797bebb-80e7-4739-ac38-9154f4de740c",
            "/forbidden",
            AccessControl.DENY,
            EndpointMethod.GET),
    WORKOUT_PUT("1d80fac7-d440-4ea1-ac7a-73e4e361603d",
            "/profiles/:profileid/workouts",
            AccessControl.PROFILE,
            EndpointMethod.PUT),
    WORKOUT_DELETE("9dbc6789-ed5b-47d9-8302-c7dd4e96238e",
            "/profiles/:profileid/workouts/:workoutid",
            AccessControl.PROFILE,
            EndpointMethod.DELETE);

    private final String address;
    private final String path;
    private final AccessControl control;
    private final EndpointMethod method;

    Endpoint(String address, String path, AccessControl control, EndpointMethod method) {
        this.address = address;
        this.path = path;
        this.control = control;
        this.method = method;
    }

    public String getAddress() {
        return address;
    }

    public String getPath() {
        return path;
    }

    public AccessControl getControl() {
        return control;
    }

    public EndpointMethod getMethod() {
        return method;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("address", address)
                .add("path", path)
                .add("control", control)
                .add("method", method)
                .toString();
    }

}
