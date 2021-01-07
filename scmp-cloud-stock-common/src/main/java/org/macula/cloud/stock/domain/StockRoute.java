package org.macula.cloud.stock.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.macula.cloud.stock.util.InventoryUtils;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "CN_STOCK_ROUTE")
public class StockRoute extends AbstractAuditable<Long> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "PRODUCT_CODE")
    private String productCode;

    @Column(name = "DELIVERY_TYPE")
    private String deliveryType;

    @Column(name = "SOURCE_CODE")
    private String sourceCode;

    @Column(name = "STOCK_CODE")
    private String stockCode;

    @Column(name = "DC_0")
    private String dc0;

    @Column(name = "DC_1")
    private String dc1;

    @Column(name = "DC_2")
    private String dc2;

    @Column(name = "DC_3")
    private String dc3;

    @Column(name = "DC_4")
    private String dc4;

    @Column(name = "DC_5")
    private String dc5;

    @Column(name = "DC_6")
    private String dc6;

    @Column(name = "DC_7")
    private String dc7;

    @Column(name = "DC_8")
    private String dc8;

    @Column(name = "DC_9")
    private String dc9;

    @Column(name = "DC_X")
    private String dcX;

    @Temporal(TemporalType.DATE)
    @Column(name = "EFFECTIVE_DATE")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date effectiveDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "INACTIVE_DATE")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date inactiveDate;

    @Column(name = "ACTIVE")
    private boolean active;

    public String getRouteRedisKey(String accDate) {
        return InventoryUtils.getRouteRedisKey(accDate, productCode, sourceCode, deliveryType);
    }

    public List<String> getFulfillmentPath() {
        List<String> fulfillmentPaths = new ArrayList<String>();
        if (StringUtils.isNotEmpty(dc0)) {
            fulfillmentPaths.add(dc0);
        }
        if (StringUtils.isNotEmpty(dc1)) {
            fulfillmentPaths.add(dc1);
        }
        if (StringUtils.isNotEmpty(dc2)) {
            fulfillmentPaths.add(dc2);
        }
        if (StringUtils.isNotEmpty(dc3)) {
            fulfillmentPaths.add(dc3);
        }
        if (StringUtils.isNotEmpty(dc4)) {
            fulfillmentPaths.add(dc4);
        }
        if (StringUtils.isNotEmpty(dc5)) {
            fulfillmentPaths.add(dc5);
        }
        if (StringUtils.isNotEmpty(dc6)) {
            fulfillmentPaths.add(dc6);
        }
        if (StringUtils.isNotEmpty(dc7)) {
            fulfillmentPaths.add(dc7);
        }
        if (StringUtils.isNotEmpty(dc8)) {
            fulfillmentPaths.add(dc8);
        }
        if (StringUtils.isNotEmpty(dc9)) {
            fulfillmentPaths.add(dc9);
        }
        if (StringUtils.isNotEmpty(dcX)) {
            fulfillmentPaths.addAll(Arrays.asList(dcX.split(",")));
        }
        return fulfillmentPaths;
    }
}
