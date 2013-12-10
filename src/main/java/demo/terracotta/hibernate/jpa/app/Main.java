package demo.terracotta.hibernate.jpa.app;

import demo.terracotta.hibernate.jpa.model.Order;
import demo.terracotta.hibernate.jpa.service.AccountService;
import demo.terracotta.hibernate.jpa.service.CartService;
import demo.terracotta.hibernate.jpa.service.OrderProcessingService;
import demo.terracotta.hibernate.jpa.util.HibernateUtil;
import org.hibernate.Session;

import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * 
 * @author vch
 * 
 */
public class Main {
	private static String MSG = "Enter " + "[clean]/"
			+ "[addToCart user product]/" + "[getAllOrders user]/"
			+ "[getAllOrdersByUserID userIDFromDatabase] / "
			+ "[getOrdersForAccount accountIDFromDatabase] /"
			+ "[setOrderStatus orderIDFromDatabase newStatusAsString]";

	public static void main(String[] args) {
		OrderProcessingService orderProcessingService = new OrderProcessingService();
		CartService cartService = new CartService();
		AccountService accountService = new AccountService();

		while (userHasMoreInput()) {

			if ("clean".equals(COMMAND)) {
				// simply bring up the session factory that is going to recreate
				// the schema
				Session session = HibernateUtil.getSessionFactoryToSetupData()
						.openSession();
				session.close();

			} else if ("addToCart".equals(COMMAND)) {

				String userName = nextToken();
				String productName = nextToken();
				Order order = cartService.addOrder(userName, productName);
				System.out.println("Added order " + order + " to user named "
						+ userName);

			} else if ("getAllOrders".equals(COMMAND)) {
				String userName = nextToken();
				List<Order> orders = orderProcessingService
						.findAllOrdersByUserName(userName);
				System.out.println("user " + userName
						+ " has the following orders");
				if(orders!=null)
				for (Order order : orders) {
					System.out.println("--" + order);
				}

			} else if ("getAllOrdersByUserID".equals(COMMAND)) {
				long userID = Long.parseLong(nextToken());
				List<Order> orders = orderProcessingService
						.findAllOrdersByUserID(userID);
				System.out.println("user " + userID
						+ " has the following orders");
				if(orders!=null)
				for (Order order : orders) {
					System.out.println("--" + order);
				}
			} else if ("finalizeOrder".equals(COMMAND)) {
				String userName = nextToken();
				long orderID = Long.parseLong(nextToken());
				Order order = accountService.finalizeOrder(userName, orderID);
				System.out.println("Finlized order " + order
						+ " for user named " + userName);

			} else if ("getAllOrdersForAccount".equals(COMMAND)) {
				long accountId = Long.parseLong(nextToken());
				List<Order> orders = accountService
						.findOrdersByAccountName(accountId);
				System.out.println("user " + accountId
						+ " has the following orders");
				if(orders!=null)
				for (Order order : orders) {
					System.out.println("--" + order);
				}

			} else if ("setOrderStatus".equals(COMMAND)) {
				long orderId = Long.parseLong(nextToken());
				String status = nextToken();
				Order order = orderProcessingService.changeOrderStatus(orderId,
						status);
				System.out.println("changed order " + order);

			} else if ("quit".equals(COMMAND)) {
				break;
			}

		}

	}

	private static String COMMAND;
	private static StringTokenizer TOKENS = null;
	private static Scanner SCANNER = new Scanner(System.in);

	public static boolean userHasMoreInput() {
		System.out.println(MSG);
		String str = SCANNER.nextLine();
		TOKENS = new StringTokenizer(str, " ");
		COMMAND = "";// do nothing
		if (TOKENS.hasMoreTokens())
			COMMAND = TOKENS.nextToken();
		return true;

	}

	public static String nextToken() {
		return TOKENS.nextToken();
	}
}
