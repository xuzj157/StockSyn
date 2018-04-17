package personal.xuzj157.stocksyn.crawler.plugin.impl;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import personal.xuzj157.stocksyn.crawler.plugin.BaiduService;
import personal.xuzj157.stocksyn.pojo.po.SnapShot;

@Service
public class BaiduImpl implements BaiduService {
    private String urlGetAll = "https://gupiao.baidu.com/api/stocks/stockbets?from=h5&os_ver=0&cuid=xxx&vv=2.2&format=json&stock_code=%s";

    @Autowired
    RestTemplate restTemplate;

    @Override
    public SnapShot getSnapShotFromAndroid(int start, int end, String name) {
        while (end >= start) {
            String symbol = name + String.format("%06d", start);
            String resultStr = restTemplate.getForObject(String.format(urlGetAll, symbol), String.class);
            SnapShot snapShot = JSONObject.parseObject(resultStr).getObject("snapShot", SnapShot.class);
            //todo 入库
            start++;
        }
        return null;
    }
}
