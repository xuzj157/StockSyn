package personal.xuzj157.stocksyn.utils;

import personal.xuzj157.stocksyn.pojo.po.FinInfo;
import personal.xuzj157.stocksyn.pojo.po.HqInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FirstCalculationUnitUtils {

    /**
     * 获取60日涨幅
     *
     * @return
     */
    public static Double getUpDate(List<HqInfo> hqInfoList) {
        double lastThirty = 0;
        double firstThirty = 0;
        for (int i = hqInfoList.size() - 30; i < hqInfoList.size(); i++) {
            lastThirty = lastThirty + SymbolUtils.getAvgOneDay(hqInfoList.get(i));
        }
        for (int i = hqInfoList.size() - 60; i < hqInfoList.size() - 30; i++) {
            firstThirty = firstThirty + SymbolUtils.getAvgOneDay(hqInfoList.get(i));
        }
        double a = (1.0 / 4.0) * (firstThirty / 30.0) + (3.0 / 4.0) * (lastThirty / 30.0);
        double b = getSomedayPrice(hqInfoList, 60);
        return (a - b) / b * 100.0;
    }

    /**
     * 获取某一天的股票均价
     *
     * @param hqInfoList
     * @param site       倒数第几个
     * @return
     */
    public static double getSomedayPrice(List<HqInfo> hqInfoList, int site) {
        return SymbolUtils.getAvgOneDay(hqInfoList.get(hqInfoList.size() - site));
    }

    /**
     * 获取一段时间的均价
     */
    public static double getAvgPeriodPrice(List<HqInfo> hqInfoList, int start, int end) {
        double allSum = 0;
        for (int i = hqInfoList.size() - start; i < hqInfoList.size() - end; i++) {
            allSum = allSum + SymbolUtils.getAvgOneDay(hqInfoList.get(i));
        }
        return allSum / (start - end);
    }

    /**
     * 获取一段时间的最低价
     */
    public static double getLowPeriodPrice(List<HqInfo> hqInfoList, int start, int end) {
        List<Double> list = new ArrayList<>();
        for (int i = hqInfoList.size() - start; i < hqInfoList.size() - end; i++) {
            list.add(SymbolUtils.getAvgOneDay(hqInfoList.get(i)));
        }
        return Collections.min(list);
    }

    /**
     * 获取历史主营业务收入平均值
     */
    public static double getMainbusiincomeAvg(List<FinInfo> finInfos) {
        double allSum = 0;
        for (FinInfo finInfo : finInfos) {
            allSum = allSum + finInfo.getMainbusiincome();
        }
        return allSum / finInfos.size();
    }

    /**
     * 获取历史净利润平均值
     */
    public static double getNetprofitAvg(List<FinInfo> finInfos) {
        double allSum = 0;
        for (FinInfo finInfo : finInfos) {
            allSum = allSum + finInfo.getNetprofit();
        }
        return allSum / finInfos.size();
    }

    /**
     * 获取历史资产总额平均值
     *
     * @param finInfos
     * @return
     */
    public static double getTotalassetsAvg(List<FinInfo> finInfos) {
        double allSum = 0;
        for (FinInfo finInfo : finInfos) {
            allSum = allSum + finInfo.getTotalassets();
        }
        return allSum / finInfos.size();
    }

}
