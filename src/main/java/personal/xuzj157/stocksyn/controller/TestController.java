package personal.xuzj157.stocksyn.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import personal.xuzj157.stocksyn.crawler.plugin.BaiduService;
import personal.xuzj157.stocksyn.crawler.plugin.DongFangService;
import personal.xuzj157.stocksyn.crawler.plugin.ShouhuService;
import personal.xuzj157.stocksyn.crawler.plugin.XueQiuService;

import javax.annotation.Resource;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/test")
public class TestController {

    @Resource
    DongFangService dongFangService;

    @Resource
    XueQiuService xueQiuService;

    @Resource
    ShouhuService shouhuService;

    @Resource
    BaiduService baiduService;

    @RequestMapping("/dongfang")
    public void GetAllTest() throws URISyntaxException {
        dongFangService.getStockInfo();
    }

    @RequestMapping("/xueqiu")
    public void xueqiuTest(int start, int end, String exchange) {
        xueQiuService.getCompInfo(start, end, exchange);
        xueQiuService.getFin(start, end, exchange);
    }

    @RequestMapping("/shouhu")
    public void shouhuTest() {
        shouhuService.getHistory();
    }

    @GetMapping("/baidu/params")
    public void baiduTest(int start, int end, String exchange) {
        baiduService.getSnapShotFromAndroid(start, end, exchange);
    }

    @GetMapping("/baidu/null")
    public void baiduTest() {
        baiduService.getSnapShotFromAndroid();
    }
}
