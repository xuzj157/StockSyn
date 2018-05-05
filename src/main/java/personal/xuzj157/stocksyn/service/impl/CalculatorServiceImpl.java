package personal.xuzj157.stocksyn.service.impl;

import com.sun.javafx.scene.control.skin.VirtualFlow;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import personal.xuzj157.stocksyn.pojo.bo.RandomUnit;
import personal.xuzj157.stocksyn.pojo.bo.SecondCalculationUnit;
import personal.xuzj157.stocksyn.pojo.bo.SumUnit;
import personal.xuzj157.stocksyn.repository.calculationUnit.RandomUnitRepository;
import personal.xuzj157.stocksyn.repository.calculationUnit.SecondCalculationUnitRepository;
import personal.xuzj157.stocksyn.service.CalculatorService;
import personal.xuzj157.stocksyn.utils.CalculationUtils;
import personal.xuzj157.stocksyn.utils.MathUtils;
import personal.xuzj157.stocksyn.utils.MongoDB;
import personal.xuzj157.stocksyn.utils.chart.LineChartUtils;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.util.*;
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
        Map<String, Map<Double, Integer>> statisticsMap = new HashMap<>();
        Integer upNum = 0, downNum = 0;
        DecimalFormat df = new DecimalFormat("#.##");
        //初始化随机数单元
        List<RandomUnit> randomUnitList = CalculationUtils.getRandom(times);
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

    @Override
    public void calculatorStatistics(int times) {
        times = times / 100000;
        List<SecondCalculationUnit> secondList = secondCalculationUnitRepository.findAll();

        for (int i = 0; i < 100000; i++) {
            List<SumUnit> sumUnitList = CalculationUtils.getSumUnit(times);
            for (SecondCalculationUnit second : secondList) {
                Double upRate = second.getUpRate();
                List<SumUnit> newSumUnitList = new ArrayList<>();
                for (SumUnit sumUnit : sumUnitList) {
                    double randomSum = CalculationUtils.getSum(sumUnit.getRandomUnit(), second);
                    double n = MathUtils.linearFunction(upRate, randomSum);
                    newSumUnitList.add(new SumUnit(n, 1, null));
                }
//                log.info("calculation finish:  " + second.getCode());
                CalculationUtils.getSumList(newSumUnitList, sumUnitList);
//                log.info("getSumList finish:  " + second.getCode());
            }
            MongoDB.writeResultListToDB("cal", sumUnitList);
            log.info("calculation finish:  " + i);
        }
    }


}
