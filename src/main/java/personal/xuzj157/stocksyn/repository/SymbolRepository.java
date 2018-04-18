package personal.xuzj157.stocksyn.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import personal.xuzj157.stocksyn.pojo.po.Symbol;

@Repository
public interface SymbolRepository extends PagingAndSortingRepository<Symbol, String> {
}
