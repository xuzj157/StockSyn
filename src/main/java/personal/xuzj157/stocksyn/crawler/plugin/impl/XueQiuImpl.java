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
    private String cookie = "device_id=041a0db67913d4c6605ddb87a965b825; s=f011oda2d4; xq_a_token=019174f18bf425d22c8e965e48243d9fcfbd2cc0; xq_a_token.sig=_pB0kKy3fV9fvtvkOzxduQTrp7E; xq_r_token=2d465aa5d312fbe8d88b4e7de81e1e915de7989a; xq_r_token.sig=lOCElS5ycgbih9P-Ny3cohQ-FSA; u=541528599373607; Hm_lvt_1db88642e346389874251b5a1eded6e3=1528599374; _ga=GA1.2.1745805919.1528599374; _gid=GA1.2.1376658282.1528599374; _gat_gtag_UA_16079156_4=1; aliyungf_tc=AQAAAKKCFEudvgQA6ReftKmuuxmwnO60; __utma=1.33644132.1521944443.1525164850.1528599382.18; __utmc=1; __utmz=1.1528599382.18.4.utmcsr=xueqiu.com|utmccn=(referral)|utmcmd=referral|utmcct=/; __utmt=1; Hm_lpvt_1db88642e346389874251b5a1eded6e3=1528599387; __utmb=1.2.10.1528599382";

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