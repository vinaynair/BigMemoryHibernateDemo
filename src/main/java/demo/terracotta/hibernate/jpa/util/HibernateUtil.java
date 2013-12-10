package demo.terracotta.hibernate.jpa.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

	private static SessionFactory sessionFactoryToLoadData = null;
	private static SessionFactory sessionFactoryWithQueryCache = null;
	public static String configFolder = "remoteDerby";

	public static SessionFactory getSessionFactoryToSetupData() {
		if (sessionFactoryToLoadData == null)
			sessionFactoryToLoadData = new Configuration().configure(
					configFolder + "/hibernate.cfg.toload.xml")
					.buildSessionFactory();
		return sessionFactoryToLoadData;

	}

	public static SessionFactory getSessionFactoryWithQueryCache() {
		if (sessionFactoryWithQueryCache == null)
			sessionFactoryWithQueryCache = new Configuration().configure(
					configFolder + "/hibernate.cfg.withQueryCache.xml")
					.buildSessionFactory();
		return sessionFactoryWithQueryCache;
	}

}