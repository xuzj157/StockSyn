package personal.xuzj157.stocksyn.utils;

public class SymbolUtils {

    public static String getSymbol(String symbol) {
        if (symbol.matches("60[0-9]*")) {
            return "SH" + symbol;
        } else if (symbol.matches("(3|0)0[0-9]*]")) {
            return "SZ" + symbol;
        }
        return "SH" + symbol;
    }

}
