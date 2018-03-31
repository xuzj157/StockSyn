package personal.xuzj157.stocksyn.pojo.po;

import lombok.Data;

import java.util.List;

@Data
public class StockInfo {

    private String code;
    private String name;
    private BasicInfo basicInfo;
    private List<HqInfo> hqInfoList;
    private List<FinInfo> finInfoList;

    public StockInfo() {
    }

    public StockInfo(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
