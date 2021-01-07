package org.macula.cloud.stock.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.macula.cloud.stock.util.InventoryUtils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "CN_STOCK_GROUP")
public class StockGroup extends AbstractAuditable<Long> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "PRODUCT_CODE")
	private String productCode;

	@Column(name = "WH_CODE")
	private String whCode;

	@Column(name = "LEVEL")
	private Long level;

	@Column(name = "PARENT_CODE")
	private String parentCode;

	@Column(name = "FULL_PATH")
	private String fullPath;

	@Temporal(TemporalType.DATE)
	@Column(name = "EFFECTIVE_DATE")
	private Date effectiveDate;

	@Temporal(TemporalType.DATE)
	@Column(name = "INACTIVE_DATE")
	private Date inactiveDate;

	public boolean isParentGroupOf(StockGroup stockGroup) {
		return this.getProductCode().equals(stockGroup.getProductCode()) && getWhCode().equals(stockGroup.getParentCode());
	}

	public String getGroupRedisKey(String accDate) {
		return InventoryUtils.getGroupRedisKey(accDate, productCode, whCode);
	}
}
