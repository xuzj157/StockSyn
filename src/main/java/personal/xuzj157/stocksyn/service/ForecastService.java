package personal.xuzj157.stocksyn.service;

public interface ForecastService {

    /**
     * 图形预测
     * @param name
     * @param code
     * @return
     */
    double chartForecast(String name, String code);

    /**
     * 图形预测统计
     * @param name
     * @param code
     * @return
     */
    Double chartStatisticsForecast(String name, String code);


}
