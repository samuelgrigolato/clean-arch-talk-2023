package io.bestbankever.domain;

import java.util.SortedSet;
import java.util.function.BiFunction;

interface FindMonthlyInterestRatesByPeriodFunction extends BiFunction<MonthYear, MonthYear, SortedSet<MonthlyInterestRate>> {
    @Override
    SortedSet<MonthlyInterestRate> apply(MonthYear start, MonthYear end);
}
