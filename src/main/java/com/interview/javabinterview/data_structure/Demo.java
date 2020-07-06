package com.interview.javabinterview.data_structure;

import java.util.*;

/**
 * 类
 *
 * @author Ju Baoquan
 * Created at  2020/5/18
 */
public class Demo {

    public static void main(String[] args) {

        /**List*/
        ArrayList<Object> arrayList = new ArrayList<>();
        arrayList.add("");

        LinkedList<Object> linkedList = new LinkedList<>();
        linkedList.add("");
        linkedList.add(1, "");
        linkedList.addFirst("");
        linkedList.addLast("");
        linkedList.get(1);
        linkedList.getFirst();
        linkedList.getLast();
        linkedList.offerFirst("");

        /**Map*/
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("", "");

        Hashtable<Object, Object> hashtable = new Hashtable<>();
        hashtable.put(null, "");

        TreeMap<String, String> treeMap = new TreeMap<>();
        treeMap.put("", "");


        LinkedHashMap<String, String> linkedHashMap = new LinkedHashMap<>();
        linkedHashMap.put("","");

        /**Set*/
        HashSet<Object> hashSet = new HashSet<>();
        hashSet.add("");

        LinkedHashSet<Object> linkedHashSet = new LinkedHashSet<>();
        linkedHashSet.add("");


        TreeSet<Object> treeSet = new TreeSet<>();
        treeSet.add("");

    }
}
