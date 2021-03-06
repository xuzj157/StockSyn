package personal.xuzj157.stocksyn.pojo.po;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document(collection = "fin_info")
@Data
public class FinInfo {
    @Id
    private String id = UUID.randomUUID().toString().replaceAll("-","");

    /**
     * 股票编号
     */
    @DBRef
    private Symbol symbol;
    /**
     * 报表日期   YYYYMMDD
     */
    @JsonProperty("reportdate")
    private String reportDate;
    /**
     * 基本每股收益
     */
    private double basiceps;
    /**
     * 每股净资产
     */
    private double naps;
    /**
     * 每股现金流
     */
    @JsonProperty("opercashpershare")
    private double oPerCashPerShare;
    /**
     * 净资产增长率(%)
     */
    private double netassgrowrate;
    /**
     * 净资产收益率(加权)(%)
     */
    private double weightedroe;
    /**
     * 主营业务收入增长率(%)
     */
    @JsonProperty("mainbusincgrowrate")
    private double mainBusincGrowRate;
    /**
     * 净利润增长率(%)
     */
    @JsonProperty("netincgrowrate")
    private double netincGrowRate;
    /**
     * 总资产增长率(%)
     */
    private double totassgrowrate;
    /**
     * 销售毛利率(%)
     */
    private double salegrossprofitrto;
}
