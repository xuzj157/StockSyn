package personal.xuzj157.stocksyn.crawler.plugin.impl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import personal.xuzj157.stocksyn.crawler.plugin.DongFangService;
import personal.xuzj157.stocksyn.pojo.po.Symbol;
import personal.xuzj157.stocksyn.repository.SymbolRepository;

import javax.annotation.Resource;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.springframework.util.StringUtils.isEmpty;
import static personal.xuzj157.stocksyn.crawler.util.StringUtils.regexString;

@Service
public class DongFangImpl implements DongFangService {
    private String urlGetAll = "http://quote.eastmoney.com/stocklist.html";

    @Autowired
    RestTemplate restTemplate;

    @Resource
    SymbolRepository symbolRepository;

    @Override
    public void getStockInfo() throws URISyntaxException {
        URI uri = new URI(urlGetAll);
        ResponseEntity<String> htmlStrResponseEntity = restTemplate.getForEntity(uri, String.class);
        Document document = Jsoup.parse(htmlStrResponseEntity.getBody(), "gbk");
//        List<Symbol> symbolList = parseAllStock(document);
        symbolRepository.saveAll(parseAllStock(document));
    }

    /**
     * 解析网页将股票编号取出
     * 
     * @param document
     * @return
     */
    private List<Symbol> parseAllStock(Document document) {
        List<Symbol> symbolList = new CopyOnWriteArrayList<>();
        Elements elements = document.getElementById("quotesearch").getElementsByTag("ul").get(0).getElementsByTag("li");
        for (Element element : elements) {
            String text = element.text();
            String regex = "((6|0|3)0[0-3][0-9][0-9][0-9])";
            String code = regexString(regex, text);
            if (!isEmpty(code)) {
                symbolList.add(new Symbol(code, "SH"));
            }
        }

        elements = document.getElementById("quotesearch").getElementsByTag("ul").get(1).getElementsByTag("li");
        for (Element element : elements) {
            String text = element.text();
            String regex = "((6|0|3)0[0-3][0-9][0-9][0-9])";
            String code = regexString(regex, text);
            if (!isEmpty(code)) {
                symbolList.add(new Symbol(code, "SZ"));
            }
        }

        return symbolList;
    }

}
