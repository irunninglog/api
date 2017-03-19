package com.irunninglog.spring.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.irunninglog.api.Gender;

import java.io.IOException;

public class GenderDeserializer extends StdDeserializer<Gender>{

    protected GenderDeserializer() {
        super(Gender.class);
    }

    @Override
    public Gender deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        return Gender.valueOf(p.getValueAsString().toUpperCase());
    }

}
