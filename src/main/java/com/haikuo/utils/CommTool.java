package com.haikuo.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import com.haikuo.sky.annotation.Column;


public class CommTool {

	/**
	 * 获取一个实体中 @Column 这个注解的字段的值
	 * @param list
	 * @param clazz
	 * @return
	 */
	public static<T> List<Long> entity2Ids(Collection<T> list){
		if(list==null || list.isEmpty()){
			return null;
		}
		List<Long> ids = new ArrayList<Long>();
		T entity = list.iterator().next();
		Field[] fields = entity.getClass().getDeclaredFields();
		Field field = null;
		for(Field f : fields){
			Column id = f.getAnnotation(Column.class);
			if(id !=null){
				field = f;
				break;
			}
		}
		field.setAccessible(true);
		for(T t : list){
			try {
				ids.add(field.getLong(t));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return ids;
	}
	
	/**
	 * 获得一个集合中所有实体某个字段的值
	 * @param <V>
	 * @param list
	 * @param field
	 * @return
	 * @throws SecurityException 
	 * @throws NoSuchFieldException 
	 */
	public static<T, V> List<V> entity2Ids(Collection<T> list,String fieldName,Class<V> clazz) throws NoSuchFieldException, SecurityException{
		if(list==null || list.isEmpty()){
			return null;
		}
		List<V> ids = new ArrayList<V>();
		T next = list.iterator().next();
		Field field ;
		try{
			field= next.getClass().getField(fieldName);
		}catch(NoSuchFieldException e){
			field = next.getClass().getDeclaredField(fieldName);
		}
		
		if(field==null){
			return null;
		}
		field.setAccessible(true);
		for(T t : list){
			try {
				ids.add((V)field.get(t));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return ids;
	}
	
	/**
	 * 返回的数不含重合id
	 * @param list
	 * @param fieldName
	 * @param clazz
	 * @return
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 */
	public static<T, V> List<V> entity2UniqueIds(Collection<T> list,String fieldName,Class<V> clazz) throws NoSuchFieldException, SecurityException{
		if(list==null || list.isEmpty()){
			return null;
		}
		Set<V> ids = new HashSet<V>();
		T next = list.iterator().next();
		Field field ;
		try{
			field= next.getClass().getField(fieldName);
		}catch(NoSuchFieldException e){
			field = next.getClass().getDeclaredField(fieldName);
		}
		
		if(field==null){
			return null;
		}
		field.setAccessible(true);
		for(T t : list){
			try {
				ids.add((V)field.get(t));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		List<V> idList = new ArrayList<V>();
		if(ids.isEmpty()){
			return idList;
		}
		for(V v : ids){
			idList.add(v);
		}
		
		return idList;
	}
	
	public static<T, V> Map<V,T> entity2Map(Collection<T> list,String fieldName,Class<V> clazz) throws NoSuchFieldException, SecurityException{
		if(list==null || list.isEmpty()){
			return null;
		}
		Map<V,T> map = new HashMap<V,T>();
		T next = list.iterator().next();
		Field field ;
		try{
			field= next.getClass().getField(fieldName);
		}catch(NoSuchFieldException e){
			field = next.getClass().getDeclaredField(fieldName);
			field.setAccessible(true);
		}
		for(T t : list){
			try {
				map.put((V)field.get(t), t);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return map;
	}
	
	public static<T, V> Map<V,List<T>> entity2MapList(Collection<T> list,String fieldName,Class<V> clazz) throws NoSuchFieldException, SecurityException{
		if(list==null || list.isEmpty()){
			return null;
		}
		 Map<V,List<T>> map = new HashMap<V,List<T>>();
		 T next = list.iterator().next();
		Field field ;
		try{
			field= next.getClass().getField(fieldName);
		}catch(NoSuchFieldException e){
			field = next.getClass().getDeclaredField(fieldName);
			field.setAccessible(true);
		}
		for(T t : list){
			try {
				V key = (V) field.get(t);
				List<T> clist = map.get(key);
				if(clist==null){
					clist = new ArrayList<T>();
					map.put(key, clist);
				}
				clist.add(t);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return map;
	}
	
	/**
	 * 用 t 替换list中 索引未index的元素
	 * @param <T>
	 * @param list
	 * @param t
	 * @param index
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public static <T> List<T> replaceListItem(List<T> list,T t ,int index) throws InstantiationException, IllegalAccessException{
		if(t==null || index <0){
			return  null;
		}
		List<T> newList = null;
		if(list==null||list.isEmpty()){
			newList = new ArrayList<T>();
			for(int i=0;i<index+1;i++){
				if(i==index){
					newList.add(i,t);
				}else{
					newList.add(i, (T) t.getClass().newInstance());
				}
				
			}
			return newList;
		}
		
		int size = index+1;
		if(size<list.size()){
			size = list.size();
		}
		newList = new ArrayList<T>(list.size());
		for(int i=0;i<size;i++){
			if(i==index){
				newList.add(i,t);
			}else if(i<list.size()){
				newList.add(i, list.get(i));
			}else{
				newList.add(i, (T) t.getClass().newInstance());
			}
			
		}
		return newList;
	}
	
	/**
	 * 获得一个集合中一个随机实体
	 * @param list
	 * @return
	 */
	public static <T> T getRandomElement(List<T> list){
		if(list==null || list.isEmpty()){
			return null;
		}
		Random r = new Random();
		int n = r.nextInt(list.size());
		return list.get(n);
	}
	
}
