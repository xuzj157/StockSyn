package personal.xuzj157.stocksyn.repository.calculationUnit;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import personal.xuzj157.stocksyn.pojo.bo.SecondCalculationUnit;

@Repository
public interface SecondCalculationUnitRepository extends PagingAndSortingRepository<SecondCalculationUnit, String> {
}
