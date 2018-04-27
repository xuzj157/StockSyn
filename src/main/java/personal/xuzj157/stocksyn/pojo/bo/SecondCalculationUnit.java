package personal.xuzj157.stocksyn.pojo.bo;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 二层数据
 */
@Data
@Entity
public class SecondCalculationUnit {

    /**
     * 股票编号
     */
    @Id
    private String code;
    /**
     * 1 / ( 1 + e^( -净利润增长率 / 净利润增长率平均值))
     */
    private double netincGrowRate;
    /**
     * 主营业务收入
     * 1 / ( 1 + e^( -主营业务收入 / 历史主营业务收入平均值))
     */
    private double mainbusiincome;
    /**
     * 净利润
     * 1 / ( 1 + e^( -净利润 / 历史净利润平均值))
     */
    private double netprofit;
    /**
     * 1 / ( 1 + e^( -资产总额 / 历史资产总额平均值))
     */
    private double totalassets;
    /**
     * 1 / ( 1 + e^( -市盈率 / 市盈率平均值))
     */
    private double peratio;
    /**
     * 1 / ( 1 + e^( -市净率 / 市净率平均值))
     */
    private double bvRatio;
    /**
     * 15日均价 / 时价
     */
    private double fifteenPrice;
    /**
     * 15日最低价 / 时价
     */
    private double lowFifteenPrice;
    /**
     * 30日均价 / 时价
     */
    private double thirtyPrice;
    /**
     * 30日最低价 / 时价
     */
    private double lowThirtyPrice;
    /**
     * 60日均价 / 时价
     */
    private double sixtyPrice;
    /**
     * 60日最低价 / 时价
     */
    private double lowSixtyPrice;
    /**
     * 180天均价 / 时价
     */
    private double oneEightyPrice;
    /**
     * 180天最低价 / 时价
     */
    private double lowOneEightyPrice;
    /**
     * 400天均价 / 时价
     */
    private double fourHundredPrice;
    /**
     * 400天最低价 / 时价
     */
    private double lowFourHundredPrice;
    /**
     * 历史均价 / 时价
     */
    private double historyPrice;
    /**
     * 历史最低价 / 时价
     */
    private double lowHistoryPrice;
}
