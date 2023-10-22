package io.bestbankever.domain;

import java.time.LocalDate;

interface TodayProvider {
    LocalDate today();
}
