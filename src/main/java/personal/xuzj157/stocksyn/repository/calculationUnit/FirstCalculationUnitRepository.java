package personal.xuzj157.stocksyn.repository.calculationUnit;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import personal.xuzj157.stocksyn.pojo.bo.FirstCalculationUnit;

@Repository
public interface FirstCalculationUnitRepository extends PagingAndSortingRepository<FirstCalculationUnit, String> {
}
