package org.macula.cloud.stock.web.service;

import java.util.List;

import org.macula.cloud.stock.domain.StockRoute;
import org.macula.cloud.stock.web.pojo.LcCategory;

public interface DcGoodsService {

    List<LcCategory> listByParentId(Long parentId);

    LcCategory query(Long id);

    List<StockRoute> findAll();

    Object save(StockRoute sr);
}
