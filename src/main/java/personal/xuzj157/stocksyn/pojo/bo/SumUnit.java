package personal.xuzj157.stocksyn.pojo.bo;

import lombok.Data;

@Data
public class SumUnit {
    Double n;
    Integer times;
    RandomUnit randomUnit;

    public SumUnit() {
    }

    public SumUnit(Double n, Integer times, RandomUnit randomUnit) {
        this.n = n;
        this.times = times;
        this.randomUnit = randomUnit;
    }
}
