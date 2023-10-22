package io.bestbankever.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import io.bestbankever.domain.MonthYear;

import java.io.IOException;
import java.time.Month;

public class MonthYearJsonDeserializer extends StdDeserializer<MonthYear> {

    public MonthYearJsonDeserializer() {
        this(null);
    }

    MonthYearJsonDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public MonthYear deserialize(JsonParser p, DeserializationContext ctx) throws IOException {
        String str = p.readValueAs(String.class);
        String[] parts = str.split("-");
        int year = Integer.valueOf(parts[0]);
        Month month = Month.of(Integer.valueOf(parts[1]));
        return new MonthYear(month, year);
    }

}
