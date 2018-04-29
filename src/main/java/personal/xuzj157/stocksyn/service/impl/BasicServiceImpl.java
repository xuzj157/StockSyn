package personal.xuzj157.stocksyn.service.impl;

import org.springframework.stereotype.Service;
import personal.xuzj157.stocksyn.pojo.bo.BasicUnit;
import personal.xuzj157.stocksyn.pojo.po.FinInfo;
import personal.xuzj157.stocksyn.pojo.po.SnapShot;
import personal.xuzj157.stocksyn.repository.*;
import personal.xuzj157.stocksyn.repository.calculationUnit.FirstCalculationUnitRepository;
import personal.xuzj157.stocksyn.repository.calculationUnit.SecondCalculationUnitRepository;
import personal.xuzj157.stocksyn.service.BasicService;

import javax.annotation.Resource;
import java.util.List;

@Service
public class BasicServiceImpl implements BasicService {

    @Resource
    BasicInfoRepository basicInfoRepository;
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

    private BasicUnit basicUnit;

    @Override
    public void ori2First() {

    }

    @Override
    public void first2Second() {

    }

    /**
     * 初始化
     */
    @Override
    public void initBasic() {
        List<SnapShot> snapShotList = snapShotRepository.findAll();
        List<FinInfo> finInfoList = finInfoRepository.findAll();

        double netincGrowRateAvgSum = 0;   //平均净利润增长率
        double peratioAvgSum = 0;       //平均市盈率
        double bvRatioAvgSum = 0;       //平均市净率

        for (SnapShot snapShot : snapShotList) {
            peratioAvgSum = peratioAvgSum + snapShot.getPeratio();
            bvRatioAvgSum = bvRatioAvgSum + snapShot.getBvRatio();
        }
        basicUnit.setPeratioAvg(peratioAvgSum / snapShotList.size());
        basicUnit.setBvRatioAvg(bvRatioAvgSum / snapShotList.size());

        for (FinInfo finInfo : finInfoList) {
            netincGrowRateAvgSum = netincGrowRateAvgSum + finInfo.getNetincGrowRate();
        }
        basicUnit.setNetincGrowRateAvg(netincGrowRateAvgSum / finInfoList.size());
    }

}
