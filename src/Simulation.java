import java.util.Random;

public class Simulation {

    // Simulates a slow database / API call.
    static int fetchFromDatabase(int key) { return key * 10; }

    public static void main(String[] args) {
        final int CACHE_CAPACITY = 100;
        final int TOTAL_QUERIES  = 10000;
        final int KEY_RANGE      = 200;  // keys 0-199
        final int HOT_KEY_RANGE  = 50;   // popular keys 0-49

        LRUCache cache = new LRUCache(CACHE_CAPACITY);
        Random rng = new Random(42);     // fixed seed = repeatable results

        System.out.println("Starting LRU Cache Simulation...");
        System.out.println("Cache Capacity : " + CACHE_CAPACITY);
        System.out.println("Total Queries  : " + TOTAL_QUERIES);

        // Pre-populate the cache
        for (int i = 0; i < CACHE_CAPACITY; i++) {
            cache.put(i, fetchFromDatabase(i));
        }

        for (int i = 0; i < TOTAL_QUERIES; i++) {
            int key;
            if (rng.nextInt(100) < 80) {          // 80%: popular keys
                key = rng.nextInt(HOT_KEY_RANGE);
            } else {                              // 20%: any key
                key = rng.nextInt(KEY_RANGE);
            }

            if (cache.get(key) == -1) {           // miss: load and store
                cache.put(key, fetchFromDatabase(key));
            }

            if ((i + 1) % 2500 == 0) {            // occasional snapshot
                System.out.printf("After %5d queries -> ", i + 1);
                cache.printCache();
            }
        }

        int total = cache.getHits() + cache.getMisses();
        System.out.println("\n----- SIMULATION REPORT -----");
        System.out.println("Total Queries : " + total);
        System.out.println("Cache Hits    : " + cache.getHits());
        System.out.println("Cache Misses  : " + cache.getMisses());
        System.out.printf("Hit Ratio     : %.2f%%%n", 100.0 * cache.getHits() / total);
        System.out.printf("Miss Ratio    : %.2f%%%n", 100.0 * cache.getMisses() / total);
    }
}
