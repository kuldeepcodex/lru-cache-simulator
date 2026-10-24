import java.util.HashMap;
import java.util.Map;

/**
 * LRU cache with O(1) get and put.
 * Doubly linked list (front = most recently used) + HashMap from key to node.
 */
public class LRUCache {

    // One node = one key-value pair, linked to its neighbours.
    private static class Node {
        int key, value;
        Node prev, next;
        Node(int key, int value) { this.key = key; this.value = value; }
    }

    private final int capacity;
    private final Map<Integer, Node> cacheMap = new HashMap<>();
    private final Node head = new Node(0, 0); // dummy: head.next is the MRU item
    private final Node tail = new Node(0, 0); // dummy: tail.prev is the LRU item
    private int hits, misses;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        head.next = tail;
        tail.prev = head;
    }

    // Returns the value, or -1 on a cache miss.
    public int get(int key) {
        Node node = cacheMap.get(key);
        if (node == null) {          // miss
            misses++;
            return -1;
        }
        hits++;                      // hit
        moveToFront(node);
        return node.value;
    }

    // Inserts or updates. Evicts the LRU item if the cache is full.
    public void put(int key, int value) {
        if (capacity <= 0) return;   // nothing can be stored

        Node node = cacheMap.get(key);
        if (node != null) {          // key exists: update and refresh
            node.value = value;
            moveToFront(node);
            return;
        }

        if (cacheMap.size() == capacity) {   // full: evict LRU
            Node lru = tail.prev;
            removeNode(lru);
            cacheMap.remove(lru.key);
        }

        Node fresh = new Node(key, value);
        addToFront(fresh);
        cacheMap.put(key, fresh);
    }

    // ---- linked list helpers (all O(1)) ----
    private void removeNode(Node n) {
        n.prev.next = n.next;
        n.next.prev = n.prev;
    }

    private void addToFront(Node n) {
        n.next = head.next;
        n.prev = head;
        head.next.prev = n;
        head.next = n;
    }

    private void moveToFront(Node n) {   // Java's replacement for C++ splice
        removeNode(n);
        addToFront(n);
    }

    public void printCache() {
        StringBuilder sb = new StringBuilder("Cache (MRU ---> LRU) : ");
        for (Node n = head.next; n != tail; n = n.next) {
            sb.append('[').append(n.key).append(':').append(n.value).append("] ");
        }
        System.out.println(sb);
    }

    public int getHits()   { return hits; }
    public int getMisses() { return misses; }
}
