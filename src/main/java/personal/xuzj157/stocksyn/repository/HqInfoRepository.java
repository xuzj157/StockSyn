package personal.xuzj157.stocksyn.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import personal.xuzj157.stocksyn.pojo.po.FinInfo;
import personal.xuzj157.stocksyn.pojo.po.HqInfo;

@Repository
public interface HqInfoRepository extends PagingAndSortingRepository<HqInfo, String> {

}
