package org.macula.cloud.stock.web.pojo;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.macula.cloud.stock.domain.AbstractAuditable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "LC_CATEGORY")
public class LcCategory extends AbstractAuditable<java.lang.Long> implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "PARENT_ID")
	private Long parentId;

	@Column(name = "NAME")
	private String name;

	@Column(name = "PRODUCT_CODE")
	private String productCode;

	@Column(name = "STOCK")
	private String stock;

	@Column(name = "PRICE")
	private BigDecimal price;

	@Column(name = "CATEGORY_TYPE")
	private Short categoryType;

	@Column(name = "STATUS")
	private Short status;

	@Column(name = "LEAF_FLAG")
	private Boolean leafFlag;

	@Column(name = "DESCRIPTION")
	private String description;

}
