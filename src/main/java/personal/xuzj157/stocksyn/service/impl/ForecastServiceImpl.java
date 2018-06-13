package personal.xuzj157.stocksyn.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import personal.xuzj157.stocksyn.pojo.bo.RandomUnit;
import personal.xuzj157.stocksyn.pojo.bo.SecondCalculationUnit;
import personal.xuzj157.stocksyn.repository.calculationUnit.SecondCalculationUnitRepository;
import personal.xuzj157.stocksyn.service.ForecastService;
import personal.xuzj157.stocksyn.utils.CalculationUtils;
import personal.xuzj157.stocksyn.utils.chart.LineChartUtils;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class ForecastServiceImpl implements ForecastService {

    @Resource
    private SecondCalculationUnitRepository secondCalculationUnitRepository;

    @Override
    public double chartForecast(String name, String code) {
        Map<String, Map<Double, Integer>> statisticsMap = CalculationUtils.findMap(name + "_", "cal_history");
        SecondCalculationUnit second = secondCalculationUnitRepository.findByCode(code);
        DecimalFormat df;
        if (Integer.valueOf(name) > 2000000) {
            df = new DecimalFormat("#.#");
        } else {
            df = new DecimalFormat("#.#");
        }

        Map<Double, Integer> map = new HashMap<>();
        for (int i = 0; i < 100; i++) {
            List<RandomUnit> randomUnitList = CalculationUtils.getRandom(Integer.valueOf(name));
            for (RandomUnit randomUnit : randomUnitList) {
                double randomSum = CalculationUtils.getSum(randomUnit, second);
                randomSum = Double.parseDouble(df.format(randomSum));
                Integer num = map.get(randomSum);
                if (num == null || num == 0) {
                    num = 1;
                } else {
                    num++;
                }
                map.put(randomSum, num);
            }
            log.info("i:" + i + "    finish: " + second.getCode());
        }

        statisticsMap.put(code, CalculationUtils.mapSort(map, 110));

        for (Map.Entry<String, Map<Double, Integer>> entry : statisticsMap.entrySet()) {
            statisticsMap.put(entry.getKey(), CalculationUtils.mapSort(entry.getValue(), 1));
        }

        LineChartUtils.allInOne(statisticsMap, name + " " + code, "", "", 2048, 950);
        return second.getUpRate();
    }

    @Override
    public Map<String, String> chartStatisticsForecast(String name, String code) {
//        Map<String, Map<Double, Integer>> statisticsMap = CalculationUtils.findMap(name + "_", "cal_statistics_history");
        SecondCalculationUnit second = secondCalculationUnitRepository.findByCode(code);
        DecimalFormat df = new DecimalFormat("#.#");
        Map<Double, Integer> map = new HashMap<>();
        List<RandomUnit> randomUnitList = CalculationUtils.getRandom(Integer.valueOf(name));

        for (RandomUnit randomUnit : randomUnitList) {
            double randomSum = CalculationUtils.getSum(randomUnit, second);
            randomSum = Double.parseDouble(df.format(randomSum));
            Integer num = map.get(randomSum);
            if (num == null || num == 0) {
                num = 1;
            } else {
                num++;
            }
            map.put(randomSum, num);
        }

        map = CalculationUtils.statisticsMapUtil(map, 1);
//        int num = 0;
//        for (Map.Entry<Double, Integer> entry : map.entrySet()) {
//            switch (num) {
//                case 0:
//                    entry.setValue(15000);
//                    break;
//                case 1:
//                    entry.setValue(10);
//                    break;
//                case 2:
//                    entry.setValue(10);
//                    break;
//
//            }
//            num++;
//        }
//        statisticsMap.put("0000", map);
//        for (Map.Entry<String, Map<Double, Integer>> entry : statisticsMap.entrySet()) {
//            statisticsMap.put(entry.getKey(), CalculationUtils.mapSort(entry.getValue(), 1));
//        }
//        LineChartUtils.allInOne(statisticsMap, name + " 统计 " + code, "", "", 2048, 950);
//        log.info("finish");

        String result = "";
        for (Map.Entry<Double, Integer> entry : map.entrySet()) {
            result = entry.getKey().toString();
            break;
        }

        Map<String, String> resMap = new HashMap<>();
        resMap.put("up_rate", second.getUpRate().toString());
        resMap.put("result", result);

        return resMap;
    }

}
