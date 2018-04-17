package personal.xuzj157.stocksyn.pojo.po;

import jdk.nashorn.internal.objects.annotations.Constructor;
import lombok.Data;

@Data
public class Symbol {
    /**
     * 股票代码
     */
    private String stockCode;
    /**
     * 证交所
     */

    private String exchange;

    public Symbol(String stockCode, String exchange) {
        this.stockCode = stockCode;
        this.exchange = exchange;
    }
}
