package com.haikuo.utiltools;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;


public class JSONTool {

	/**
	 * 把传入实体转换成一个json 
	 * @param t
	 * @param mappingMap  对应规则 key json中的key  value 对应实体中的字段名
	 * @return
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static <T> JSONObject crateJSON(T t ,Map<String,String> mappingMap) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
		if(t==null){
			return null;
		}
		if(mappingMap==null||mappingMap.isEmpty()){
			return (JSONObject) JSON.toJSON(t);
		}
		JSONObject json = new JSONObject();
		for(String key :mappingMap.keySet()){
			String value = mappingMap.get(key);
			Field f = t.getClass().getDeclaredField(value);
			f.setAccessible(true);
			Object object = f.get(t);
			json.put(key, object);
		}
		return json;
	}
	
	/**
	 * 把传入集合转换成一个JSONArray
	 * @param collections
	* @param mappingMap  对应规则 key json中的key  value 对应实体中的字段名
	 * @return
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static <T> JSONArray crateJSON(Collection<T> collections ,Map<String,String> mappingMap) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
		if(collections==null||collections.isEmpty()){
			return null;
		}
		if(mappingMap==null||mappingMap.isEmpty()){
			return (JSONArray) JSON.toJSON(collections);
		}
		JSONArray array = new JSONArray();
		for(T t :collections){
			array.add(crateJSON(t,mappingMap));
		}
		return array;
	}
}
