package personal.xuzj157.stocksyn.pojo.bo;

import lombok.Data;
import personal.xuzj157.stocksyn.utils.MathUtils;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Data
public class RandomUnitS {

    @Id
    String id = UUID.randomUUID().toString().replaceAll("-", "");

    private double netincGrowRate = MathUtils.random(15);
    /**
     * 主营业务收入
     * 1 / ( 1 + e^( -主营业务收入 / 历史主营业务收入平均值))
     */
    private double mainbusiincome = MathUtils.random(15);
    /**
     * 净利润
     * 1 / ( 1 + e^( -净利润 / 历史净利润平均值))
     */
    private double netprofit = MathUtils.random(15);
    /**
     * 1 / ( 1 + e^( -资产总额 / 历史资产总额平均值))
     */
    private double totalassets = MathUtils.random(15);
    /**
     * 1 / ( 1 + e^( -市盈率 / 市盈率平均值))
     */
    private double peratio = MathUtils.random(15);
    /**
     * 1 / ( 1 + e^( -市净率 / 市净率平均值))
     */
    private double bvRatio = MathUtils.random(15);
    /**
     * 15日均价 / 时价
     */
    private double fifteenPrice = MathUtils.random(15);
    /**
     * 15日最低价 / 时价
     */
    private double lowFifteenPrice = MathUtils.random(15);
    /**
     * 30日均价 / 时价
     */
    private double thirtyPrice = MathUtils.random(15);
    /**
     * 30日最低价 / 时价
     */
    private double lowThirtyPrice = MathUtils.random(15);
    /**
     * 60日均价 / 时价
     */
    private double sixtyPrice = MathUtils.random(15);
    /**
     * 60日最低价 / 时价
     */
    private double lowSixtyPrice = MathUtils.random(15);
    /**
     * 180天均价 / 时价
     */
    private double oneEightyPrice = MathUtils.random(15);
    /**
     * 180天最低价 / 时价
     */
    private double lowOneEightyPrice = MathUtils.random(15);
    /**
     * 400天均价 / 时价
     */
    private double fourHundredPrice = MathUtils.random(15);
    /**
     * 400天最低价 / 时价
     */
    private double lowFourHundredPrice = MathUtils.random(15);
    /**
     * 历史均价 / 时价
     */
    private double historyPrice = MathUtils.random(15);
    /**
     * 历史最低价 / 时价
     */
    private double lowHistoryPrice = MathUtils.random(15);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        RandomUnitS that = (RandomUnitS) o;
        return Double.compare(that.netincGrowRate, netincGrowRate) == 0 &&
                Double.compare(that.mainbusiincome, mainbusiincome) == 0 &&
                Double.compare(that.netprofit, netprofit) == 0 &&
                Double.compare(that.totalassets, totalassets) == 0 &&
                Double.compare(that.peratio, peratio) == 0 &&
                Double.compare(that.bvRatio, bvRatio) == 0 &&
                Double.compare(that.fifteenPrice, fifteenPrice) == 0 &&
                Double.compare(that.lowFifteenPrice, lowFifteenPrice) == 0 &&
                Double.compare(that.thirtyPrice, thirtyPrice) == 0 &&
                Double.compare(that.lowThirtyPrice, lowThirtyPrice) == 0 &&
                Double.compare(that.sixtyPrice, sixtyPrice) == 0 &&
                Double.compare(that.lowSixtyPrice, lowSixtyPrice) == 0 &&
                Double.compare(that.oneEightyPrice, oneEightyPrice) == 0 &&
                Double.compare(that.lowOneEightyPrice, lowOneEightyPrice) == 0 &&
                Double.compare(that.fourHundredPrice, fourHundredPrice) == 0 &&
                Double.compare(that.lowFourHundredPrice, lowFourHundredPrice) == 0 &&
                Double.compare(that.historyPrice, historyPrice) == 0 &&
                Double.compare(that.lowHistoryPrice, lowHistoryPrice) == 0;
    }

}
