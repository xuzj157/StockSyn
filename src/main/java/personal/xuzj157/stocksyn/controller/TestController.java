package personal.xuzj157.stocksyn.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import personal.xuzj157.stocksyn.crawler.plugin.BaiduService;
import personal.xuzj157.stocksyn.crawler.plugin.DongFangService;
import personal.xuzj157.stocksyn.crawler.plugin.ShouhuService;
import personal.xuzj157.stocksyn.crawler.plugin.XueQiuService;

import javax.annotation.Resource;
import java.net.URISyntaxException;

@Slf4j
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

    @RequestMapping("/xueqiuFin")
    public void xueqiuFin() {
        xueQiuService.getFin();
    }

    @RequestMapping("/xueqiuCompInfo")
    public void xueqiuCompInfo() {
        xueQiuService.getCompInfo();
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

    @GetMapping("/crawler/all")
    public void getAll() throws URISyntaxException {
        dongFangService.getStockInfo();
        log.info("dongfang getStockInfo() finish!!!");
        xueQiuService.getCompInfo();
        log.info("xueqiu getCompInfo() finish!!!");
        xueQiuService.getFin();
        log.info("xueqiu getFin() finish!!!");
        baiduService.getSnapShotFromAndroid();
        log.info("baidu getSnapShotFromAndroid() finish!!!");
        shouhuService.getHistory();
        log.info("shouhu getHistory() finish!!!");
    }
}
