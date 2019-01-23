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
    private String cookie = "_ga=GA1.2.451785216.1547954444; device_id=51d86b0feda66bfe3f23b321ecec2be7; __utma=1.451785216.1547954444.1547954458.1547954458.1; __utmz=1.1547954458.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); aliyungf_tc=AQAAABCsHQ0lNgsAAapH3h7l43J7tO6A; xq_a_token=8dd2cc84915c45983930bb32e788dc93e0fcfddd; xq_a_token.sig=rjG2G1sq6nNdwvwGHxpwqDYbk3s; xq_r_token=5bb4c968b369150a382906ceba61eb8763282a13; xq_r_token.sig=eoelFajTh7zpqBNrEdBVD9rYjbw; u=791548256674657; _gid=GA1.2.357316646.1548256675; _gat_gtag_UA_16079156_4=1; Hm_lvt_1db88642e346389874251b5a1eded6e3=1547954446,1548256675; Hm_lpvt_1db88642e346389874251b5a1eded6e3=1548256675";

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