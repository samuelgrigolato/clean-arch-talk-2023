package io.bestbankever.vos;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.time.LocalDate;
import java.time.Month;
import java.util.Comparator;

@JsonDeserialize(using = MonthYearJsonDeserializer.class)
public record MonthYear(
        Month month,
        int year
) implements Comparable<MonthYear> {
    public static MonthYear from(LocalDate date) {
        Month month = date.getMonth();
        int year = date.getYear();
        return new MonthYear(month, year);
    }

    @Override
    public int compareTo(MonthYear o) {
        return Comparator.comparingInt(MonthYear::year)
                .thenComparing(MonthYear::month)
                .compare(this, o);
    }
}
