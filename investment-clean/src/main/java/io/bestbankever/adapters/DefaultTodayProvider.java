package io.bestbankever.adapters;

import io.bestbankever.domain.TodayProvider;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DefaultTodayProvider implements TodayProvider {
    @Override
    public LocalDate today() {
        return LocalDate.now();
    }
}
