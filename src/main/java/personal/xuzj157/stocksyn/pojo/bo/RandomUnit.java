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

    private double netincGrowRate = MathUtils.logicS(Math.random(), 1, 4);
    /**
     * 主营业务收入
     * 1 / ( 1 + e^( -主营业务收入 / 历史主营业务收入平均值))
     */
    private double mainbusiincome = MathUtils.logicS(Math.random(), 1, 4);
    /**
     * 净利润
     * 1 / ( 1 + e^( -净利润 / 历史净利润平均值))
     */
    private double netprofit = MathUtils.logicS(Math.random(), 1, 4);
    /**
     * 1 / ( 1 + e^( -资产总额 / 历史资产总额平均值))
     */
    private double totalassets = MathUtils.logicS(Math.random(), 1, 4);
    /**
     * 1 / ( 1 + e^( -市盈率 / 市盈率平均值))
     */
    private double peratio = MathUtils.logicS(Math.random(), 1, 4);
    /**
     * 1 / ( 1 + e^( -市净率 / 市净率平均值))
     */
    private double bvRatio = MathUtils.logicS(Math.random(), 1, 4);
    /**
     * 15日均价 / 时价
     */
    private double fifteenPrice = MathUtils.logicS(Math.random(), 1, 4);
    /**
     * 15日最低价 / 时价
     */
    private double lowFifteenPrice = MathUtils.logicS(Math.random(), 1, 4);
    /**
     * 30日均价 / 时价
     */
    private double thirtyPrice = MathUtils.logicS(Math.random(), 1, 4);
    /**
     * 30日最低价 / 时价
     */
    private double lowThirtyPrice = MathUtils.logicS(Math.random(), 1, 4);
    /**
     * 60日均价 / 时价
     */
    private double sixtyPrice = MathUtils.logicS(Math.random(), 1, 4);
    /**
     * 60日最低价 / 时价
     */
    private double lowSixtyPrice = MathUtils.logicS(Math.random(), 1, 4);
    /**
     * 180天均价 / 时价
     */
    private double oneEightyPrice = MathUtils.logicS(Math.random(), 1, 4);
    /**
     * 180天最低价 / 时价
     */
    private double lowOneEightyPrice = MathUtils.logicS(Math.random(), 1, 4);
    /**
     * 400天均价 / 时价
     */
    private double fourHundredPrice = MathUtils.logicS(Math.random(), 1, 4);
    /**
     * 400天最低价 / 时价
     */
    private double lowFourHundredPrice = MathUtils.logicS(Math.random(), 1, 4);
    /**
     * 历史均价 / 时价
     */
    private double historyPrice = MathUtils.logicS(Math.random(), 1, 4);
    /**
     * 历史最低价 / 时价
     */
    private double lowHistoryPrice = MathUtils.logicS(Math.random(), 6, 4);


}
