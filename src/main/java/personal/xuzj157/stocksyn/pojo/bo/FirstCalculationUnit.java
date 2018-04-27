package personal.xuzj157.stocksyn.pojo.bo;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 一层数据
 */
@Data
@Entity
public class FirstCalculationUnit {

    /**
     * 股票编号
     */
    @Id
    private String code;
    /**
     * 净利润增长率(%)
     */
    private double netincGrowRate;
    /**
     * 主营业务收入
     */
    private double mainbusiincome;
    /**
     * 历史主营业务收入平均值
     */
    private double mainbusiincomeAvg;
    /**
     * 净利润
     */
    private double netprofit;
    /**
     * 历史净利润平均值
     */
    private double netprofitAvg;
    /**
     * 资产总额
     */
    private double totalassets;
    /**
     * 历史资产总额平均值
     */
    private double totalassetsAvg;
    /**
     * 市盈率
     */
    private double peratio;
    /**
     * 市净率
     */
    private double bvRatio;
    /**
     * 时价
     */
    private double nowPrice;
    /**
     * 15日均价
     */
    private double fifteenPrice;
    /**
     * 15日最低价
     */
    private double lowFifteenPrice;
    /**
     * 30日均价
     */
    private double thirtyPrice;
    /**
     * 30日最低价
     */
    private double lowThirtyPrice;
    /**
     * 60日均价
     */
    private double sixtyPrice;
    /**
     * 60日最低价
     */
    private double lowSixtyPrice;
    /**
     * 180天均价
     */
    private double oneEightyPrice;
    /**
     * 180天最低价
     */
    private double lowOneEightyPrice;
    /**
     * 400天均价
     */
    private double fourHundredPrice;
    /**
     * 400天最低价
     */
    private double lowFourHundredPrice;
    /**
     * 历史均价
     */
    private double historyPrice;
    /**
     * 历史最低价
     */
    private double lowHistoryPrice;
}
