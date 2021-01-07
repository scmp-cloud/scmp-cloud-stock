package org.macula.cloud.stock.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "CN_STOCK_REQ_MASTER")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@EqualsAndHashCode(callSuper = true)
public class StockRequestMaster extends AbstractAuditable<Long> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "CLIENT_ID")
	private String clientId;

	@Column(name = "CHANNEL")
	private String channel;

	@Column(name = "DOCUMENT")
	private String document;

	@Column(name = "VERSION")
	private String version;

	@Column(name = "ACC_DATE")
	private String accDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "APPLY_TIME")
	private Date applyTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "INITIALIZED_TIME")
	private Date initializedTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "RESERVED_TIME")
	private Date reservedTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "RELEASED_TIME")
	private Date releasedTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "COMMITED_TIME")
	private Date commitedTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "INCREASED_TIME")
	private Date increasedTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "REDUCED_TIME")
	private Date reducedTime;

	// RESERVED, RELEASED, COMMITED
	@Column(name = "STATUS")
	private String status;

	/** 备注 */
	@Column(name = "COMMENTS")
	private String comments;

	@OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, targetEntity = StockRequestDetail.class)
	@JoinColumn(name = "P_ID")
	private List<StockRequestDetail> details = new ArrayList<>();

	public void addDetail(StockRequestDetail detail) {
		if (details == null) {
			details = new ArrayList<>();
		}
		details.add(detail);
	}
}
