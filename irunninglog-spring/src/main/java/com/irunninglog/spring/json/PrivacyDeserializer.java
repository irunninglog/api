package com.irunninglog.spring.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.irunninglog.api.Privacy;

import java.io.IOException;

public class PrivacyDeserializer extends StdDeserializer<Privacy> {

    protected PrivacyDeserializer() {
        super(Privacy.class);
    }

    @Override
    public Privacy deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        return Privacy.valueOf(p.getValueAsString().toUpperCase());
    }

}
