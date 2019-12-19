package com.interview.javabinterview.collection;

import java.util.*;
import java.util.LinkedHashMap;
import java.util.concurrent.ConcurrentHashMap;

public class CollectionDemo {
    public static void main(String[] args) {
        new java.util.HashMap<>();
        new LinkedHashMap<>();
        Map<Object, Object> hashMap = new HashMap<>();
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
    }
}
