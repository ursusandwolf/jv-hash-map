package core.basesyntax;

public class MyHashMap<K, V> implements MyMap<K, V> {
    private static final int DEFAULT_CAPACITY = 16;
    private static final float LOAD_FACTOR = 0.75f;

    private Entry<K, V>[] table;
    private int size = 0;

    @SuppressWarnings("unchecked")
    public MyHashMap() {
        table = new Entry[DEFAULT_CAPACITY];
    }

    @Override
    public void put(K key, V value) {
        int index = index(key); // calc bucket index
        Entry<K,V> entry = table[index]; // first element in list
        while (entry != null) {
            if (keyEquals(entry.key, key)) {
                entry.value = value; //rewrite value
                return; //exit, size will not change
            }
            entry = entry.next; //go to next element
        }
        // So, if key was not found, we need to create new entry
        Entry<K,V> newEntry = new Entry<>(key, value);
        newEntry.next = table[index]; // insert new element in begin of chain
        table[index] = newEntry; //new link of the head list to the new entry
        size++;

        if (size >= table.length * LOAD_FACTOR) {
            resize();
        }

    }

    @Override
    public V getValue(K key) {
        int index = index(key);
        Entry<K, V> entry = table[index];
        while (entry != null) {
            if (keyEquals(entry.key, key)) {
                return entry.value;
            }
            entry = entry.next;
        }
        return null;
    }

    @Override
    public int getSize() {
        return 0;
    }

    private void resize() {
    }

    private boolean keyEquals(K key, K key1) {
        return key == key1 || (key != null && key.equals(key1));
    }

    private int index(K key) {
        if (key == null) {
            return 0;
        }
        return Math.abs(key.hashCode()) % table.length;
    }

    static class Entry<K, V> {
        final K key;
        V value;
        Entry<K, V> next;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

}
