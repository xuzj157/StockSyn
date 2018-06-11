package personal.xuzj157.stocksyn.crawler.plugin.impl;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import personal.xuzj157.stocksyn.crawler.plugin.BaiduService;
import personal.xuzj157.stocksyn.pojo.po.SnapShot;
import personal.xuzj157.stocksyn.pojo.po.Symbol;
import personal.xuzj157.stocksyn.repository.SnapShotRepository;
import personal.xuzj157.stocksyn.repository.SymbolRepository;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Service
public class BaiduImpl implements BaiduService {
    private String urlGetAll = "https://gupiao.baidu.com/api/stocks/stockbets?from=h5&os_ver=0&cuid=xxx&vv=2.2&format=json&stock_code=%s";

    @Autowired
    RestTemplate restTemplate;
    @Resource
    SnapShotRepository snapShotRepository;
    @Resource
    SymbolRepository symbolRepository;

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
        log.info("baidu_snapShot:     finsh");
        return null;
    }

    @Override
    public SnapShot getSnapShotFromAndroid() {
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        List<Symbol> symbolList = symbolRepository.findAll();
        for (Symbol symbol : symbolList) {
            String symbolStr = symbol.getExchange().toLowerCase() + symbol.getStockCode();
            executorService.execute(() -> {
                String resultStr = restTemplate.getForObject(String.format(urlGetAll, symbolStr), String.class);
                SnapShot snapShot = JSONObject.parseObject(resultStr).getObject("snapShot", SnapShot.class);
                snapShot.setCode(symbol.getStockCode());
                snapShotRepository.save(snapShot);
            });
        }
        log.info("baidu_snapShot:     finsh");
        return null;
    }
}