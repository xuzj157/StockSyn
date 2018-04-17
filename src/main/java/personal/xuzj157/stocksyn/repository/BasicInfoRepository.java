package personal.xuzj157.stocksyn.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import personal.xuzj157.stocksyn.pojo.po.BasicInfo;
import personal.xuzj157.stocksyn.pojo.po.Symbol;

import java.util.List;

@Repository
public interface BasicInfoRepository extends PagingAndSortingRepository<BasicInfo, String> {

}
