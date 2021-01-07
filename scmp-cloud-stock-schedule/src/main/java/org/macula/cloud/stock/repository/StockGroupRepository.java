package org.macula.cloud.stock.repository;

import java.util.List;

import org.macula.cloud.stock.domain.StockGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StockGroupRepository extends JpaRepository<StockGroup, Long> {

	@Query("select max(a.level) from StockGroup a where a.productCode = '*' ")
	Long findDefaultGroupMaxLevel();

	@Query("select max(a.level) from StockGroup a where a.productCode <> '*' and a.productCode is not null ")
	Long findProductGroupMaxLevel();

	@Query("from StockGroup r where r.effectiveDate <= str_to_date( :accDate, '%Y%m%d' ) and  r.inactiveDate >= str_to_date( :accDate, '%Y%m%d' )")
	List<StockGroup> findCurrentGroups(@Param("accDate") String accDate);
}
