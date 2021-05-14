package service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import model.Quote;
import org.apache.http.client.utils.URIBuilder;

import java.io.IOException;
import java.net.HttpURLConnection;

public class StockService {
    private static final String BASE_URL = "https://www.alphavantage.co/query";
    private static final String API_KEY = "VR432GZWF4TXK9BP";
    private ObjectMapper mapper = new ObjectMapper();

    public StockService() {
        this.mapper = new ObjectMapper();
    }

    @SneakyThrows
    public Quote getQuote(String ticker) throws IOException {
        URIBuilder builder = new URIBuilder(BASE_URL);
        builder.addParameter("function", "GLOBAL_QUOTE");
        builder.addParameter("symbol", ticker);
        builder.addParameter("apikey", API_KEY);

        HttpURLConnection conn = (HttpURLConnection) builder.build().toURL().openConnection();
        conn.setRequestMethod("GET");

        JsonNode jsonNode = mapper.readValue(conn.getInputStream(), JsonNode.class);
        return mapper.readValue(jsonNode.get("Global Quote").toString(), Quote.class);
    }
}
