package personal.xuzj157.stocksyn.pojo.bo;

import lombok.Data;

import java.util.List;

@Data
public class CalculationResUnit {
    RandomUnitS randomUnit;
    List<SumUnit> sumUnitList;

    public CalculationResUnit(RandomUnitS randomUnit, List<SumUnit> sumUnitList) {
        this.randomUnit = randomUnit;
        this.sumUnitList = sumUnitList;
    }
}
