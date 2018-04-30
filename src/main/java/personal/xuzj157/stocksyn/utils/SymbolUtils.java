package personal.xuzj157.stocksyn.utils;

import personal.xuzj157.stocksyn.pojo.po.HqInfo;

public class SymbolUtils {

    public static String getSymbol(String symbol) {
        if (symbol.matches("60[0-9]*")) {
            return "SH" + symbol;
        } else if (symbol.matches("(3|0)0[0-9]*]")) {
            return "SZ" + symbol;
        }
        return "SH" + symbol;
    }

    /**
     * 获取一天均价
     *
     * @param hqInfo 当天数据
     * @return 均价
     */
    public static Double getAvgOneDay(HqInfo hqInfo) {
        return (hqInfo.getOpen() + hqInfo.getClose()) / 2;
    }

}
