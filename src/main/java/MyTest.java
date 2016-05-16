import com.babytree.cache.memcached.MemcachedClient;

public class MyTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			MemcachedClient mem = MemcachedClient.getInstense();
			String key = "key_";
			String value = "value";
			for (int i=0; i<100; i++) {
				key = key + String.valueOf(i);
				value = value + String.valueOf(i);
				mem.set(key, value);
				String s = mem.get(key);
				System.out.println(key + ":" + s);
				key = "key_";
			}
		} catch (Exception e) {
			
		}
	}

}
