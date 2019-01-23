package personal.xuzj157.stocksyn.service.impl;

import org.springframework.stereotype.Service;
import personal.xuzj157.stocksyn.pojo.bo.BasicUnit;
import personal.xuzj157.stocksyn.pojo.bo.FirstCalculationUnit;
import personal.xuzj157.stocksyn.pojo.bo.SecondCalculationUnit;
import personal.xuzj157.stocksyn.pojo.po.FinInfo;
import personal.xuzj157.stocksyn.pojo.po.HqInfo;
import personal.xuzj157.stocksyn.pojo.po.SnapShot;
import personal.xuzj157.stocksyn.pojo.po.Symbol;
import personal.xuzj157.stocksyn.repository.*;
import personal.xuzj157.stocksyn.repository.calculationUnit.FirstCalculationUnitRepository;
import personal.xuzj157.stocksyn.repository.calculationUnit.SecondCalculationUnitRepository;
import personal.xuzj157.stocksyn.service.BasicService;
import personal.xuzj157.stocksyn.utils.FirstCalculationUnitUtils;
import personal.xuzj157.stocksyn.utils.MathUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class BasicServiceImpl implements BasicService {

    @Resource
    FinInfoRepository finInfoRepository;
    @Resource
    HqInfoRepository hqInfoRepository;
    @Resource
    SnapShotRepository snapShotRepository;
    @Resource
    SymbolRepository symbolRepository;
    @Resource
    FirstCalculationUnitRepository firstCalculationUnitRepository;
    @Resource
    SecondCalculationUnitRepository secondCalculationUnitRepository;


    @Override
    public void ori2First() {
        List<Symbol> symbolList = symbolRepository.findAll();
        ExecutorService executorService = Executors.newFixedThreadPool(6);

        for (Symbol symbol : symbolList) {
            executorService.execute(() -> {
                String code;
                Double upRate;
                Double nowPrice;
                Double fifteenPrice, lowFifteenPrice,
                        thirtyPrice, lowThirtyPrice,
                        sixtyPrice, lowSixtyPrice,
                        oneEightyPrice, lowOneEightyPrice,
                        fourHundredPrice, lowFourHundredPrice,
                        historyPrice, lowHistoryPrice;
                Double peratio, bvRatio;
                Double naps, basiceps, oPerCashPerShare, netassgrowrate, weightedroe, mainBusincGrowRate, totassgrowrate,
                        netincGrowRate, mainbusiincome, mainbusiincomeAvg,
                        netprofit, netprofitAvg,
                        totalassets, totalassetsAvg;

                FirstCalculationUnit firstCalculationUnit = new FirstCalculationUnit();
                code = symbol.getStockCode();
                SnapShot snapShot = snapShotRepository.findByCode(code);
                List<FinInfo> finInfoList = finInfoRepository.findAllByCode(code);
                List<HqInfo> hqInfoList = hqInfoRepository.findHqInfosByCode(code);

                //筛选条件
                if (hqInfoList.size() > 900) {
                    if (snapShot.getPeratio() < 50.0 && snapShot.getPeratio() != 0) {
                        firstCalculationUnit.setCode(code);
//                    ----------------------------------SnapShot操作-----------------------------
                        peratio = snapShot.getPeratio();
                        firstCalculationUnit.setPeratio(peratio);
                        bvRatio = snapShot.getBvRatio();
                        firstCalculationUnit.setBvRatio(bvRatio);
//                -------------------------------------hqInfoList相关操作------------------------------------------------------
                        //upRate
                        upRate = FirstCalculationUnitUtils.getUpDate(hqInfoList);
                        firstCalculationUnit.setUpRate(upRate);
                        //nowPrice
                        nowPrice = FirstCalculationUnitUtils.getSomedayPrice(hqInfoList, 60);
                        firstCalculationUnit.setNowPrice(nowPrice);
                        //fifteenPrice
                        fifteenPrice = FirstCalculationUnitUtils.getAvgPeriodPrice(hqInfoList, 75, 60);
                        firstCalculationUnit.setFifteenPrice(fifteenPrice);
                        //lowFifteenPrice
                        lowFifteenPrice = FirstCalculationUnitUtils.getLowPeriodPrice(hqInfoList, 75, 60);
                        firstCalculationUnit.setLowFifteenPrice(lowFifteenPrice);
                        //thirtyPrice
                        thirtyPrice = FirstCalculationUnitUtils.getAvgPeriodPrice(hqInfoList, 90, 60);
                        firstCalculationUnit.setThirtyPrice(thirtyPrice);
                        //lowThirtyPrice
                        lowThirtyPrice = FirstCalculationUnitUtils.getLowPeriodPrice(hqInfoList, 90, 60);
                        firstCalculationUnit.setLowThirtyPrice(lowThirtyPrice);
                        //sixtyPrice
                        sixtyPrice = FirstCalculationUnitUtils.getAvgPeriodPrice(hqInfoList, 120, 60);
                        firstCalculationUnit.setSixtyPrice(sixtyPrice);
                        //lowSixtyPrice
                        lowSixtyPrice = FirstCalculationUnitUtils.getLowPeriodPrice(hqInfoList, 120, 60);
                        firstCalculationUnit.setLowSixtyPrice(lowSixtyPrice);
                        //oneEightyPrice
                        oneEightyPrice = FirstCalculationUnitUtils.getAvgPeriodPrice(hqInfoList, 240, 60);
                        firstCalculationUnit.setOneEightyPrice(oneEightyPrice);
                        //lowOneEightyPrice
                        lowOneEightyPrice = FirstCalculationUnitUtils.getLowPeriodPrice(hqInfoList, 240, 60);
                        firstCalculationUnit.setLowOneEightyPrice(lowOneEightyPrice);
                        //fourHundredPrice
                        fourHundredPrice = FirstCalculationUnitUtils.getAvgPeriodPrice(hqInfoList, 460, 60);
                        firstCalculationUnit.setFourHundredPrice(fourHundredPrice);
                        //lowFourHundredPrice
                        lowFourHundredPrice = FirstCalculationUnitUtils.getLowPeriodPrice(hqInfoList, 460, 60);
                        firstCalculationUnit.setLowFourHundredPrice(lowFourHundredPrice);
                        //historyPrice
                        historyPrice = FirstCalculationUnitUtils.getAvgPeriodPrice(hqInfoList, hqInfoList.size(), 60);
                        firstCalculationUnit.setHistoryPrice(historyPrice);
                        //lowHistoryPrice
                        lowHistoryPrice = FirstCalculationUnitUtils.getLowPeriodPrice(hqInfoList, hqInfoList.size(), 60);
                        firstCalculationUnit.setLowHistoryPrice(lowHistoryPrice);
//                    ----------------------------FinInfo操作---------------------------------------------
                        //basiceps
                        basiceps = finInfoList.get(finInfoList.size() - 2).getBasiceps();
                        //naps
                        naps = finInfoList.get(finInfoList.size() - 2).getNaps();
                        //oPerCashPerShare
                        oPerCashPerShare = finInfoList.get(finInfoList.size() - 2).getOPerCashPerShare();
                        //netassgrowrate
                        netassgrowrate = finInfoList.get(finInfoList.size() - 2).getNetassgrowrate();
                        //weightedroe
                        weightedroe = finInfoList.get(finInfoList.size() - 2).getWeightedroe();
                        //mainBusincGrowRate
                        mainBusincGrowRate = finInfoList.get(finInfoList.size() - 2).getMainBusincGrowRate();
                        //totassgrowrate
                        totassgrowrate = finInfoList.get(finInfoList.size() - 2).getTotassgrowrate();

                        //netincGrowRate
                        netincGrowRate = finInfoList.get(finInfoList.size() - 2).getNetincGrowRate();
                        firstCalculationUnit.setNetincGrowRate(netincGrowRate);
                        //mainbusiincome
                        mainbusiincome = finInfoList.get(finInfoList.size() - 2).getMainbusiincome();
                        firstCalculationUnit.setMainbusiincome(mainbusiincome);
                        //mainbusiincomeAvg
                        mainbusiincomeAvg = FirstCalculationUnitUtils.getMainbusiincomeAvg(finInfoList);
                        firstCalculationUnit.setMainbusiincomeAvg(mainbusiincomeAvg);
                        //netprofit
                        netprofit = finInfoList.get(finInfoList.size() - 2).getNetprofit();
                        firstCalculationUnit.setNetprofit(netprofit);
                        //netprofitAvg
                        netprofitAvg = FirstCalculationUnitUtils.getNetprofitAvg(finInfoList);
                        firstCalculationUnit.setNetprofitAvg(netprofitAvg);
                        //totalassets
                        totalassets = finInfoList.get(finInfoList.size() - 2).getTotalassets();
                        firstCalculationUnit.setTotalassets(totalassets);
                        //totalassetsAvg
                        totalassetsAvg = FirstCalculationUnitUtils.getTotalassetsAvg(finInfoList);
                        firstCalculationUnit.setTotalassetsAvg(totalassetsAvg);
//                    --------------------------------------存储------------------------------------------
                        firstCalculationUnitRepository.save(firstCalculationUnit);
                    }
                }
            });
        }

    }

    @Override
    public void first2Second() {
        BasicUnit basicUnit = initBasic();
        List<FirstCalculationUnit> firstCalculationUnitList = firstCalculationUnitRepository.findAll();
        SecondCalculationUnit second = new SecondCalculationUnit();

        for (FirstCalculationUnit first : firstCalculationUnitList) {
            second.setCode(first.getCode());
            second.setUpRate(first.getUpRate());
            second.setNetincGrowRate(MathUtils.logicS(first.getNetincGrowRate(), basicUnit.getNetincGrowRateAvg()));
            second.setMainbusiincome(MathUtils.logicS(first.getMainbusiincome(), first.getMainbusiincomeAvg()));
            second.setNetprofit(MathUtils.logicS(first.getNetprofit(), first.getNetprofitAvg()));
            second.setTotalassets(MathUtils.logicS(first.getTotalassets(), first.getTotalassetsAvg()));
            second.setPeratio(MathUtils.logicS(first.getPeratio(), basicUnit.getPeratioAvg()));
            second.setBvRatio(MathUtils.logicS(first.getBvRatio(), basicUnit.getBvRatioAvg()));

            double now = first.getNowPrice();
            second.setFifteenPrice(first.getFifteenPrice() / now);
            second.setLowFifteenPrice(first.getLowFifteenPrice() / now);
            second.setThirtyPrice(first.getThirtyPrice() / now);
            second.setLowThirtyPrice(first.getLowThirtyPrice() / now);
            second.setSixtyPrice(first.getSixtyPrice() / now);
            second.setLowSixtyPrice(first.getLowSixtyPrice() / now);
            second.setOneEightyPrice(first.getOneEightyPrice() / now);
            second.setLowOneEightyPrice(first.getLowOneEightyPrice() / now);
            second.setFourHundredPrice(first.getFourHundredPrice() / now);
            second.setLowFourHundredPrice(first.getLowFourHundredPrice() / now);
            second.setHistoryPrice(first.getHistoryPrice() / now);
            second.setLowHistoryPrice(first.getLowHistoryPrice() / now);

            secondCalculationUnitRepository.save(second);
        }
    }

    /**
     * 初始化
     */
    @Override
    public BasicUnit initBasic() {
        BasicUnit basicUnit = new BasicUnit();

        List<FirstCalculationUnit> firstCalculationUnitList = firstCalculationUnitRepository.findAll();

        double netincGrowRateAvgSum = 0;   //平均净利润增长率
        double peratioAvgSum = 0;       //平均市盈率
        double bvRatioAvgSum = 0;       //平均市净率
        for (FirstCalculationUnit firstCalculationUnit : firstCalculationUnitList) {
            peratioAvgSum = peratioAvgSum + firstCalculationUnit.getPeratio();
            bvRatioAvgSum = bvRatioAvgSum + firstCalculationUnit.getBvRatio();
            netincGrowRateAvgSum = netincGrowRateAvgSum + firstCalculationUnit.getNetincGrowRate();
        }
        basicUnit.setPeratioAvg(peratioAvgSum / firstCalculationUnitList.size());
        basicUnit.setBvRatioAvg(bvRatioAvgSum / firstCalculationUnitList.size());
        basicUnit.setNetincGrowRateAvg(netincGrowRateAvgSum / firstCalculationUnitList.size());
        return basicUnit;
    }

}
