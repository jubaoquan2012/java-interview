package com.interview.javabinterview.data_structure;

/**
 * 总结
 * （1）LinkedHashMap继承自HashMap，具有HashMap的所有特性；
 *
 * （2）LinkedHashMap内部维护了一个双向链表存储所有的元素；
 *
 * （3）如果accessOrder为false，则可以按插入元素的顺序遍历元素；
 *
 * （4）如果accessOrder为true，则可以按访问元素的顺序遍历元素；
 *
 * （5）LinkedHashMap的实现非常精妙，很多方法都是在HashMap中留的钩子（Hook），直接实现这些Hook就可以实现对应的功能了，并不需要再重写put()等方法；
 *
 * （6）默认的LinkedHashMap并不会移除旧元素，如果需要移除旧元素，则需要重写removeEldestEntry()方法设定移除策略；
 *
 * （7）LinkedHashMap可以用来实现LRU缓存淘汰策略；
 *
 * @Author : baoquan.ju
 * @Date : 2019/12/19
 **/

import java.util.*;
import java.util.function.Consumer;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.io.IOException;

public class LinkedHashMap<K, V> extends HashMap<K, V> implements Map<K, V> {

    static class Entry<K, V> extends HashMap.Node<K, V> {

        LinkedHashMap.Entry<K, V> before, after;

        Entry(int hash, K key, V value, Node<K, V> next) {
            super(hash, key, value, next);
        }
    }

    private static final long serialVersionUID = 3801124242820219131L;

    transient LinkedHashMap.Entry<K, V> head;

    transient LinkedHashMap.Entry<K, V> tail;

    /** 是否按访问顺序排序 :false，说明双向链表是按插入顺序存储元素。 */
    final boolean accessOrder;

    // internal utilities

    // link at the end of list
    private void linkNodeLast(LinkedHashMap.Entry<K, V> p) {
        LinkedHashMap.Entry<K, V> last = tail;
        tail = p;
        if (last == null) {
            head = p;
        } else {
            p.before = last;
            last.after = p;
        }
    }

    // apply src's links to dst
    private void transferLinks(LinkedHashMap.Entry<K, V> src,
            LinkedHashMap.Entry<K, V> dst) {
        LinkedHashMap.Entry<K, V> b = dst.before = src.before;
        LinkedHashMap.Entry<K, V> a = dst.after = src.after;
        if (b == null) {
            head = dst;
        } else {
            b.after = dst;
        }
        if (a == null) {
            tail = dst;
        } else {
            a.before = dst;
        }
    }

    // overrides of HashMap hook methods

    void reinitialize() {
        super.reinitialize();
        head = tail = null;
    }

    Node<K, V> newNode(int hash, K key, V value, Node<K, V> e) {
        LinkedHashMap.Entry<K, V> p =
                new LinkedHashMap.Entry<K, V>(hash, key, value, e);
        linkNodeLast(p);
        return p;
    }

    Node<K, V> replacementNode(Node<K, V> p, Node<K, V> next) {
        LinkedHashMap.Entry<K, V> q = (LinkedHashMap.Entry<K, V>) p;
        LinkedHashMap.Entry<K, V> t =
                new LinkedHashMap.Entry<K, V>(q.hash, q.key, q.value, next);
        transferLinks(q, t);
        return t;
    }

    TreeNode<K, V> newTreeNode(int hash, K key, V value, Node<K, V> next) {
        TreeNode<K, V> p = new TreeNode<K, V>(hash, key, value, next);
        linkNodeLast(p);
        return p;
    }

    TreeNode<K, V> replacementTreeNode(Node<K, V> p, Node<K, V> next) {
        LinkedHashMap.Entry<K, V> q = (LinkedHashMap.Entry<K, V>) p;
        TreeNode<K, V> t = new TreeNode<K, V>(q.hash, q.key, q.value, next);
        transferLinks(q, t);
        return t;
    }

    void afterNodeRemoval(Node<K, V> e) { // unlink
        LinkedHashMap.Entry<K, V> p =
                (LinkedHashMap.Entry<K, V>) e, b = p.before, a = p.after;
        p.before = p.after = null;
        if (b == null) {
            head = a;
        } else {
            b.after = a;
        }
        if (a == null) {
            tail = b;
        } else {
            a.before = b;
        }
    }

