package org.macula.cloud.stock.web.controller;

import java.util.List;

import org.macula.cloud.stock.domain.StockRoute;
import org.macula.cloud.stock.web.pojo.LcCategory;
import org.macula.cloud.stock.web.service.DcGoodsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.csp.sentinel.annotation.SentinelResource;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping(value = "/v1/goods")
public class DcGoodsController {

	private DcGoodsService dcGoodsService;

	public DcGoodsController(DcGoodsService dcGoodsService) {
		this.dcGoodsService = dcGoodsService;
	}

	@GetMapping
	@Operation(description = "根据父id 查询商品分类列表")
	public ResponseEntity<List<LcCategory>> list(@RequestParam("parent_id") Long parentId) {
		return new ResponseEntity<>(dcGoodsService.listByParentId(parentId), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	@Operation(description = "根据id查询商品类目")
	public ResponseEntity<LcCategory> query(@PathVariable("id") Long id) {
		return new ResponseEntity<>(dcGoodsService.query(id), HttpStatus.OK);
	}

	@SentinelResource
	@GetMapping("/all")
	@Operation(description = "查询SKU配置表")
	public ResponseEntity<List<StockRoute>> findAll() {
		return new ResponseEntity<>(dcGoodsService.findAll(), HttpStatus.OK);
	}

	@PostMapping("/add")
	@Operation(description = "保存SKU配置记录")
	public ResponseEntity<?> addOne(@RequestBody StockRoute sr) {
		return new ResponseEntity<>(dcGoodsService.save(sr), HttpStatus.OK);
	}

}
