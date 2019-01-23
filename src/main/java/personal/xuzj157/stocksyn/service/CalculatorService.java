package personal.xuzj157.stocksyn.service;

public interface CalculatorService {
    void getRandomUnit();

    /**
     * 计算图形
     * @param times
     */
    void calculatorChart(int times);

    /**
     * 计算普通统计
     * @param times
     */
    void calculatorStatistics(int times);

    /**
     * 模型统计
     * @param times
     */
    void calculatorChartStatistics(int times);

    void cal(int times) throws Exception;
}
