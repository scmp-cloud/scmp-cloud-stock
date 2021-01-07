package org.macula.cloud.stock.repository;

import java.util.List;

import org.macula.cloud.stock.domain.StockRoute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StockRouteRepository extends JpaRepository<StockRoute, Long> {

	@Query("from StockRoute r where r.effectiveDate <= str_to_date( :accDate, '%Y%m%d' ) and  r.inactiveDate >= str_to_date( :accDate, '%Y%m%d' ) and r.active = true")
	List<StockRoute> findCurrentRoutes(@Param("accDate") String accDate);
}
