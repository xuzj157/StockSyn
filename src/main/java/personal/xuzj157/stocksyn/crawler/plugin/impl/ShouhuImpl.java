package personal.xuzj157.stocksyn.crawler.plugin.impl;

import com.alibaba.fastjson.JSONArray;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import personal.xuzj157.stocksyn.crawler.plugin.ShouhuService;
import personal.xuzj157.stocksyn.pojo.po.HqInfo;
import personal.xuzj157.stocksyn.pojo.po.Symbol;
import personal.xuzj157.stocksyn.repository.HqInfoRepository;
import personal.xuzj157.stocksyn.repository.SymbolRepository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Service
public class ShouhuImpl implements ShouhuService {

    private String urlHistory = "http://q.stock.sohu.com/hisHq?code=cn_%s&start=19960101&end=20190120&stat=1&order=D&period=d&callback=historySearchHandler&rt=jsonp";

    @Resource
    RestTemplate restTemplate;
    @Autowired
    HqInfoRepository hqInfoRepository;
    @Autowired
    SymbolRepository symbolRepository;

    @Override
    public void getHistory() {

        List<Symbol> symbolList = symbolRepository.findAll();
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(12);

        for (Symbol symbol : symbolList) {
            String symbolStr = symbol.getStockCode();
            fixedThreadPool.execute(() -> {
                try {
                    String resultStr = restTemplate.getForObject(String.format(urlHistory, symbolStr), String.class);
                    resultStr = resultStr.replaceAll("historySearchHandler\\(", "");
                    resultStr = resultStr.replaceAll("\\)", "");
                    JSONArray jsonArray = JSONArray.parseArray(resultStr);
                    int status = jsonArray.getJSONObject(0).getInteger("status");
                    if (status == 0) {
                        List<HqInfo> hqInfoList = new ArrayList<>();
                        JSONArray hqArray = jsonArray.getJSONObject(0).getJSONArray("hq");
                        for (int i = 0; i < hqArray.size(); i++) {
                            HqInfo hqInfo = new HqInfo();
                            hqInfo.setData(hqArray.getJSONArray(i).getString(0).replaceAll("-", ""));
                            hqInfo.setOpen(Double.valueOf(hqArray.getJSONArray(i).getString(1)));
                            hqInfo.setClose(Double.valueOf(hqArray.getJSONArray(i).getString(2)));
                            hqInfo.setFloatW(Double.valueOf(hqArray.getJSONArray(i).getString(3)));
                            hqInfo.setFloatPre(Double.valueOf(hqArray.getJSONArray(i).getString(4).replaceAll("%", "")));
                            hqInfo.setLowPrice(Double.valueOf(hqArray.getJSONArray(i).getString(5)));
                            hqInfo.setHighPrice(Double.valueOf(hqArray.getJSONArray(i).getString(6)));
                            hqInfo.setVol(Double.valueOf(hqArray.getJSONArray(i).getString(7)) / 100);
                            hqInfo.setVolPrice(Double.valueOf(hqArray.getJSONArray(i).getString(8)));
                            hqInfo.setExchangeRate(Double.valueOf(hqArray.getJSONArray(i).getString(9).replaceAll("%", "")));
                            hqInfo.setCode(symbol.getStockCode());
                            hqInfoList.add(hqInfo);
                        }
                        hqInfoRepository.saveAll(hqInfoList);
                    }
                } catch (Exception e) {
                    log.error("搜狐抓取失败 symbolStr： " + symbolStr + "\n", e);
                }
            });
        }
        log.info("shouhu     finish");
    }
}
