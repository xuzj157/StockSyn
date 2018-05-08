package personal.xuzj157.stocksyn.pojo.po;

import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Data
@Table(indexes = {@Index(name = "index_hq_info", columnList = "code")})
public class HqInfo {
    @Id
    private String id = UUID.randomUUID().toString().replaceAll("-", "");
    /**
     * 股票编号
     */
    private String code;
    /**
     * 日期 YYYYMMDD
     */
    private String data;
    /**
     * 开盘价
     */
    private double open;
    /**
     * 收盘价
     */
    private double close;
    /**
     * 浮动金额
     */
    private double floatW;
    /**
     * 浮动比例（百分比）
     */
    private double floatPre;
    /**
     * 最低
     */
    private double lowPrice;
    /**
     * 最高
     */
    private double highPrice;
    /**
     * 成交量（万）
     */
    private double vol;
    /**
     * 成交额（万）
     */
    private double volPrice;
    /**
     * 换手率（%）
     */
    private double exchangeRate;

}
