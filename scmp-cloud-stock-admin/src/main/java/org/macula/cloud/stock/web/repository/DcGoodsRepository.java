package org.macula.cloud.stock.web.repository;

import java.util.List;

import org.macula.cloud.stock.web.pojo.LcCategory;
import org.springframework.data.jpa.repository.JpaRepository;
public interface DcGoodsRepository extends JpaRepository<LcCategory, Long> {

    List<LcCategory> findByParentId(Long parentId);
}
