package personal.xuzj157.stocksyn.pojo.po;

import lombok.Data;

@Data
public class StockInfo {

    private String code;
    private String name;
    private BasicInfo basicInfo;

    public StockInfo() {
    }

    public StockInfo(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
