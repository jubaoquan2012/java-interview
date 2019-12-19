package com.interview.javabinterview.collection;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class CollectionDemo {
    public static void main(String[] args) {
        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("","");

        Hashtable<Object, Object> hashtable = new Hashtable<>(10);
        hashtable.put("","");

        List<Integer> arrayList = new ArrayList<>();
        arrayList.add(1);

        List<Object> linkedList = new LinkedList<>();
        linkedList.add(1);

        Set<String> hashSet = new HashSet<>();
        hashSet.add("");

        ConcurrentHashMap<Object,Object> concurrentHashMap = new ConcurrentHashMap<>();
        concurrentHashMap.put("","");

        System.out.println(15 % 4);
        System.out.println(15 & (4-1));
    }
}
