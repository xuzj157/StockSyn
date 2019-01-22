package personal.xuzj157.stocksyn.service.impl;

import com.mongodb.client.model.geojson.CoordinateReferenceSystem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import personal.xuzj157.stocksyn.pojo.bo.*;
import personal.xuzj157.stocksyn.pojo.vo.Coordinate;
import personal.xuzj157.stocksyn.repository.calculationUnit.RandomUnitRepository;
import personal.xuzj157.stocksyn.repository.calculationUnit.SecondCalculationUnitRepository;
import personal.xuzj157.stocksyn.service.CalculatorService;
import personal.xuzj157.stocksyn.utils.CalculationUtils;
import personal.xuzj157.stocksyn.utils.MongoDB;
import personal.xuzj157.stocksyn.utils.chart.LineChartUtils;
import personal.xuzj157.stocksyn.utils.chart.ScatterPlotUtils;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

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
        Map<String, Map<Double, Integer>> finalMap = new HashMap<>();
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
                Integer num = statisticsMap.get(randomSum);
                if (num == null || num == 0) {
                    num = 1;
                } else {
                    num++;
                }
                statisticsMap.put(randomSum, num);
            }
            //取出前五个
            statisticsMap = CalculationUtils.statisticsMapUtil(statisticsMap, 1);
            //分别赋值
            int num = 0;
            for (Map.Entry<Double, Integer> entry : statisticsMap.entrySet()) {
                switch (num) {
                    case 0:
                        entry.setValue(30000);
                        break;
                    case 1:
                        entry.setValue(50);
                        break;
                    case 2:
                        entry.setValue(50);
                        break;
                    case 3:
                        entry.setValue(30);
                        break;
                    case 4:
                        entry.setValue(30);
                        break;
                    case 5:
                        entry.setValue(15);
                        break;
                    case 6:
                        entry.setValue(15);
                        break;
                    case 7:
                        entry.setValue(5);
                        break;
                    case 8:
                        entry.setValue(5);
                        break;
                    default:
                        break;
                }
                statisticsMap.put(entry.getKey(), entry.getValue());
                num++;
            }

            for (Map.Entry<Double, Integer> entry : statisticsMap.entrySet()) {
                if (uprate > 0) {
                    Integer value = upMap.get(entry.getKey());
                    if (value == null || value == 0) {
                        value = entry.getValue();
                    } else {
                        value = value + entry.getValue();
                    }
                    upMap.put(entry.getKey(), value);
                    upNum++;
                } else {
                    Integer value = downMap.get(entry.getKey());
                    if (value == null || value == 0) {
                        value = entry.getValue();
                    } else {
                        value = value + entry.getValue();
                    }
                    downMap.put(entry.getKey(), value);
                    downNum++;
                }
            }
            log.info("finish:   " + second.getCode());
        }
        log.info("basic finish!!!");
        finalMap.put(times + "_up", CalculationUtils.mapSort(upMap, upNum / 10));
        finalMap.put(times + "_down", CalculationUtils.mapSort(downMap, downNum / 10));
        //存储以备下次使用
        CalculationUtils.saveMap(finalMap, "cal_statistics_history");
        log.info("statisticsMap finish!!!");
        LineChartUtils.allInOne(finalMap, times + "次统计型拟合" + format + " 股票预测", "价格", "数量", 2048, 950);
        log.info("all finish!!!");
    }


    @Override
    public void calculatorChart(int times) {
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(16);

        Map<String, Map<Double, Integer>> statisticsMap = new HashMap<>();
        AtomicReference<Integer> upNum = new AtomicReference<>(0);
        AtomicReference<Integer> downNum = new AtomicReference<>(0);
        String format = "#.#";
        DecimalFormat df = new DecimalFormat(format);
        //初始化随机数单元
        List<RandomUnit> randomUnitList = CalculationUtils.getRandom(times);
        List<SecondCalculationUnit> secondList = secondCalculationUnitRepository.findAll();
        Map<Double, Integer> upMap = new HashMap<>();
        Map<Double, Integer> downMap = new HashMap<>();
        for (SecondCalculationUnit second : secondList) {

            fixedThreadPool.execute(() -> {
                double uprate = second.getUpRate();
                if (uprate > 0) {
                    upNum.getAndSet(upNum.get() + 1);
                } else {
                    downNum.getAndSet(downNum.get() + 1);
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
            });
        }

        while (!fixedThreadPool.isTerminated()) {
            try {
                new Thread().sleep(100000l);
                System.out.println("未执行完成");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        log.info("basic finish!!!");
        statisticsMap.put(times + "_up", CalculationUtils.mapSort(upMap, upNum.get()));
        statisticsMap.put(times + "_down", CalculationUtils.mapSort(downMap, downNum.get()));
        //存储以备下次使用
        CalculationUtils.saveMap(statisticsMap, "cal_history");
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
                    if ((rateN1 > 0.6 && rateN4 > 0.6) || (rateN2 > 0.6 && rateN3 > 0.6)) {
                        sumUnitListRes.add(sumUnit);
                    }
                }

                if (sumUnitListRes.size() > 0) {
                    MongoDB.writeResultObjectToDB("calculation_res", new CalculationResUnit(random, sumUnitListRes));
                    t2++;
                }
            }
            log.info(j + "       " + t2);

        }
    }

    @Override
    public void cal() throws Exception {
        List<SecondCalculationUnit> secondList = secondCalculationUnitRepository.findAll();
        List<Coordinate> coordinateUp = new ArrayList<>();
        List<Coordinate> coordinateDown = new ArrayList<>();
        for (SecondCalculationUnit secondCalculationUnit : secondList) {
            Coordinate coordinate = new Coordinate();
            coordinate.setXAxis(Math.abs(secondCalculationUnit.getUpRate()));
            coordinate.setYAxis(secondCalculationUnit.getLowHistoryPrice());
            if (secondCalculationUnit.getUpRate() < 0) {
                coordinateUp.add(coordinate);
            } else {
                coordinateDown.add(coordinate);
            }
        }
        Map<String, List<Coordinate>> map = new HashMap<>();
        map.put("up", coordinateUp);
        map.put("down", coordinateDown);
        ScatterPlotUtils.allInOne(map,"title","x","y",1000,800);
    }

}
