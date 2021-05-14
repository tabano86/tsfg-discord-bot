package model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Quote {
    @JsonProperty("01. symbol")
    public String symbol;
    @JsonProperty("02. open")
    public String open;
    @JsonProperty("03. high")
    public String high;
    @JsonProperty("04. low")
    public String low;
    @JsonProperty("05. price")
    public String price;
    @JsonProperty("06. volume")
    public String volume;
    @JsonProperty("07. latest trading day")
    public String latestTradingDay;
    @JsonProperty("08. previous close")
    public String previousClose;
    @JsonProperty("09. change")
    public String change;
    @JsonProperty("10. change percent")
    public String changePercent;
}
