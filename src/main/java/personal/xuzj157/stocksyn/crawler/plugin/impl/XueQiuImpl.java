package personal.xuzj157.stocksyn.crawler.plugin.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import personal.xuzj157.stocksyn.crawler.plugin.XueQiuService;
import personal.xuzj157.stocksyn.pojo.po.BasicInfo;
import personal.xuzj157.stocksyn.pojo.po.FinInfo;
import personal.xuzj157.stocksyn.pojo.po.Symbol;
import personal.xuzj157.stocksyn.repository.BasicInfoRepository;
import personal.xuzj157.stocksyn.repository.FinInfoRepository;
import personal.xuzj157.stocksyn.repository.SymbolRepository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Service
public class XueQiuImpl implements XueQiuService {

    private String urlConInfo = "https://xueqiu.com/stock/f10/compinfo.json";
    private String urlFin = "https://xueqiu.com/stock/f10/finmainindex.json";
    private String cookie = "xq_a_token=dac65245b3a3efae1b7df05a0da1e391a1dc9135; xq_a_token.sig=2jFI2opILtFxo21yBLvZ2SnDNZA; xq_r_token=24a12835d176d574c10d976cfc672b9a9d73eba7; xq_r_token.sig=uCuclRLKQMgHs7hWYxhcbMUHu-s; _ga=GA1.2.1428376186.1509680287; _gid=GA1.2.1493123690.1548047320; Hm_lvt_1db88642e346389874251b5a1eded6e3=1548047320; u=611548047323282; device_id=572a380f09087c7e1fccd6bbc24906e9; Hm_lpvt_1db88642e346389874251b5a1eded6e3=1548047359";

    @Autowired
    RestTemplate restTemplate;
    @Resource
    BasicInfoRepository basicInfoRepository;
    @Resource
    FinInfoRepository finInfoRepository;
    @Resource
    SymbolRepository symbolRepository;

    @Override
    public void getCompInfo() {
        HttpEntity<String> requestEntity = setHeader();
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(4);
        List<Symbol> symbolList = symbolRepository.findAll();
        for (Symbol symbol : symbolList) {
            String symbolStr = symbol.getExchange().toLowerCase() + symbol.getStockCode();
            fixedThreadPool.execute(() -> {
                String url = urlConInfo + "?" + "symbol=" + symbolStr;
                ResponseEntity<JSONObject> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, JSONObject.class);
                JSONObject jsonObject = response.getBody().getJSONObject("tqCompInfo");
                if (jsonObject != null) {
                    BasicInfo basicInfo = JSONObject.parseObject(jsonObject.toJSONString(), BasicInfo.class);
                    JSONObject jsonObjectBoardMap = jsonObject.getJSONArray("tqCompBoardmapList").getJSONObject(0);
                    JSONObject jsonObjectIndustry = jsonObject.getJSONArray("tqCompIndustryList").getJSONObject(0);
                    //解析二级模块
                    basicInfo.setKeyname(jsonObjectBoardMap.getString("keyname"));
                    basicInfo.setKeycode(jsonObjectBoardMap.getString("keycode"));
                    basicInfo.setBoardcode(jsonObjectBoardMap.getString("boardcode"));
                    basicInfo.setBoardname(jsonObjectBoardMap.getString("boardname"));
                    basicInfo.setLevel2code(jsonObjectIndustry.getString("level2code"));
                    basicInfo.setLevel2name(jsonObjectIndustry.getString("level2name"));

                    basicInfo.setCode(symbol.getStockCode());
                    basicInfoRepository.save(basicInfo);
                }
            });
        }
        log.info("xuqiu_getCompInfo:   finish");

    }

    @Override
    public void getFin() {
        HttpEntity<String> requestEntity = setHeader();
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(10);
        List<Symbol> symbolList = symbolRepository.findAll();
        for (Symbol symbol : symbolList) {
            String symbolStr = symbol.getExchange().toLowerCase() + symbol.getStockCode();
            fixedThreadPool.execute(() -> {
                String url = urlFin + "?" + "symbol=" + symbolStr;
                ResponseEntity<JSONObject> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, JSONObject.class);
                JSONArray jsonArray = response.getBody().getJSONArray("list");
                if (jsonArray != null) {
                    List<FinInfo> finInfoList = JSONArray.parseArray(jsonArray.toJSONString(), FinInfo.class);
                    for (FinInfo finInfo : finInfoList) {
                        finInfo.setCode(symbol.getStockCode());
                        finInfoRepository.save(finInfo);
                    }
                }
            });
        }
        log.info("xuqiu_getFin:   finish");
    }

    private HttpEntity<String> setHeader() {
        HttpHeaders requestHeader = new HttpHeaders();
        requestHeader.add("Cookie", cookie);
        HttpEntity<String> requestEntity = new HttpEntity<>(null, requestHeader);
        return requestEntity;
    }

}