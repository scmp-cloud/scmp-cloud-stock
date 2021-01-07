package org.macula.cloud.stock.repository;

import org.macula.cloud.stock.domain.StockRequestMaster;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRequestMasterRepository extends JpaRepository<StockRequestMaster, Long> {

}
