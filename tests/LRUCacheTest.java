public class LRUCacheTest {
    static int passed = 0, failed = 0;

    static void check(String name, boolean ok) {
        System.out.println((ok ? "  PASS : " : "  FAIL : ") + name);
        if (ok) passed++; else failed++;
    }

    static void header(String t) { System.out.println("\n--- " + t + " ---"); }

    public static void main(String[] args) {
        header("Test 1 : Basic put and get");
        LRUCache c = new LRUCache(3);
        c.put(1, 10); c.put(2, 20); c.put(3, 30);
        check("get key 1 returns 10", c.get(1) == 10);
        check("get key 2 returns 20", c.get(2) == 20);
        check("get key 3 returns 30", c.get(3) == 30);

        header("Test 2 : Cache miss returns -1");
        c = new LRUCache(3);
        c.put(1, 10);
        check("missing key 99 returns -1", c.get(99) == -1);
        check("missing key 0 returns -1", c.get(0) == -1);

        header("Test 3 : LRU eviction order");
        c = new LRUCache(3);
        c.put(1, 10); c.put(2, 20); c.put(3, 30); c.put(4, 40); // evicts 1
        check("key 1 evicted", c.get(1) == -1);
        check("key 2 exists", c.get(2) == 20);
        check("key 3 exists", c.get(3) == 30);
        check("key 4 inserted", c.get(4) == 40);

        header("Test 4 : Update existing key");
        c = new LRUCache(3);
        c.put(1, 10); c.put(2, 20); c.put(3, 30); c.put(1, 999);
        check("key 1 updated to 999", c.get(1) == 999);
        check("key 2 unchanged", c.get(2) == 20);
        check("key 3 unchanged", c.get(3) == 30);

        header("Test 5 : Re-access protects item from eviction");
        c = new LRUCache(3);
        c.put(1, 10); c.put(2, 20); c.put(3, 30);
        c.get(1);          // [1,3,2]
        c.put(4, 40);      // evicts 2
        check("key 1 protected", c.get(1) == 10);
        check("key 2 evicted", c.get(2) == -1);
        check("key 3 exists", c.get(3) == 30);
        check("key 4 inserted", c.get(4) == 40);

        header("Test 6 : Capacity 1");
        c = new LRUCache(1);
        c.put(1, 10);
        check("key 1 inserted", c.get(1) == 10);
        c.put(2, 20);
        check("key 1 evicted", c.get(1) == -1);
        check("key 2 inserted", c.get(2) == 20);
        c.put(3, 30);
        check("key 2 evicted", c.get(2) == -1);
        check("key 3 inserted", c.get(3) == 30);

        header("Test 7 : Hit and miss stats");
        c = new LRUCache(3);
        c.put(1, 10); c.put(2, 20);
        c.get(1); c.get(2); c.get(99); c.get(100); c.get(1);
        check("3 hits", c.getHits() == 3);
        check("2 misses", c.getMisses() == 2);

        header("Test 8 : Update moves item to front");
        c = new LRUCache(3);
        c.put(1, 10); c.put(2, 20); c.put(3, 30);
        c.put(1, 100);     // [1,3,2]
        c.put(4, 40);      // evicts 2
        check("key 1 updated and protected", c.get(1) == 100);
        check("key 2 evicted", c.get(2) == -1);
        check("key 3 exists", c.get(3) == 30);
        check("key 4 inserted", c.get(4) == 40);

        header("Test 9 : Capacity 0");
        c = new LRUCache(0);
        c.put(1, 10);
        check("put ignored on capacity 0", c.get(1) == -1);

        header("Test 10 : Miss does not change LRU order");
        c = new LRUCache(2);
        c.put(1, 10); c.put(2, 20);
        c.get(99);         // miss, order stays [2,1]
        c.put(3, 30);      // evicts 1
        check("key 1 evicted", c.get(1) == -1);
        check("key 2 exists", c.get(2) == 20);
        check("key 3 inserted", c.get(3) == 30);

        header("Test 11 : put() does not affect stats");
        c = new LRUCache(2);
        c.put(1, 10); c.put(1, 11); c.put(2, 20); c.put(3, 30);
        check("0 hits", c.getHits() == 0);
        check("0 misses", c.getMisses() == 0);

        System.out.println("\nPassed: " + passed + "  Failed: " + failed
                + "  Total: " + (passed + failed));
        System.exit(failed == 0 ? 0 : 1);
    }
}
