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
     * 60日涨幅(%)   不参与运算
     * 1/4 * 前30日 + 3/4 * 后半段30日均价
     */
    private Double upRate;
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
     * 每股收益 / 时价
     */
    private double basiceps;
    /**
     * 每股净资产 / 时价
     */
    private double naps;
    /**
     * 每股现金流 / 时价
     */
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
    private double mainBusincGrowRate;
    /**
     * 总资产增长率(%)
     */
    private double totassgrowrate;
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
