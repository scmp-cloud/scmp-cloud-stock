package org.macula.cloud.stock.web.service.impl;

import lombok.AllArgsConstructor;
import org.macula.cloud.stock.domain.StockRoute;
import org.macula.cloud.stock.web.pojo.LcCategory;
import org.macula.cloud.stock.web.repository.DcGoodsRepository;
import org.macula.cloud.stock.web.repository.StockRouteRepository;
import org.macula.cloud.stock.web.service.DcGoodsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DcGoodsServiceImpl implements DcGoodsService {

    private DcGoodsRepository dcGoodsRepository;

    private StockRouteRepository stockRouteRepository;

    @Override
    public List<LcCategory> listByParentId(Long parentId) {
        return dcGoodsRepository.findByParentId(parentId);
    }

    @Override
    public LcCategory query(Long id) {
        Optional<LcCategory> lc = dcGoodsRepository.findById(id);
        return lc.get();
    }

    @Override
    public List<StockRoute> findAll() {
        return stockRouteRepository.findAll();
    }

    @Override
    public Object save(StockRoute sr) {
        return stockRouteRepository.save(sr);
    }
}
