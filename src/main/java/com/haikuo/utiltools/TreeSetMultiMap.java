package com.haikuo.utiltools;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;
import java.util.Map.Entry;

public class TreeSetMultiMap<T,I> {
	private Map<T, TreeSet<I>> map = new HashMap<T, TreeSet<I>>();
	
	public static <T,I> TreeSetMultiMap<T, I> getInstance(){
		return new TreeSetMultiMap<T,I>();
	}
	
	public void put(T key,I value){
		if(key != null && value != null){
			TreeSet<I> list = map.get(key);
			if(list == null){
				list = new TreeSet<I>();
				map.put(key, list);
			}
			list.add(value);
		}
	}

	public Map<T, TreeSet<I>> asMap(){
		return map;
	}
	
	public static void main(String[] args) {
		TreeSetMultiMap<String, Long> multiMap = TreeSetMultiMap.getInstance();
		multiMap.put("A", 100L);
		multiMap.put("A", 102L);
		multiMap.put("A", 101L);
		multiMap.put("A", 102L);
		multiMap.put("B", 107L);
		multiMap.put("B", 108L);
		
		Map<String, TreeSet<Long>> letterBrandMap =  multiMap.asMap();
		for(Entry<String, TreeSet<Long>> entry: letterBrandMap.entrySet()){
			System.err.println("=============================");
			if(entry == null || entry.getValue() == null){
				continue;
			}
			System.err.println(entry.getKey());
			for(Long id : entry.getValue()){
				System.err.println(id);
			}
		}
	}
}
