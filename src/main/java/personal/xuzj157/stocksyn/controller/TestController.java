package personal.xuzj157.stocksyn.controller;

import com.alibaba.fastjson.JSONObject;
import com.mongodb.BasicDBObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import personal.xuzj157.stocksyn.crawler.plugin.DongFangService;
import personal.xuzj157.stocksyn.crawler.plugin.XueQiuService;
import personal.xuzj157.stocksyn.db.MongoDB;

import javax.annotation.Resource;
import java.net.URISyntaxException;
import java.util.List;

@RestController
public class TestController {

    @Resource
    DongFangService dongFangService;

    @Resource
    XueQiuService xueQiuService;

    @RequestMapping("/dongfang")
    public void GetAllTest() throws URISyntaxException {
        dongFangService.getStockInfo();
    }

    @RequestMapping("/mongo")
    public String MongoTest() {
        List<JSONObject> jsonObjects = MongoDB.getResultListFromDB("info",new BasicDBObject(),new BasicDBObject(),JSONObject.class);
        return jsonObjects.get(0).toJSONString();
    }

    @RequestMapping("/xueqiu/compInfo")
    public void xueqiuTest(){
        xueQiuService.getCompInfo();
        xueQiuService.getFin();
    }
}
