package com.irunninglog.spring.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.irunninglog.api.Progress;

import java.io.IOException;

public class ProgressDeserializer extends StdDeserializer<Progress> {

    public ProgressDeserializer() {
        super(Progress.class);
    }

    @Override
    public Progress deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        return Progress.valueOf(p.getValueAsString().toUpperCase());
    }

}
