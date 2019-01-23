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
    public void ori2First(int days) {
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
                List<FinInfo> finInfoList = finInfoRepository.findAllByCodeOrderByReportDateDesc(code);
                List<HqInfo> hqInfoList = hqInfoRepository.findHqInfosByCodeOrderByDataAsc(code);

                //筛选条件
                if (hqInfoList.size() > 600) {
                    if (snapShot.getPeratio() < 50.0 && snapShot.getPeratio() != 0) {
                        firstCalculationUnit.setCode(code);
//                    ----------------------------------SnapShot操作-----------------------------
                        peratio = snapShot.getPeratio();
                        firstCalculationUnit.setPeratio(peratio);
                        bvRatio = snapShot.getBvRatio();
                        firstCalculationUnit.setBvRatio(bvRatio);
//                -------------------------------------hqInfoList相关操作------------------------------------------------------
                        //upRate
                        upRate = FirstCalculationUnitUtils.getUpDate(hqInfoList, days);
                        firstCalculationUnit.setUpRate(upRate);
                        //nowPrice
                        nowPrice = FirstCalculationUnitUtils.getSomedayPrice(hqInfoList, days);
                        firstCalculationUnit.setNowPrice(nowPrice);
                        //fifteenPrice
                        fifteenPrice = FirstCalculationUnitUtils.getAvgPeriodPrice(hqInfoList, days + 15, days);
                        firstCalculationUnit.setFifteenPrice(fifteenPrice);
                        //lowFifteenPrice
                        lowFifteenPrice = FirstCalculationUnitUtils.getLowPeriodPrice(hqInfoList, days + 15, days);
                        firstCalculationUnit.setLowFifteenPrice(lowFifteenPrice);
                        //thirtyPrice
                        thirtyPrice = FirstCalculationUnitUtils.getAvgPeriodPrice(hqInfoList, days + 30, days);
                        firstCalculationUnit.setThirtyPrice(thirtyPrice);
                        //lowThirtyPrice
                        lowThirtyPrice = FirstCalculationUnitUtils.getLowPeriodPrice(hqInfoList, days + 30, days);
                        firstCalculationUnit.setLowThirtyPrice(lowThirtyPrice);
                        //sixtyPrice
                        sixtyPrice = FirstCalculationUnitUtils.getAvgPeriodPrice(hqInfoList, days + 60, days);
                        firstCalculationUnit.setSixtyPrice(sixtyPrice);
                        //lowSixtyPrice
                        lowSixtyPrice = FirstCalculationUnitUtils.getLowPeriodPrice(hqInfoList, days + 60, days);
                        firstCalculationUnit.setLowSixtyPrice(lowSixtyPrice);
                        //oneEightyPrice
                        oneEightyPrice = FirstCalculationUnitUtils.getAvgPeriodPrice(hqInfoList, days + 180, days);
                        firstCalculationUnit.setOneEightyPrice(oneEightyPrice);
                        //lowOneEightyPrice
                        lowOneEightyPrice = FirstCalculationUnitUtils.getLowPeriodPrice(hqInfoList, days + 180, days);
                        firstCalculationUnit.setLowOneEightyPrice(lowOneEightyPrice);
                        //fourHundredPrice
                        fourHundredPrice = FirstCalculationUnitUtils.getAvgPeriodPrice(hqInfoList, days + 400, days);
                        firstCalculationUnit.setFourHundredPrice(fourHundredPrice);
                        //lowFourHundredPrice
                        lowFourHundredPrice = FirstCalculationUnitUtils.getLowPeriodPrice(hqInfoList, days + 400, days);
                        firstCalculationUnit.setLowFourHundredPrice(lowFourHundredPrice);
                        //historyPrice
                        historyPrice = FirstCalculationUnitUtils.getAvgPeriodPrice(hqInfoList, hqInfoList.size(), days);
                        firstCalculationUnit.setHistoryPrice(historyPrice);
                        //lowHistoryPrice
                        lowHistoryPrice = FirstCalculationUnitUtils.getLowPeriodPrice(hqInfoList, hqInfoList.size(), days);
                        firstCalculationUnit.setLowHistoryPrice(lowHistoryPrice);
//                    ----------------------------FinInfo操作---------------------------------------------
                        //basiceps
                        basiceps = finInfoList.get(0).getBasiceps();
                        firstCalculationUnit.setBasiceps(basiceps);
                        //naps
                        naps = finInfoList.get(0).getNaps();
                        firstCalculationUnit.setNaps(naps);
                        //oPerCashPerShare
                        oPerCashPerShare = finInfoList.get(0).getOPerCashPerShare();
                        firstCalculationUnit.setOPerCashPerShare(oPerCashPerShare);
                        //netassgrowrate
                        netassgrowrate = finInfoList.get(0).getNetassgrowrate();
                        firstCalculationUnit.setNetassgrowrate(netassgrowrate);
                        //weightedroe
                        weightedroe = finInfoList.get(0).getWeightedroe();
                        firstCalculationUnit.setWeightedroe(weightedroe);
                        //mainBusincGrowRate
                        mainBusincGrowRate = finInfoList.get(0).getMainBusincGrowRate();
                        firstCalculationUnit.setMainBusincGrowRate(mainBusincGrowRate);
                        //totassgrowrate
                        totassgrowrate = finInfoList.get(0).getTotassgrowrate();
                        firstCalculationUnit.setTotassgrowrate(totassgrowrate);
                        //netincGrowRate
                        netincGrowRate = finInfoList.get(0).getNetincGrowRate();
                        firstCalculationUnit.setNetincGrowRate(netincGrowRate);
                        //mainbusiincome
                        mainbusiincome = finInfoList.get(0).getMainbusiincome();
                        firstCalculationUnit.setMainbusiincome(mainbusiincome);
                        //mainbusiincomeAvg
                        mainbusiincomeAvg = FirstCalculationUnitUtils.getMainbusiincomeAvg(finInfoList);
                        firstCalculationUnit.setMainbusiincomeAvg(mainbusiincomeAvg);
                        //netprofit
                        netprofit = finInfoList.get(0).getNetprofit();
                        firstCalculationUnit.setNetprofit(netprofit);
                        //netprofitAvg
                        netprofitAvg = FirstCalculationUnitUtils.getNetprofitAvg(finInfoList);
                        firstCalculationUnit.setNetprofitAvg(netprofitAvg);
                        //totalassets
                        totalassets = finInfoList.get(0).getTotalassets();
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
            second.setNetassgrowrate(first.getNetassgrowrate() / 100.0);
            second.setWeightedroe(first.getWeightedroe() / 100.0);
            second.setMainBusincGrowRate(first.getMainBusincGrowRate() / 100.0);
            second.setTotassgrowrate(first.getTotassgrowrate() / 100.0);

            double now = first.getNowPrice();
            second.setBasiceps(first.getBasiceps() / now);
            second.setNaps(first.getNaps() / now);
            second.setOPerCashPerShare(first.getOPerCashPerShare() / now);
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
