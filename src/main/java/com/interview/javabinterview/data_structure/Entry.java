package com.interview.javabinterview.data_structure;

import java.util.Map;

public  class Entry<K,V> implements Map.Entry<K,V> {
    final K key;
    V value;
    Entry<K,V> next;

    protected Entry(K key, V value, Entry<K,V> next) {
        this.key =  key;
        this.value = value;
        this.next = next;
    }


    // Map.Entry Ops

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    public V setValue(V value) {
        if (value == null)
            throw new NullPointerException();

        V oldValue = this.value;
        this.value = value;
        return oldValue;
    }
}
