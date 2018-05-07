package personal.xuzj157.stocksyn.service.impl;

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
        Map<String, Map<Double, Integer>> statisticsMap = CalculationUtils.findMap(name);

        SecondCalculationUnit second = secondCalculationUnitRepository.findByCode(code);
        List<RandomUnit> randomUnitList = CalculationUtils.getRandom(Integer.valueOf(name));

        DecimalFormat df;
        if (Integer.valueOf(name) > 2000000) {
            df = new DecimalFormat("#.##");
        } else {
            df = new DecimalFormat("#.#");
        }

        Map<Double, Integer> map = new HashMap<>();
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
        log.info("finish: " + second.getCode());

        statisticsMap.put(code, map);

        for (Map.Entry<String, Map<Double, Integer>> entry : statisticsMap.entrySet()) {
            statisticsMap.put(entry.getKey(),CalculationUtils.mapSort(entry.getValue(),1));
        }

        LineChartUtils.allInOne(statisticsMap, name + " " + code, "", "", 2048, 950);
        return second.getUpRate();
    }

}