    /**
     * evict，驱逐的意思。
     * <p>
     * （1）如果evict为true，且头节点不为空，且确定移除最老的元素，那么就调用HashMap.removeNode()把头节点移除
     * （这里的头节点是双向链表的头节点，而不是某个桶中的第一个元素）；
     * <p>
     * （2）HashMap.removeNode()从HashMap中把这个节点移除之后，会调用afterNodeRemoval()方法；
     * <p>
     * （3）afterNodeRemoval()方法在LinkedHashMap中也有实现，用来在移除元素后修改双向链表，见下文；
     * <p>
     * （4）默认removeEldestEntry()方法返回false，也就是不删除元素。
     *
     * @param evict
     */
    void afterNodeInsertion(boolean evict) { // possibly remove eldest
        LinkedHashMap.Entry<K, V> first;
        if (evict && (first = head) != null && removeEldestEntry(first)) {
            K key = first.key;
            removeNode(hash(key), key, null, false, true);
        }
    }

    /**
     * 在节点访问之后被调用，主要在put()已经存在的元素或get()时被调用，
     * 如果accessOrder为true，调用这个方法把访问到的节点移动到双向链表的末尾。
     * 1）如果accessOrder为true，并且访问的节点不是尾节点；
     * <p>
     * （2）从双向链表中移除访问的节点；
     * <p>
     * （3）把访问的节点加到双向链表的末尾；（末尾为最新访问的元素）
     *
     * @param e
     */
    void afterNodeAccess(Node<K, V> e) { // move node to last
        LinkedHashMap.Entry<K, V> last;
        if (accessOrder && (last = tail) != e) {
            LinkedHashMap.Entry<K, V> p =
                    (LinkedHashMap.Entry<K, V>) e, b = p.before, a = p.after;
            p.after = null;
            if (b == null) {
                head = a;
            } else {
                b.after = a;
            }
            if (a != null) {
                a.before = b;
            } else {
                last = b;
            }
            if (last == null) {
                head = p;
            } else {
                p.before = last;
                last.after = p;
            }
            tail = p;
            ++modCount;
        }
    }

    void internalWriteEntries(java.io.ObjectOutputStream s) throws IOException {
        for (LinkedHashMap.Entry<K, V> e = head; e != null; e = e.after) {
            s.writeObject(e.key);
            s.writeObject(e.value);
        }
    }

    public LinkedHashMap(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
        accessOrder = false;
    }

    public LinkedHashMap(int initialCapacity) {
        super(initialCapacity);
        accessOrder = false;
    }

    public LinkedHashMap() {
        super();
        accessOrder = false;
    }

    public LinkedHashMap(Map<? extends K, ? extends V> m) {
        super();
        accessOrder = false;
        putMapEntries(m, false);
    }

    public LinkedHashMap(int initialCapacity,
            float loadFactor,
            boolean accessOrder) {
        super(initialCapacity, loadFactor);
        this.accessOrder = accessOrder;
    }

    public boolean containsValue(Object value) {
        for (LinkedHashMap.Entry<K, V> e = head; e != null; e = e.after) {
            V v = e.value;
            if (v == value || (value != null && value.equals(v))) {
                return true;
            }
        }
        return false;
    }

    public V get(Object key) {
        Node<K, V> e;
        if ((e = getNode(hash(key), key)) == null) {
            return null;
        }
        if (accessOrder) {
            afterNodeAccess(e);
        }
        return e.value;
    }

    /**
     * {@inheritDoc}
     */
    public V getOrDefault(Object key, V defaultValue) {
        Node<K, V> e;
        if ((e = getNode(hash(key), key)) == null) {
            return defaultValue;
        }
        if (accessOrder) {
            afterNodeAccess(e);
        }
        return e.value;
    }

