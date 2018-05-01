package personal.xuzj157.stocksyn.service.impl;

import org.springframework.stereotype.Service;
import personal.xuzj157.stocksyn.pojo.bo.RandomUnit;
import personal.xuzj157.stocksyn.repository.calculationUnit.RandomUnitRepository;
import personal.xuzj157.stocksyn.service.CalculatorService;

import javax.annotation.Resource;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class CalculatorServiceImpl implements CalculatorService {

    @Resource
    RandomUnitRepository randomUnitRepository;

    @Override
    public void getRandomUnit() {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 3000000; i++) {
            executorService.execute(() -> {
                RandomUnit randomUnit = new RandomUnit();
//                randomUnitRepository.save(randomUnit);
            });
        }
        System.out.println("finish");
    }
}
