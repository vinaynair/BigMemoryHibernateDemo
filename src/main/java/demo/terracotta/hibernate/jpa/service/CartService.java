package demo.terracotta.hibernate.jpa.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import demo.terracotta.hibernate.jpa.model.Cart;
import demo.terracotta.hibernate.jpa.model.Order;
import demo.terracotta.hibernate.jpa.util.HibernateUtil;

public class CartService {
	@SuppressWarnings("unchecked")
	public Cart findCartByUserName(Session session, String userName) {
		Query query = session
				.createQuery("from Cart as cart where cart.userName=?");
		query.setParameter(0, userName);
		query.setCacheable(true);
		List<Cart> carts = query.list();
		Cart foundCart = null;
		for (Cart cart : carts) {
			foundCart = cart;
		}
		return foundCart;
	}

	public Order addOrder(String userName, String productName) {

		Session session = HibernateUtil.getSessionFactoryWithQueryCache()
				.openSession();
		Transaction tx = session.beginTransaction();
		tx.begin();
		Cart cart = findCartByUserName(session, userName);
		if (cart == null) {
			System.out.println("Create NEW cart for user " + userName);
			cart = new Cart(userName);
			cart.setOrders(new ArrayList<Order>());
			session.save(cart);
		}
		Order order = new Order(productName);
		cart.getOrders().add(order);
		session.saveOrUpdate(cart);
		tx.commit();
		session.close();
		return order;
	}
}
