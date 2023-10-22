package io.bestbankever.adapters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import io.bestbankever.deserializers.MonthYearJsonDeserializer;
import io.bestbankever.domain.FindMonthlyInterestRatesByPeriodFunction;
import io.bestbankever.domain.MonthYear;
import io.bestbankever.domain.MonthlyInterestRate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

@Component
class AwesomeTaxFindMonthlyInterestRatesByPeriodFunction implements FindMonthlyInterestRatesByPeriodFunction {

    @Override
    public SortedSet<MonthlyInterestRate> apply(MonthYear start, MonthYear end) {
        try {
            String awesomeTaxUrl = System.getenv("AWESOMETAX_URL");
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .GET()
                    .uri(new URI(awesomeTaxUrl + "/interest-rates.json"))
                    .build();
            HttpClient httpClient = HttpClient.newBuilder()
                    .build();
            String response = httpClient
                    .send(httpRequest, HttpResponse.BodyHandlers.ofString())
                    .body();
            ObjectMapper mapper = new ObjectMapper();

            SimpleModule module = new SimpleModule();
            module.addDeserializer(MonthYear.class, new MonthYearJsonDeserializer());
            mapper.registerModule(module);

            MonthlyInterestRate[] rates = mapper.readValue(response, MonthlyInterestRate[].class);
            return new TreeSet<>(List.of(rates));
        } catch (IOException | InterruptedException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

}
