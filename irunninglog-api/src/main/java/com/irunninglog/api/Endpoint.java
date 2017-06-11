package com.irunninglog.api;

import com.google.common.base.MoreObjects;

public enum Endpoint {

    GET_CHALLENGES("4e8d8e3d-c398-440c-9b2e-569f1e248b7b",
            "/api/challenges",
            AccessControl.PROFILE,
            EndpointMethod.GET),
    GET_PROFILE("fff11faa-bf42-442c-ac31-eb338c7cf81e",
            "/api/profile",
            AccessControl.PROFILE,
            EndpointMethod.GET),
    GET_SHOES("1f1b6a8f-07a5-46a3-a481-5ab2e01be51e",
            "/api/shoes",
            AccessControl.PROFILE,
            EndpointMethod.GET),
    GET_STREAKS("8f639fb2-f4ac-431e-83cb-25c957d0c835",
            "/api/streaks",
            AccessControl.PROFILE,
            EndpointMethod.GET),
    LOGIN("cdba3f2f-d195-48f6-829b-d28fb834508c",
            "/api/login",
            AccessControl.ANONYMOUS,
            EndpointMethod.GET),
    PING("6678d445-030f-4aad-b360-6304588c07b6",
            "/api/ping",
            AccessControl.ANONYMOUS,
            EndpointMethod.GET);

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
