package personal.xuzj157.stocksyn.crawler.plugin;

import personal.xuzj157.stocksyn.pojo.po.SnapShot;

public interface BaiduService {

    SnapShot getSnapShotFromAndroid(int start, int end, String name);

    SnapShot getSnapShotFromAndroid();
}
