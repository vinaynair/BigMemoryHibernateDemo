package demo.terracotta.hibernate.jpa.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name="ORDERS")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Order {
	private long id;
	private String status="started";
	private String productName;

	public Order(String productName) {
		this.productName = productName;
	}
	public Order(){
		
	}
	@Id
	@GeneratedValue
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	@Override
	public String toString() {
		return "id:" + id + ",productName:" + productName + ",status:" + status;
	}
}
