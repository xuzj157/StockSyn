package personal.xuzj157.stocksyn.crawler;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public abstract class CrawlerInterface {

    /**
     * 进度查询
     */
    @JsonProperty("order_id")
    public String orderId;

    public abstract String login();

    public abstract String parse();

    public abstract String save();


}
