package personal.xuzj157.stocksyn.repository.calculationUnit;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import personal.xuzj157.stocksyn.pojo.bo.RandomUnit;

import java.util.List;

@Repository
public interface RandomUnitRepository extends PagingAndSortingRepository<RandomUnit, String> {

    List<RandomUnit> findAll();

}
