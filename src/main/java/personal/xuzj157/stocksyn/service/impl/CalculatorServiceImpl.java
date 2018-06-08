package personal.xuzj157.stocksyn.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import personal.xuzj157.stocksyn.pojo.bo.*;
import personal.xuzj157.stocksyn.repository.calculationUnit.RandomUnitRepository;
import personal.xuzj157.stocksyn.repository.calculationUnit.SecondCalculationUnitRepository;
import personal.xuzj157.stocksyn.service.CalculatorService;
import personal.xuzj157.stocksyn.utils.CalculationUtils;
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
    public void calculatorChartStatistics(int times) {
        Integer upNum = 0, downNum = 0;
        String format = "#.#";
        DecimalFormat df = new DecimalFormat(format);
        List<RandomUnit> randomUnitList = CalculationUtils.getRandom(times);
        List<SecondCalculationUnit> secondList = secondCalculationUnitRepository.findAll();
        Map<Double, Integer> upMap = new HashMap<>();
        Map<Double, Integer> downMap = new HashMap<>();

        for (SecondCalculationUnit second : secondList) {
            double uprate = second.getUpRate();
            Map<Double, Integer> statisticsMap = new HashMap<>();

            for (RandomUnit randomUnit : randomUnitList) {
                double randomSum = CalculationUtils.getSum(randomUnit, second);
                randomSum = Double.parseDouble(df.format(randomSum));
                Integer num = upMap.get(randomSum);
                if (num == null || num == 0) {
                    num = 1;
                } else {
                    num++;
                }
                statisticsMap.put(randomSum, num);
            }

            statisticsMap = CalculationUtils.statisticsMapUtil(statisticsMap, 5);

            if (uprate > 0) {
                upNum++;

            } else {
                downNum++;
            }

        }

    }


    @Override
    public void calculatorChart(int times) {
        Map<String, Map<Double, Integer>> statisticsMap = new HashMap<>();
        Integer upNum = 0, downNum = 0;
        String format = "#.#";
        DecimalFormat df = new DecimalFormat(format);
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
        statisticsMap.put(times + "_up", CalculationUtils.mapSort(upMap, upNum));
        statisticsMap.put(times + "_down", CalculationUtils.mapSort(downMap, downNum));
        //存储以备下次使用
        CalculationUtils.saveMap(statisticsMap);
        log.info("statisticsMap finish!!!");
        LineChartUtils.allInOne(statisticsMap, times + "次拟合" + format + " 股票预测", "价格", "数量", 2048, 950);
        log.info("all finish!!!");
    }

    @Override
    public void calculatorStatistics(int timesOri) {
        List<SecondCalculationUnit> secondList = secondCalculationUnitRepository.findAll();
        Integer up = 0, down = 0;
        for (SecondCalculationUnit secondCalculationUnit : secondList) {
            if (secondCalculationUnit.getUpRate() > 0) {
                up++;
            } else {
                down++;
            }
        }

        int t = 0, t2 = 0;

        for (int j = 0; j < 10000; j++) {
            List<RandomUnitS> randomUnitList = CalculationUtils.getRandomS(timesOri);
            for (RandomUnitS random : randomUnitList) {
                List<SumUnit> sumUnitSet = CalculationUtils.getLimitList();
                for (SecondCalculationUnit second : secondList) {
                    Double sum = CalculationUtils.getSum(random, second);
                    for (int i = 0; i < sumUnitSet.size(); i++) {
                        SumUnit sumUnit = sumUnitSet.get(i);
                        //二分类
                        if (second.getUpRate() >= 0) {
                            if (sum < sumUnit.getLimit()) {
                                sumUnit.setN1(sumUnit.getN1() + 1);
                            } else {
                                sumUnit.setN3(sumUnit.getN3() + 1);
                            }
                        } else {
                            if (sum < sumUnit.getLimit()) {
                                sumUnit.setN2(sumUnit.getN2() + 1);
                            } else {
                                sumUnit.setN4(sumUnit.getN4() + 1);
                            }
                        }
                        sumUnitSet.set(i, sumUnit);
                    }
                }
//            Collections.sort(sumUnitSet);
//            sumUnitSet = sumUnitSet.subList(0, 10);
                List<SumUnit> sumUnitListRes = new LinkedList<>();
                for (SumUnit sumUnit : sumUnitSet) {
                    Double rateN1 = (double) sumUnit.getN1() / (double) up;
                    Double rateN2 = (double) sumUnit.getN2() / (double) down;
                    Double rateN3 = (double) sumUnit.getN3() / (double) up;
                    Double rateN4 = (double) sumUnit.getN4() / (double) down;
                    if ((rateN1 > 0.7 && rateN4 > 0.7) || (rateN2 > 0.7 && rateN3 > 0.7)) {
                        sumUnitListRes.add(sumUnit);
                    }
                }

                if (sumUnitListRes.size() > 0) {
                    MongoDB.writeResultObjectToDB("calculation_res", new CalculationResUnit(random, sumUnitListRes));
                    t2++;
                }
            }
            System.out.println(j + "       " + t2);

        }
    }


}
