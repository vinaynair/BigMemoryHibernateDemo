package demo.terracotta.hibernate.jpa.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import demo.terracotta.hibernate.jpa.model.Account;
import demo.terracotta.hibernate.jpa.model.Order;
import demo.terracotta.hibernate.jpa.util.HibernateUtil;

public class AccountService {

	@SuppressWarnings("unchecked")
	public Account findAccountByName(Session session, String name) {
		Query query = session
				.createQuery("from Account as account where account.name=?");
		query.setParameter(0, name);
		query.setCacheable(true);
		List<Account> accounts = query.list();
		Account foundAccount = null;
		for (Account account : accounts) {
			foundAccount = account;
		}
		return foundAccount;
	}

	public Order finalizeOrder(String accountName, long orderID) {

		Session session = HibernateUtil.getSessionFactoryWithQueryCache()
				.openSession();
		Transaction tx = session.beginTransaction();
		tx.begin();
		Account account = findAccountByName(session, accountName);
		if (account == null) {
			account = new Account(accountName);
			account.setOrders(new ArrayList<Order>());
		}
		Order order = (Order) session.load(Order.class, orderID);
		order.setStatus("final");
		account.getOrders().add(order);
		session.saveOrUpdate(account);
		tx.commit();
		session.close();
		return order;
	}

	public List<Order> findOrdersByAccountName(long accountId) {

		Session session = HibernateUtil.getSessionFactoryWithQueryCache()
				.openSession();
		Transaction tx = session.beginTransaction();
		tx.begin();
		Account account = (Account) session.load(Account.class, accountId);
		List<Order> orders = account.getOrders();
		tx.commit();
		session.close();
		return orders;
	}
}
