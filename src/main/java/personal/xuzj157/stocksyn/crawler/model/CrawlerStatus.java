package personal.xuzj157.stocksyn.crawler.model;

import lombok.Data;

@Data
public class CrawlerStatus {
    /**
     * 单号
     */
    private String orderId;
    /**
     * 状态
     */
    private String status;
    /**
     * 类型
     */
    private String type;
    /**
     * 组织号
     */
    private String orgId;
}
