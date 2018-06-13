package personal.xuzj157.stocksyn.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import personal.xuzj157.stocksyn.pojo.vo.JsonResponse;
import personal.xuzj157.stocksyn.service.ForecastService;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/forecast")
public class ForecastController {

    @Resource
    private ForecastService forecastService;

    @RequestMapping(value = "/chartForecast/{name}/{code}", method = RequestMethod.GET)
    public JsonResponse<Double> chartForecast(@PathVariable String name, @PathVariable String code) {

        return new JsonResponse<>(forecastService.chartForecast(name, code));
    }

    @RequestMapping(value = "/chartStatisticsForecast/{name}/{code}", method = RequestMethod.GET)
    public JsonResponse chartStatisticsForecast(@PathVariable String name, @PathVariable String code) {

        return new JsonResponse<>(forecastService.chartStatisticsForecast(name, code));
    }

}
