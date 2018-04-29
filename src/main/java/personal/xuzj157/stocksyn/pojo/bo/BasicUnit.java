package personal.xuzj157.stocksyn.pojo.bo;


import lombok.Data;

/**
 * 基础数据单元
 */
@Data
public class BasicUnit {
    /**
     * 平均净利润增长率(%)
     */
    private double netincGrowRateAvg;
    /**
     * 平均市盈率
     */
    private double peratioAvg;
    /**
     * 平均市净率
     */
    private double bvRatioAvg;
}
