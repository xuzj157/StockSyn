package personal.xuzj157.stocksyn.service.impl;

import org.hibernate.sql.Update;
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
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
    public void calculator() {
        ExecutorService executorService = Executors.newFixedThreadPool(4);

        List<RandomUnit> randomUnitList = new LinkedList<>();
        Map<String, Map<Double, Integer>> statisticsMap = new HashMap<>();
        Integer upNum = 0;
        Integer downNum = 0;
        DecimalFormat df = new DecimalFormat("#.##");

        for (int i = 0; i < 10000000; i++) {
            RandomUnit randomUnit = new RandomUnit();
            randomUnitList.add(randomUnit);
            System.out.println(i);
        }
        List<SecondCalculationUnit> secondList = secondCalculationUnitRepository.findAll();
        Map<Double, Integer> upMap = new HashMap<>();
        Map<Double, Integer> downMap = new HashMap<>();

        for (SecondCalculationUnit second : secondList) {
            double uprate = second.getUpRate();
            if (uprate > 0) {
                upNum++;
            }else {
                downNum++;
            }
            for (RandomUnit randomUnit : randomUnitList) {
                executorService.execute(() -> {
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
                });
            }
            System.out.println("finish: " + second.getCode());
        }
        System.out.println("basic finish!!!");
        statisticsMap.put("up", CalculationUtils.getMap(upMap, 33));
        statisticsMap.put("down", CalculationUtils.getMap(downMap, 36));
        System.out.println("statisticsMap finish!!!");
        LineChartUtils.allInOne(statisticsMap, "标题", "价格", "数量", 2048, 1024);
        System.out.println("all finish!!!");

    }
}