    /**
     * {@inheritDoc}
     */
    public void clear() {
        super.clear();
        head = tail = null;
    }

    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return false;
    }

    public Set<K> keySet() {
        Set<K> ks = keySet;
        if (ks == null) {
            ks = new LinkedHashMap.LinkedKeySet();
            keySet = ks;
        }
        return ks;
    }

    final class LinkedKeySet extends AbstractSet<K> {

        public final int size() { return size; }

        public final void clear() { LinkedHashMap.this.clear(); }

        public final Iterator<K> iterator() {
            return new LinkedHashMap.LinkedKeyIterator();
        }

        public final boolean contains(Object o) { return containsKey(o); }

        public final boolean remove(Object key) {
            return removeNode(hash(key), key, null, false, true) != null;
        }

        public final Spliterator<K> spliterator() {
            return Spliterators.spliterator(this, Spliterator.SIZED |
                    Spliterator.ORDERED |
                    Spliterator.DISTINCT);
        }

        public final void forEach(Consumer<? super K> action) {
            if (action == null) {
                throw new NullPointerException();
            }
            int mc = modCount;
            for (LinkedHashMap.Entry<K, V> e = head; e != null; e = e.after) {
                action.accept(e.key);
            }
            if (modCount != mc) {
                throw new ConcurrentModificationException();
            }
        }
    }

    public Collection<V> values() {
        Collection<V> vs = values;
        if (vs == null) {
            vs = new LinkedHashMap.LinkedValues();
            values = vs;
        }
        return vs;
    }

    final class LinkedValues extends AbstractCollection<V> {

        public final int size() { return size; }

        public final void clear() { LinkedHashMap.this.clear(); }

        public final Iterator<V> iterator() {
            return new LinkedHashMap.LinkedValueIterator();
        }

        public final boolean contains(Object o) { return containsValue(o); }

        public final Spliterator<V> spliterator() {
            return Spliterators.spliterator(this, Spliterator.SIZED |
                    Spliterator.ORDERED);
        }

        public final void forEach(Consumer<? super V> action) {
            if (action == null) {
                throw new NullPointerException();
            }
            int mc = modCount;
            for (LinkedHashMap.Entry<K, V> e = head; e != null; e = e.after) {
                action.accept(e.value);
            }
            if (modCount != mc) {
                throw new ConcurrentModificationException();
            }
        }
    }

    public Set<Map.Entry<K, V>> entrySet() {
        Set<Map.Entry<K, V>> es;
        return (es = entrySet) == null ? (entrySet = new LinkedHashMap.LinkedEntrySet()) : es;
    }

    final class LinkedEntrySet extends AbstractSet<Map.Entry<K, V>> {

        public final int size() { return size; }

        public final void clear() { LinkedHashMap.this.clear(); }

        public final Iterator<Map.Entry<K, V>> iterator() {
            return new LinkedHashMap.LinkedEntryIterator();
        }

        public final boolean contains(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            Map.Entry<?, ?> e = (Map.Entry<?, ?>) o;
            Object key = e.getKey();
            Node<K, V> candidate = getNode(hash(key), key);
            return candidate != null && candidate.equals(e);
        }

        public final boolean remove(Object o) {
            if (o instanceof Map.Entry) {
                Map.Entry<?, ?> e = (Map.Entry<?, ?>) o;
                Object key = e.getKey();
                Object value = e.getValue();
                return removeNode(hash(key), key, value, true, true) != null;
            }
            return false;
        }

        public final Spliterator<Map.Entry<K, V>> spliterator() {
            return Spliterators.spliterator(this, Spliterator.SIZED |
                    Spliterator.ORDERED |
                    Spliterator.DISTINCT);
        }

        public final void forEach(Consumer<? super Map.Entry<K, V>> action) {
            if (action == null) {
                throw new NullPointerException();
            }
            int mc = modCount;
            for (LinkedHashMap.Entry<K, V> e = head; e != null; e = e.after) {
                action.accept(e);
            }
            if (modCount != mc) {
                throw new ConcurrentModificationException();
            }
        }
    }

    // Map overrides

    public void forEach(BiConsumer<? super K, ? super V> action) {
        if (action == null) {
            throw new NullPointerException();
        }
        int mc = modCount;
        for (LinkedHashMap.Entry<K, V> e = head; e != null; e = e.after) {
            action.accept(e.key, e.value);
        }
        if (modCount != mc) {
            throw new ConcurrentModificationException();
        }
    }

    public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
        if (function == null) {
            throw new NullPointerException();
        }
        int mc = modCount;
        for (LinkedHashMap.Entry<K, V> e = head; e != null; e = e.after) {
            e.value = function.apply(e.key, e.value);
        }
        if (modCount != mc) {
            throw new ConcurrentModificationException();
        }
    }

    // Iterators

    abstract class LinkedHashIterator {

        LinkedHashMap.Entry<K, V> next;

        LinkedHashMap.Entry<K, V> current;

        int expectedModCount;

        LinkedHashIterator() {
            next = head;
            expectedModCount = modCount;
            current = null;
        }

        public final boolean hasNext() {
            return next != null;
        }

        final LinkedHashMap.Entry<K, V> nextNode() {
            LinkedHashMap.Entry<K, V> e = next;
            if (modCount != expectedModCount) {
                throw new ConcurrentModificationException();
            }
            if (e == null) {
                throw new NoSuchElementException();
            }
            current = e;
            next = e.after;
            return e;
        }

        public final void remove() {
            Node<K, V> p = current;
            if (p == null) {
                throw new IllegalStateException();
            }
            if (modCount != expectedModCount) {
                throw new ConcurrentModificationException();
            }
            current = null;
            K key = p.key;
            removeNode(hash(key), key, null, false, false);
            expectedModCount = modCount;
        }
    }

    final class LinkedKeyIterator extends LinkedHashIterator implements Iterator<K> {

        public final K next() { return nextNode().getKey(); }
    }

    final class LinkedValueIterator extends LinkedHashIterator implements Iterator<V> {

        public final V next() { return nextNode().value; }
    }

    final class LinkedEntryIterator extends LinkedHashMap.LinkedHashIterator
            implements Iterator<Map.Entry<K, V>> {

        public final Map.Entry<K, V> next() { return nextNode(); }
    }
}

