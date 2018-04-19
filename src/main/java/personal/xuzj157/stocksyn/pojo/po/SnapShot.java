package personal.xuzj157.stocksyn.pojo.po;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
@Data
public class SnapShot {
    /**
     * 股票编号
     */
    @Id
    private String code;
    /**
     * 市盈率
     */
    private double peratio;
    /**
     * 市净率
     */
    private double bvRatio;
    /**
     * 每股收益
     */
    private double perShareEarn;
    /**
     * 每股净资产
     */
    private double netAssetsPerShare;
    /**
     * 总股本
     */
    private Integer totalShareCapital;
    /**
     * 流通股
     */
    private Integer circulatingCapital;
}
