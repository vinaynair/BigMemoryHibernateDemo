package demo.terracotta.hibernate.ehcache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * simple app that puts key,value into a cache named "cacheOne"
 * 
 * @author vch
 * 
 */
public class Put {
	public static void main(String[] args) throws Exception {
		Put app = new Put();
		app.getUserInput();
	}

	// ==========
	private CacheManager cacheManager;
	private Cache cache;

	public Put() {
		cacheManager = CacheManager.getInstance();
		cache = cacheManager.getCache("cacheOne");
	}

	public void getUserInput() {
		Scanner scanner = new Scanner(System.in);
		String MSG = "Enter key,value to be put in the cache";
		System.out.println(MSG);
		while (scanner.hasNext()) {
			String str = scanner.nextLine();
			StringTokenizer tokens = new StringTokenizer(str, ",");
			String key = tokens.nextToken();
			if ("QUIT".equals(key)) {
				System.out.println("Exiting");
				break;
			}
			String value = tokens.nextToken();
			// put the key,value pair into the cache
			cache.put(new Element(key, value));
			System.out.println("Put complete\n" + MSG);
		}
	}
}
