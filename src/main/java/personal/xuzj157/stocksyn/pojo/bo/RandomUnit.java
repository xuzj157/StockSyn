package personal.xuzj157.stocksyn.pojo.bo;

import lombok.Data;
import personal.xuzj157.stocksyn.utils.MathUtils;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Data
public class RandomUnit {

    @Id
    String id = UUID.randomUUID().toString().replaceAll("-", "");

    private double netincGrowRate = MathUtils.logicS(1, 1, 2);
    /**
     * 主营业务收入
     * 1 / ( 1 + e^( -主营业务收入 / 历史主营业务收入平均值))
     */
    private double mainbusiincome = MathUtils.logicS(1, 1, 2);
    /**
     * 净利润
     * 1 / ( 1 + e^( -净利润 / 历史净利润平均值))
     */
    private double netprofit = MathUtils.logicS(1, 1, 2);
    /**
     * 1 / ( 1 + e^( -资产总额 / 历史资产总额平均值))
     */
    private double totalassets = MathUtils.logicS(1, 1, 2);
    /**
     * 1 / ( 1 + e^( -市盈率 / 市盈率平均值))
     */
    private double peratio = MathUtils.logicS(1, 1, 2);
    /**
     * 1 / ( 1 + e^( -市净率 / 市净率平均值))
     */
    private double bvRatio = MathUtils.logicS(1, 1, 2);
    /**
     * 15日均价 / 时价
     */
    private double fifteenPrice = MathUtils.logicS(1, 1, 2);
    /**
     * 15日最低价 / 时价
     */
    private double lowFifteenPrice = MathUtils.logicS(1, 1, 2);
    /**
     * 30日均价 / 时价
     */
    private double thirtyPrice = MathUtils.logicS(1, 1, 2);
    /**
     * 30日最低价 / 时价
     */
    private double lowThirtyPrice = MathUtils.logicS(1, 1, 2);
    /**
     * 60日均价 / 时价
     */
    private double sixtyPrice = MathUtils.logicS(1, 1, 2);
    /**
     * 60日最低价 / 时价
     */
    private double lowSixtyPrice = MathUtils.logicS(1, 1, 2);
    /**
     * 180天均价 / 时价
     */
    private double oneEightyPrice = MathUtils.logicS(1, 1, 2);
    /**
     * 180天最低价 / 时价
     */
    private double lowOneEightyPrice = MathUtils.logicS(1, 1, 2);
    /**
     * 400天均价 / 时价
     */
    private double fourHundredPrice = MathUtils.logicS(1, 1, 2);
    /**
     * 400天最低价 / 时价
     */
    private double lowFourHundredPrice = MathUtils.logicS(1, 1, 2);
    /**
     * 历史均价 / 时价
     */
    private double historyPrice = MathUtils.logicS(1, 1, 2);
    /**
     * 历史最低价 / 时价
     */
    private double lowHistoryPrice = MathUtils.logicS(1, 1, 2);


}
