package com.irunninglog.spring.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.irunninglog.api.Progress;

import java.io.IOException;

public class ProgressSerializer extends StdSerializer<Progress> {

    protected ProgressSerializer() {
        super(Progress.class);
    }

    @Override
    public void serialize(Progress value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeString(Character.toUpperCase(value.name().charAt(0)) + value.name().substring(1).toLowerCase());
    }

}
