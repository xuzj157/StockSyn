package personal.xuzj157.stocksyn.service;

import personal.xuzj157.stocksyn.pojo.bo.BasicUnit;

public interface BasicService {

    void ori2First(int days);

    void first2Second();

    BasicUnit initBasic();
}
