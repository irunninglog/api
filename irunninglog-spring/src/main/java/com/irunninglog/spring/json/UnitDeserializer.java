package com.irunninglog.spring.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.irunninglog.api.Unit;

import java.io.IOException;

public class UnitDeserializer extends StdDeserializer<Unit> {

    public UnitDeserializer() {
        super(Unit.class);
    }

    @Override
    public Unit deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        return Unit.valueOf(p.getValueAsString().toUpperCase());
    }

}
