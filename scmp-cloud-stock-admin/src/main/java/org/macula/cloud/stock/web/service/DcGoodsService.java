package org.macula.cloud.stock.web.service;

import org.macula.cloud.stock.domain.StockRoute;
import org.macula.cloud.stock.web.pojo.LcCategory;

import java.util.List;

public interface DcGoodsService {

    List<LcCategory> listByParentId(Long parentId);

    LcCategory query(Long id);

    List<StockRoute> findAll();

    Object save(StockRoute sr);
}
