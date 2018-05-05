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
        return (1.0 / (1.0 + Math.pow(Math.E, ((-t) / p))) - 0.5) * 2;
    }

    public static Double logicS(double t, double p, double range) {
        return (1.0 / (1.0 + Math.pow(Math.E, ((-t) / p))) - 0.5) * range;
    }

    /**
     * y = ax + b
     *
     * @return a
     */
    public static Double linearFunction(Double y, Double x, Double b) {
        return (y - b) / x;
    }

    /**
     * y = nx
     *
     * @return n
     */
    public static Double linearFunction(Double y, Double x) {
        return y / x;
    }
}
