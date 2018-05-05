package personal.xuzj157.stocksyn.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import personal.xuzj157.stocksyn.pojo.bo.RandomUnit;
import personal.xuzj157.stocksyn.pojo.bo.SecondCalculationUnit;
import personal.xuzj157.stocksyn.repository.calculationUnit.RandomUnitRepository;
import personal.xuzj157.stocksyn.repository.calculationUnit.SecondCalculationUnitRepository;
import personal.xuzj157.stocksyn.service.CalculatorService;
import personal.xuzj157.stocksyn.utils.CalculationUtils;
import personal.xuzj157.stocksyn.utils.chart.LineChartUtils;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Service
public class CalculatorServiceImpl implements CalculatorService {

    @Resource
    RandomUnitRepository randomUnitRepository;
    @Resource
    SecondCalculationUnitRepository secondCalculationUnitRepository;

    @Override
    public void getRandomUnit() {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 3000000; i++) {
            executorService.execute(() -> {
                RandomUnit randomUnit = new RandomUnit();
                randomUnitRepository.save(randomUnit);
            });
        }
    }

    @Override
    public void calculatorChart(int times) {
//        ExecutorService executorService = Executors.newFixedThreadPool(2);
        List<RandomUnit> randomUnitList;
        Map<String, Map<Double, Integer>> statisticsMap = new HashMap<>();
        Integer upNum = 0;
        Integer downNum = 0;
        DecimalFormat df = new DecimalFormat("#.##");
        //初始化随机数单元
        randomUnitList = CalculationUtils.getRandom(times);
        List<SecondCalculationUnit> secondList = secondCalculationUnitRepository.findAll();
        Map<Double, Integer> upMap = new HashMap<>();
        Map<Double, Integer> downMap = new HashMap<>();
        for (SecondCalculationUnit second : secondList) {
            double uprate = second.getUpRate();
            if (uprate > 0) {
                upNum++;
            } else {
                downNum++;
            }
            for (RandomUnit randomUnit : randomUnitList) {
//                executorService.execute(() -> {
                double randomSum = CalculationUtils.getSum(randomUnit, second);
                randomSum = Double.parseDouble(df.format(randomSum));
                if (uprate > 0) {
                    Integer num = upMap.get(randomSum);
                    if (num == null || num == 0) {
                        num = 1;
                    } else {
                        num++;
                    }
                    upMap.put(randomSum, num);
                } else {
                    Integer num = downMap.get(randomSum);
                    if (num == null || num == 0) {
                        num = 1;
                    } else {
                        num++;
                    }
                    downMap.put(randomSum, num);
                }
//                });
            }
            log.info("finish: " + second.getCode());
        }
        log.info("basic finish!!!");
        statisticsMap.put(times + "up", CalculationUtils.getMap(upMap, upNum));
        statisticsMap.put(times + "down", CalculationUtils.getMap(downMap, downNum));
        //存储以备下次使用
        CalculationUtils.saveMap(statisticsMap);
        log.info("statisticsMap finish!!!");
        LineChartUtils.allInOne(statisticsMap, times + "次拟合 股票预测", "价格", "数量", 2048, 950);
        log.info("all finish!!!");

    }
}
