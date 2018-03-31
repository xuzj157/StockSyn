package personal.xuzj157.stocksyn.crawler.plugin.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mongodb.BasicDBObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import personal.xuzj157.stocksyn.crawler.plugin.XueQiuService;
import personal.xuzj157.stocksyn.db.MongoDB;
import personal.xuzj157.stocksyn.pojo.po.BasicInfo;
import personal.xuzj157.stocksyn.pojo.po.FinInfo;
import personal.xuzj157.stocksyn.pojo.po.StockInfo;
import personal.xuzj157.stocksyn.utils.SymbolUtils;

import javax.mail.StoreClosedException;
import java.util.List;

@Service
public class XueQiuImpl implements XueQiuService {

    private String urlConInfo = "https://xueqiu.com/stock/f10/compinfo.json";
    private String urlFin = "https://xueqiu.com/stock/f10/finmainindex.json";
    private String cookie = "device_id=041a0db67913d4c6605ddb87a965b825; __utmz=1.1521944443.1.1.utmcsr=google|utmccn=(organic)|utmcmd=organic|utmctr=(not%20provided); s=f011oda2d4; aliyungf_tc=AQAAAGCu4X1hOwEAHnpQZVmAVNh8mplb; xq_a_token=229a3a53d49b5d0078125899e528279b0e54b5fe; xq_a_token.sig=oI-FfEMvVYbAuj7Ho7Z9mPjGjjI; xq_r_token=8a43eb9046efe1c0a8437476082dc9aac6db2626; xq_r_token.sig=Efl_JMfn071_BmxcpNvmjMmUP40; u=951522418885862; __utmc=1; Hm_lvt_1db88642e346389874251b5a1eded6e3=1521944442,1521944461,1521987090,1522418886; __utma=1.33644132.1521944443.1522418886.1522421728.6; __utmt=1; __utmb=1.2.10.1522421728; Hm_lpvt_1db88642e346389874251b5a1eded6e3=1522421756";
    @Autowired
    RestTemplate restTemplate;

    @Override
    public void getCompInfo(int start, int end, String name) {
        HttpEntity<String> requestEntity = setHeader();
        int symbol = start;
        while (symbol <= end) {
            String symbolStr = String.format("%06d",symbol);
            String url = urlConInfo + "?" + "symbol=" + name + symbolStr;
            ResponseEntity<JSONObject> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, JSONObject.class);
            JSONObject jsonObject = response.getBody().getJSONObject("tqCompInfo");
            if (jsonObject != null) {
                System.out.println(symbolStr);
                BasicInfo basicInfo = JSONObject.parseObject(jsonObject.toJSONString(), BasicInfo.class);
                StockInfo stockInfo = new StockInfo();
                stockInfo.setCode(symbolStr);
                stockInfo.setName(name);
                stockInfo.setBasicInfo(basicInfo);
                MongoDB.writeResultObjectToDB("basic_info", stockInfo);
            }
            symbol++;
        }
    }

    @Override
    public void getFin(int start, int end, String name) {
        HttpEntity<String> requestEntity = setHeader();

        int symbol = start;
        while (symbol <= end) {
            String symbolStr = String.format("%06d",symbol);
            String url = urlFin + "?" + "symbol=" + name + symbolStr;

            ResponseEntity<JSONObject> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, JSONObject.class);
            JSONArray jsonArray = response.getBody().getJSONArray("list");
            if (jsonArray != null) {
                System.out.println(symbolStr);
                List<FinInfo> finInfoList = JSONArray.parseArray(jsonArray.toJSONString(), FinInfo.class);
                StockInfo stockInfo = new StockInfo();
                stockInfo.setCode(symbolStr);
                stockInfo.setName(name);
                stockInfo.setFinInfoList(finInfoList);
                MongoDB.writeResultObjectToDB("fin_info", stockInfo);
            }
            symbol++;
        }
    }

    private HttpEntity<String> setHeader() {
        //TODO 从mongo数据库中读取
        HttpHeaders requestHeader = new HttpHeaders();
        requestHeader.add("Cookie", cookie);
        HttpEntity<String> requestEntity = new HttpEntity<>(null, requestHeader);
        return requestEntity;
    }

}