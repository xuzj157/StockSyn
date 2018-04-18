package personal.xuzj157.stocksyn.crawler.plugin.impl;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import personal.xuzj157.stocksyn.crawler.plugin.BaiduService;
import personal.xuzj157.stocksyn.pojo.po.SnapShot;
import personal.xuzj157.stocksyn.repository.SnapShotRepository;

import javax.annotation.Resource;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class BaiduImpl implements BaiduService {
    private String urlGetAll = "https://gupiao.baidu.com/api/stocks/stockbets?from=h5&os_ver=0&cuid=xxx&vv=2.2&format=json&stock_code=%s";

    @Autowired
    RestTemplate restTemplate;
    @Resource
    SnapShotRepository snapShotRepository;

    @Override
    public SnapShot getSnapShotFromAndroid(int start, int end, String name) {
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        while (end >= start) {
            String symbol = name + String.format("%06d", start);
            executorService.execute(() -> {
                String resultStr = restTemplate.getForObject(String.format(urlGetAll, symbol), String.class);
                SnapShot snapShot = JSONObject.parseObject(resultStr).getObject("snapShot", SnapShot.class);
                snapShotRepository.save(snapShot);

            });
            start++;
        }
        return null;
    }
}
