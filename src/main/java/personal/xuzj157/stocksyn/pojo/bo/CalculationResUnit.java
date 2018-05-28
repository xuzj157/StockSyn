package personal.xuzj157.stocksyn.pojo.bo;

import lombok.Data;

import java.util.List;

@Data
public class CalculationResUnit {
    RandomUnit randomUnit;
    List<SumUnit> sumUnitList;

    public CalculationResUnit(RandomUnit randomUnit, List<SumUnit> sumUnitList) {
        this.randomUnit = randomUnit;
        this.sumUnitList = sumUnitList;
    }
}
