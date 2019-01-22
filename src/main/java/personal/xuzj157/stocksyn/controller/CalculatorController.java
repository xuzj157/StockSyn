package personal.xuzj157.stocksyn.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import personal.xuzj157.stocksyn.service.CalculatorService;

import javax.annotation.Resource;

@RestController
@RequestMapping("/calculator")
public class CalculatorController {

    @Resource
    CalculatorService calculatorService;

    @GetMapping("/getRandomUnit")
    public void genRandom() {
        calculatorService.getRandomUnit();
    }

    @GetMapping("/calculatorChart")
    public void calculatorChart(@RequestParam int times) {
        calculatorService.calculatorChart(times);
    }

    @GetMapping("/calculatorStatistics")
    public void calculatorStatistics(@RequestParam int times) {
        calculatorService.calculatorStatistics(times);
    }

    @GetMapping("/calculatorChartStatistics")
    public void calculatorChartStatistics(@RequestParam int times) {
        calculatorService.calculatorChartStatistics(times);
    }

    @GetMapping("/cal")
    public void cal(){
        try {
            calculatorService.cal();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
