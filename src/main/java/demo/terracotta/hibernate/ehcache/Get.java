package demo.terracotta.hibernate.ehcache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import java.util.Scanner;

/**
 * simple app that given a key gets the corresponding value from a cache named
 * "cacheOne"
 * 
 * @author vch
 * 
 */
public class Get {
	public static void main(String[] args) throws Exception {
		Get app = new Get();
		app.getUserInput();
	}

	// ==========
	private CacheManager cacheManager;
	private Cache cache;

	public Get() {
		cacheManager = CacheManager.getInstance();
		cache = cacheManager.getCache("cacheOne");
	}

	public void getUserInput() {
		Scanner scanner = new Scanner(System.in);
		String MSG = "Enter key to be fetched from cache";
		System.out.println(MSG);
		while (scanner.hasNext()) {
			String key = scanner.nextLine();
			//
			if ("QUIT".equals(key)) {
				System.out.println("Exiting");
				break;
			}
			// read from cache using the provided key
			Element element = cache.get(key);
			if (element == null) {
				System.out.println("No value found in cache\n" + MSG);
			} else {
				System.out.println("Got value=" + element.getObjectValue()
						+ "\n" + MSG);
			}
		}
	}
}
