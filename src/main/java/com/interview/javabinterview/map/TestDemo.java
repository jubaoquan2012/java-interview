package com.interview.javabinterview.map;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;

public class TestDemo<K,V>  {
    public static void main(String[] args) {
        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("","");
        Hashtable<Object, Object> hashtable = new Hashtable<>(10);
        for (int i = 0; i <1000; i++) {
            hashtable.put(i,i);
        }
        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.add(1);
        LinkedList<Object> linkedList = new LinkedList<>();
        linkedList.add(1);
        HashSet<String> hashSet = new HashSet<>();
        System.out.println();
        ConcurrentHashMap<Object,Object> c = new ConcurrentHashMap<>();
        c.put("","");

    }

    public void put(String k,String value){
        Node<String, String> kvNode = new Node<String, String>(k,value,null);
    }

    Node<K,V>[] tab;

    static class Node<K,V> implements Map.Entry<K,V> {

        final K key;
        volatile V val;
        volatile Node<K,V> next;

        Node( K key, V val, Node<K,V> next) {
            this.key = key;
            this.val = val;
            this.next = next;
        }

        public final K getKey()       { return key; }
        public final V getValue()     { return val; }
        public final int hashCode()   { return key.hashCode() ^ val.hashCode(); }
        public final String toString(){ return key + "=" + val; }
        public final V setValue(V value) {
            throw new UnsupportedOperationException();
        }

        public final boolean equals(Object o) {
            Object k, v, u; Map.Entry<?,?> e;
            return ((o instanceof Map.Entry) &&
                    (k = (e = (Map.Entry<?,?>)o).getKey()) != null &&
                    (v = e.getValue()) != null &&
                    (k == key || k.equals(key)) &&
                    (v == (u = val) || v.equals(u)));
        }
    }
}
