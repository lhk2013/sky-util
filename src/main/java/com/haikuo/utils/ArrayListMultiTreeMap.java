package com.haikuo.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ArrayListMultiTreeMap<T, I>  {
	private Map<T, List<I>> map = new TreeMap<T, List<I>>();
	
	public static <T,I> ArrayListMultiTreeMap<T, I> getInstance(){
		return new ArrayListMultiTreeMap<T, I>();
	}
	
	public void put(T key,I value){
		if(key != null && value != null){
			List<I> list = map.get(key);
			if(list == null){
				list = new ArrayList<I>();
				map.put(key, list);
			}
			list.add(value);
		}
	}

	public Map<T, List<I>> asMap(){
		return map;
	}
}
