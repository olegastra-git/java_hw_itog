package ru.geekbrains.lesson4;

private class HashMapIterator implements Iterator<Entity<K, V>> {

    private int currentIndex = 0;
    private Node<K, V> currentNode = null;
    private boolean nextCalled = false;

    @Override
    public boolean hasNext() {
        int bucketIndex = currentIndex / buckets.length;
        while (currentIndex < buckets.length && (buckets[bucketIndex] == null || buckets[bucketIndex].head == null)) {
            currentIndex++;
            bucketIndex = currentIndex / buckets.length;
        }

        if (currentIndex < buckets.length) {
            return true;
        }
        return false;
    }

    @Override
    public Entity<K, V> next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }

        nextCalled = true;
        if (currentNode == null || currentNode.next == null) {
            int bucketIndex = currentIndex / buckets.length;
            currentNode = buckets[bucketIndex].head;
            currentIndex++;
        } else {
            currentNode = currentNode.next;
        }

        return new Entity<>(currentNode.key, currentNode.value);
    }

    @Override
    public void remove() {
        if (!nextCalled) {
            throw new IllegalStateException("next() method must be called before remove()");
        }

        int bucketIndex = (currentIndex - 1) / buckets.length;
        if (currentNode == buckets[bucketIndex].head) {
            buckets[bucketIndex].head = currentNode.next;
        } else {
            Node<K, V> prev = buckets[bucketIndex].head;
            while (prev.next != currentNode) {
                prev = prev.next;
            }
            prev.next = currentNode.next;
        }

        currentNode = null;
        nextCalled = false;
        size--;
    }
}