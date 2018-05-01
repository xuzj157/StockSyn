package personal.xuzj157.stocksyn.utils;

import personal.xuzj157.stocksyn.pojo.bo.RandomUnit;
import personal.xuzj157.stocksyn.pojo.bo.SecondCalculationUnit;

public class CalculationUtils {

    public static double getSum(RandomUnit randomUnit, SecondCalculationUnit second) {
        double sum = 0;
        sum = sum + randomUnit.getNetincGrowRate() * second.getNetincGrowRate();
        sum = sum + randomUnit.getMainbusiincome() * second.getMainbusiincome();
        sum = sum + randomUnit.getNetprofit() * second.getNetprofit();
        sum = sum + randomUnit.getTotalassets() * second.getTotalassets();
        sum = sum + randomUnit.getPeratio() * second.getPeratio();
        sum = sum + randomUnit.getBvRatio() * second.getBvRatio();
        sum = sum + randomUnit.getFifteenPrice() * second.getFifteenPrice();
        sum = sum + randomUnit.getLowFifteenPrice() * second.getLowFifteenPrice();
        sum = sum + randomUnit.getThirtyPrice() * second.getThirtyPrice();
        sum = sum + randomUnit.getLowThirtyPrice() * second.getLowThirtyPrice();
        sum = sum + randomUnit.getSixtyPrice() * second.getSixtyPrice();
        sum = sum + randomUnit.getLowSixtyPrice() * second.getLowSixtyPrice();
        sum = sum + randomUnit.getOneEightyPrice() * second.getOneEightyPrice();
        sum = sum + randomUnit.getLowOneEightyPrice() * second.getLowOneEightyPrice();
        sum = sum + randomUnit.getFourHundredPrice() * second.getFourHundredPrice();
        sum = sum + randomUnit.getLowFourHundredPrice() * second.getLowFourHundredPrice();
        sum = sum + randomUnit.getHistoryPrice() * second.getLowHistoryPrice();
        sum = sum + randomUnit.getLowHistoryPrice() * second.getLowHistoryPrice();
        return sum;
    }

}
