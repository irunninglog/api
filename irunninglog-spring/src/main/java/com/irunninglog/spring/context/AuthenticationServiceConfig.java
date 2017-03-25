package com.irunninglog.spring.context;

public final class AuthenticationServiceConfig {

    private final String key;
    private final long duration;

    public AuthenticationServiceConfig(String config) {
        super();

        String[] tokens = config.split("\\|");
        duration = Long.valueOf(tokens[0]);
        key = tokens[1];
    }

    public String getKey() {
        return key;
    }

    public long getDuration() {
        return duration;
    }

}
