package personal.xuzj157.stocksyn.crawler.plugin.impl;

import com.alibaba.fastjson.JSONArray;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import personal.xuzj157.stocksyn.crawler.plugin.ShouhuService;
import personal.xuzj157.stocksyn.db.MongoDB;
import personal.xuzj157.stocksyn.pojo.po.HqInfo;
import personal.xuzj157.stocksyn.pojo.po.StockInfo;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class ShouhuImpl implements ShouhuService {

    private String urlHistory = "http://q.stock.sohu.com/hisHq?code=cn_%s&start=19960101&end=20180330&stat=1&order=D&period=d&callback=historySearchHandler&rt=jsonp";

    @Resource
    RestTemplate restTemplate;

    @Override
    public void getHistory(int start, int end) {

        while (start <= end){
            String symbolStr = String.format("%06d",start);

            String resultStr = restTemplate.getForObject(String.format(urlHistory,symbolStr), String.class);
            resultStr = resultStr.replaceAll("historySearchHandler\\(", "");
            resultStr = resultStr.replaceAll("\\)", "");
            JSONArray jsonArray = JSONArray.parseArray(resultStr);
            int status = jsonArray.getJSONObject(0).getInteger("status");
            if (status == 0){
                System.out.println(symbolStr);
                List<HqInfo> hqInfoList = new ArrayList<>();
                StockInfo stockInfo = new StockInfo();
                stockInfo.setCode(symbolStr);

                JSONArray hqArray = jsonArray.getJSONObject(0).getJSONArray("hq");
                for (int i = 0; i < hqArray.size(); i++) {
                    HqInfo hqInfo = new HqInfo();
                    hqInfo.setData(hqArray.getJSONArray(i).getString(0).replaceAll("-",""));
                    hqInfo.setOpen(Double.valueOf(hqArray.getJSONArray(i).getString(1)));
                    hqInfo.setClose(Double.valueOf(hqArray.getJSONArray(i).getString(2)));
                    hqInfo.setFloatW(Double.valueOf(hqArray.getJSONArray(i).getString(3)));
                    hqInfo.setFloatPre(Double.valueOf(hqArray.getJSONArray(i).getString(4).replaceAll("%","")));
                    hqInfo.setLowPrice(Double.valueOf(hqArray.getJSONArray(i).getString(5)));
                    hqInfo.setHighPrice(Double.valueOf(hqArray.getJSONArray(i).getString(6)));
                    hqInfo.setVol(Double.valueOf(hqArray.getJSONArray(i).getString(7))/100);
                    hqInfo.setVolPrice(Double.valueOf(hqArray.getJSONArray(i).getString(8)));
                    hqInfo.setExchangeRate(Double.valueOf(hqArray.getJSONArray(i).getString(9).replaceAll("%","")));
                    hqInfoList.add(hqInfo);
                }
                stockInfo.setHqInfoList(hqInfoList);
                MongoDB.writeResultObjectToDB("hq_info",stockInfo);
            }

            start++;
        }



    }
}
