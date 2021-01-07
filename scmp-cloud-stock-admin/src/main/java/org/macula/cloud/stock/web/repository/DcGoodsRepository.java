package org.macula.cloud.stock.web.repository;

import org.macula.cloud.stock.web.pojo.LcCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
public interface DcGoodsRepository extends JpaRepository<LcCategory, Long> {

    List<LcCategory> findByParentId(Long parentId);
}
