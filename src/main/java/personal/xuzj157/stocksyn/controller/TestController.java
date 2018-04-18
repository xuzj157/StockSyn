package personal.xuzj157.stocksyn.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import personal.xuzj157.stocksyn.crawler.plugin.DongFangService;
import personal.xuzj157.stocksyn.crawler.plugin.ShouhuService;
import personal.xuzj157.stocksyn.crawler.plugin.XueQiuService;

import javax.annotation.Resource;
import java.net.URISyntaxException;

@RestController
public class TestController {

    @Resource
    DongFangService dongFangService;

    @Resource
    XueQiuService xueQiuService;

    @Resource
    ShouhuService shouhuService;

    @RequestMapping("/dongfang")
    public void GetAllTest() throws URISyntaxException {
        dongFangService.getStockInfo();
    }

    @RequestMapping("/xueqiu")
    public void xueqiuTest(int start, int end, String name) {
        xueQiuService.getCompInfo(start, end, name);
        xueQiuService.getFin(start, end, name);
    }

    @RequestMapping("/shouhu")
    public void shouhuTest(int start, int end){
        shouhuService.getHistory( start,  end);
    }
}
