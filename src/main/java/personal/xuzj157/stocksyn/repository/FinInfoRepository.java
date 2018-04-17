package personal.xuzj157.stocksyn.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import personal.xuzj157.stocksyn.pojo.po.BasicInfo;
import personal.xuzj157.stocksyn.pojo.po.FinInfo;

@Repository
public interface FinInfoRepository extends PagingAndSortingRepository<FinInfo, String> {

}
