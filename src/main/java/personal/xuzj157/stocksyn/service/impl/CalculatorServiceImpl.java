package personal.xuzj157.stocksyn.service.impl;

import org.springframework.stereotype.Service;
import personal.xuzj157.stocksyn.pojo.bo.RandomUnit;
import personal.xuzj157.stocksyn.pojo.bo.SecondCalculationUnit;
import personal.xuzj157.stocksyn.repository.calculationUnit.RandomUnitRepository;
import personal.xuzj157.stocksyn.repository.calculationUnit.SecondCalculationUnitRepository;
import personal.xuzj157.stocksyn.service.CalculatorService;
import personal.xuzj157.stocksyn.utils.CalculationUtils;

import javax.annotation.Resource;
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
    public void calculator() throws InterruptedException {
        List<RandomUnit> randomUnitList = new LinkedList<>();
        ExecutorService executorService = Executors.newFixedThreadPool(6);
        List a = new LinkedList();
        System.out.println(Integer.MAX_VALUE);
        for (int i = 0; i < 3000; i++) {
            executorService.execute(() -> {
                RandomUnit randomUnit = new RandomUnit();
                randomUnitList.add(randomUnit);
            });
        }

        List<SecondCalculationUnit> secondList = secondCalculationUnitRepository.findAll();
//        Thread.sleep(10 * 1000);
        for (SecondCalculationUnit second : secondList) {
            double uprate = second.getUpRate();
            for (RandomUnit randomUnit : randomUnitList) {
                double randomSum = CalculationUtils.getSum(randomUnit, second);
                System.out.println("uprate: " + uprate + ", randomSum: " + randomSum);
            }
        }
    }
}
