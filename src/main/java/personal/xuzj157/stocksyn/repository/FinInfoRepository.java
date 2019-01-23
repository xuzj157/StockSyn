package personal.xuzj157.stocksyn.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import personal.xuzj157.stocksyn.pojo.po.FinInfo;

import java.util.List;

@Repository
public interface FinInfoRepository extends PagingAndSortingRepository<FinInfo, String> {
    List<FinInfo> findAll();

    List<FinInfo> findAllByCodeOrderByReportDateDesc(String code);
}
