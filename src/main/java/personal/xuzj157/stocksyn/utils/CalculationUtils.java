package personal.xuzj157.stocksyn.utils;

import com.sun.javafx.scene.control.skin.VirtualFlow;
import lombok.extern.slf4j.Slf4j;
import personal.xuzj157.stocksyn.pojo.bo.RandomUnit;
import personal.xuzj157.stocksyn.pojo.bo.SecondCalculationUnit;
import personal.xuzj157.stocksyn.pojo.bo.SumUnit;

import java.util.*;

@Slf4j
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

    public static Map<Double, Integer> getMap(Map<Double, Integer> map, Integer num) {
        map = sortMapByKey(map);    //按Key进行排序
        for (Map.Entry<Double, Integer> entry : map.entrySet()) {
            map.replace(entry.getKey(), entry.getValue() / num);
        }
        return map;
    }


    /**
     * 使用 Map按key进行排序
     *
     * @param map
     * @return
     */
    public static Map<Double, Integer> sortMapByKey(Map<Double, Integer> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        Map<Double, Integer> sortMap = new TreeMap<>(new MapKeyComparator());
        sortMap.putAll(map);
        return sortMap;
    }

    public static class MapKeyComparator implements Comparator<Double> {
        public int compare(Double str1, Double str2) {
            return str1.compareTo(str2);
        }
    }

    public static void saveMap(Map<String, Map<Double, Integer>> mapOri) {
        Map<String, Map<String, Integer>> resultMap = new TreeMap<>();
        for (Map.Entry<String, Map<Double, Integer>> entry : mapOri.entrySet()) {
            Map<String, Integer> map = new HashMap<>();
            for (Map.Entry<Double, Integer> entry1 : entry.getValue().entrySet()) {
                map.put(String.valueOf((int) (entry1.getKey() * 100)), entry1.getValue());
            }
            resultMap.put(entry.getKey(), map);
        }

        MongoDB.writeResultObjectToDB("cal_history", resultMap);
    }

    /**
     * 初始化随机数单元
     *
     * @param times
     * @return
     */
    public static List<RandomUnit> getRandom(Integer times) {
        List<RandomUnit> randomUnitList = new LinkedList<>();
        for (int i = 0; i < times; i++) {
            randomUnitList.add(new RandomUnit());
        }
        log.info("getRandom finish");
        return randomUnitList;
    }

    public static List<SumUnit> getSumUnit(Integer times) {
        List<SumUnit> sumUnitList = new LinkedList<>();
        for (int i = 0; i < times; i++) {
            sumUnitList.add(new SumUnit(0.0, 0, new RandomUnit()));
        }
        return sumUnitList;
    }

    public static List<SumUnit> getSumList(List<SumUnit> newSumUnitList, List<SumUnit> oldSumUnitList) {
        for (int i = 0; i < newSumUnitList.size(); i++) {
            SumUnit oldSumUnit = oldSumUnitList.get(i);
            SumUnit newSumUnit = newSumUnitList.get(i);

            Double oldN = oldSumUnit.getN();
            Double newN = newSumUnit.getN();
            Integer oldTimes = oldSumUnit.getTimes();

            if (oldN == 0.0 || Math.abs((newN - oldN) / oldN) < 0.04) {
                oldSumUnit.setN(((double) oldTimes * oldN + newN) / ((double) oldTimes + 1));
                oldSumUnit.setTimes(oldTimes + 1);
                oldSumUnitList.set(i, oldSumUnit);
            }
        }
        return oldSumUnitList;
    }
}
