package personal.xuzj157.stocksyn.utils;

public class MathUtils {

    /**
     * 逻辑s函数
     * (1/(1 + e^(-t/p)) - 0.5 ) * 2
     *
     * @param t
     * @param p
     * @return
     */
    public static Double logicS(double t, double p) {
//        return (1 / (1 + Math.pow(Math.E, ((-t) / p))) - 0.5) * 2;
        return (1 / (1 + Math.pow(Math.E, ((-t) / p))) - 0.5) * 2;

    }

}
