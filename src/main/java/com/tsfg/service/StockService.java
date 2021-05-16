package com.tsfg.service;

import com.tsfg.model.Quote;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;

@Service
public class StockService {
    private final WebClient client;
    @Value("${alpha-vantage.api-key}")
    private String apiKey;
    @Value("${alpha-vantage.base-url}")
    private String baseUrl;

    public StockService(WebClient client) {
        this.client = client;
    }

    public Mono<Quote> getQuote(String ticker) {
        return client.mutate()
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .baseUrl(baseUrl)
                .build()
                .get()
                .uri((UriBuilder uriBuilder) -> uriBuilder.queryParam("function", "GLOBAL_QUOTE")
                        .queryParam("symbol", ticker)
                        .queryParam("apikey", apiKey)
                        .build())
                .retrieve()
                .bodyToMono(Quote.class);
    }
}
