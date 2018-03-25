package personal.xuzj157.stocksyn.crawler.manager;

import com.mongodb.BasicDBObject;
import personal.xuzj157.stocksyn.crawler.model.CrawlerStatus;
import personal.xuzj157.stocksyn.db.MongoDB;

import java.util.List;

/**
 * 抓取数据进度管理
 */
public class SchManage {

    private CrawlerStatus crawlerStatus;

    public SchManage(CrawlerStatus crawlerStatus) {
        this.crawlerStatus = crawlerStatus;
    }

    public CrawlerStatus statusCheck() {
        MongoDB mongoDB = new MongoDB();
        List<CrawlerStatus> crawlerStatusList = mongoDB.getResultListFromDB("crawler_status", new BasicDBObject("order_id", crawlerStatus.getOrderId()), new BasicDBObject(), CrawlerStatus.class);
        return crawlerStatusList.get(0);
    }
}
