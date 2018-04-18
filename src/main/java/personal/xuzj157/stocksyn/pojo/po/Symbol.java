package personal.xuzj157.stocksyn.pojo.po;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "symbol")
@Data
public class Symbol {
    /**
     * 股票代码
     */
    @Id
    private String stockCode;
    /**
     * 证交所
     */

    private String exchange;

    public Symbol(String stockCode, String exchange) {
        this.stockCode = stockCode;
        this.exchange = exchange;
    }

    public Symbol() {
    }


}
