package personal.xuzj157.stocksyn.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import personal.xuzj157.stocksyn.pojo.po.SnapShot;

import java.util.List;

@Repository
public interface SnapShotRepository extends PagingAndSortingRepository<SnapShot, String> {

    List<SnapShot> findAll();

    SnapShot findByCode(String code);

}
