package demo.terracotta.hibernate.jpa.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Account has a list of orders that are submitted <br/>
 * Cart has a similar list of orders that are yet to be submitted
 * 
 * @author vch
 * 
 */
@Entity
@Table
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Account {
	private long id;
	private String name;
	private List<Order> orders;

	public Account(String name) {
		this.name = name;
	}

	public Account() {

	}

	@Column
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	@Id
	@GeneratedValue
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

}
