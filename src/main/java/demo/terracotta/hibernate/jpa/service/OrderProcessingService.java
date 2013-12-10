package demo.terracotta.hibernate.jpa.service;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import demo.terracotta.hibernate.jpa.model.Cart;
import demo.terracotta.hibernate.jpa.model.Order;
import demo.terracotta.hibernate.jpa.util.HibernateUtil;

public class OrderProcessingService {
	@SuppressWarnings("unchecked")
	public List<Order> findAllOrdersByUserName(String userName) {
		Session session = HibernateUtil.getSessionFactoryWithQueryCache()
				.openSession();
		Transaction tx = session.beginTransaction();
		tx.begin();
		Query query = session
				.createQuery("from Cart as cart where cart.userName=?");
		query.setParameter(0, userName);
		query.setCacheable(true);
		List<Cart> carts = query.list();
		Cart foundCart = null;
		for (Cart cart : carts) {
			foundCart = cart;
		}
		tx.commit();
		session.close();

		if (foundCart == null) {
			return null;
		} else {
			return foundCart.getOrders();
		}

	}

	public List<Order> findAllOrdersByUserID(long id) {
		Session session = HibernateUtil.getSessionFactoryWithQueryCache()
				.openSession();
		Transaction tx = session.beginTransaction();
		tx.begin();
		Cart foundCart = (Cart) session.load(Cart.class, id);
		List<Order> orders;
		if (foundCart == null) {
			orders = null;
		} else {
			orders = foundCart.getOrders();
		}
		tx.commit();
		session.close();
		return orders;
	}

	public Order changeOrderStatus(long id, String status) {
		Session session = HibernateUtil.getSessionFactoryWithQueryCache()
				.openSession();
		Transaction tx = session.beginTransaction();
		tx.begin();
		Order order = (Order) session.load(Order.class, id);
		order.setStatus(status);
		session.save(order);
		tx.commit();
		session.close();
		return order;
	}
}
