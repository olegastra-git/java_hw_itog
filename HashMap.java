package ru.geekbrains.lesson4;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class HashMap<K, V> implements Iterable<Entity<K, V>> {

    private static final int INIT_BUCKET_COUNT = 16;
    private static final double LOAD_FACTOR = 0.5;
    private int size = 0;
    private Bucket<K, V>[] buckets;

    public HashMap() {
        this.buckets = new Bucket[INIT_BUCKET_COUNT];
    }

    public void add(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }

        int hash = key.hashCode();
        int bucketIndex = hash % buckets.length;
        if (buckets[bucketIndex] == null) {
            buckets[bucketIndex] = new Bucket<>();
        }

        Node<K, V> current = buckets[bucketIndex].head;
        while (current != null) {
            if (current.key.equals(key)) {
                current.value = value;
                return;
            }
            current = current.next;
        }

        Node<K, V> newNode = new Node<>(key, value);
        newNode.next = buckets[bucketIndex].head;
        buckets[bucketIndex].head = newNode;
        size++;

        if ((double) size / buckets.length > LOAD_FACTOR) {
            resize();
        }
    }

    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }

        int hash = key.hashCode();
        int bucketIndex = hash % buckets.length;
        if (buckets[bucketIndex] != null) {
            Node<K, V> current = buckets[bucketIndex].head;
            while (current != null) {
                if (current.key.equals(key)) {
                    return current.value;
                }
                current = current.next;
            }
        }

        return null;
    }

    public void remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }

        int hash = key.hashCode();
        int bucketIndex = hash % buckets.length;
        if (buckets[bucketIndex] != null) {
            Node<K, V> current = buckets[bucketIndex].head;
            Node<K, V> prev = null;
            while (current != null) {
                if (current.key.equals(key)) {
                    if (prev == null) {
                        buckets[bucketIndex].head = current.next;
                    } else {
                        prev.next = current.next;
                    }
                    size--;
                    return;
                }
                prev = current;
                current = current.next;
            }
        }
    }

    public boolean containsKey(K key) {
        return get(key) != null;
    }

    @Override
    public Iterator<Entity<K, V>> iterator() {
        return new HashMapIterator();
    }

    private void resize() {
        int newBucketCount = buckets.length * 2;
        Bucket<K, V>[] newBuckets = new Bucket[newBucketCount];

        for (Bucket<K, V> bucket : buckets) {
            if (bucket != null) {
                Node<K, V> current = bucket.head;
                while (current != null) {
                    int hash = current.key.hashCode();
                    int newBucketIndex = hash % newBucketCount;
                    if (newBuckets[newBucketIndex] == null) {
                        newBuckets[newBucketIndex] = new Bucket<>();}
                    Node<K, V> newNode = new Node<>(current.key, current.value);
                    newNode.next = newBuckets[newBucketIndex].head;
                    newBuckets[newBucketIndex].head = newNode;
                    current = current.next;
                }
            }
        }

        buckets = newBuckets;
}
