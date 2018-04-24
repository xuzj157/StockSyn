package personal.xuzj157.stocksyn.crawler.plugin.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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

@Service
public class XueQiuImpl implements XueQiuService {

    private String urlConInfo = "https://xueqiu.com/stock/f10/compinfo.json";
    private String urlFin = "https://xueqiu.com/stock/f10/finmainindex.json";
    private String cookie = "device_id=66d11289786ec6edd20b417e44546f16; s=fn112aohsh; bid=44e66306585a5c9788ae9c616162de73_j9rst8fy; __utmz=1.1509973978.9.2.utmcsr=baidu|utmccn=(organic)|utmcmd=organic; aliyungf_tc=AQAAANXwRDgo4AsAokBRZUXoxF2LDc/X; xq_a_token=0d524219cf0dd2d0a4d48f15e36f37ef9ebcbee1; xq_a_token.sig=P0rdE1K6FJmvC2XfH5vucrIHsnw; xq_r_token=7095ce0c820e0a53c304a6ead234a6c6eca38488; xq_r_token.sig=xBQzKLc4EP4eZvezKxqxXNtB7K0; u=881524563971954; __utma=1.1428376186.1509680287.1523931447.1524563972.14; __utmc=1; __utmt=1; Hm_lvt_1db88642e346389874251b5a1eded6e3=1523931448,1524563973; Hm_lpvt_1db88642e346389874251b5a1eded6e3=1524564126; __utmb=1.7.10.1524563972";

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
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(20);
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
    }

    @Override
    public void getFin() {
        HttpEntity<String> requestEntity = setHeader();
        List<FinInfo> finInfoListResult = new ArrayList<>();
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(20);
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
                        finInfoListResult.add(finInfo);
                    }
                    finInfoRepository.saveAll(finInfoListResult);
                }
            });
        }
    }

    private HttpEntity<String> setHeader() {
        HttpHeaders requestHeader = new HttpHeaders();
        requestHeader.add("Cookie", cookie);
        HttpEntity<String> requestEntity = new HttpEntity<>(null, requestHeader);
        return requestEntity;
    }

}