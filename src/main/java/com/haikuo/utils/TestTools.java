package com.haikuo.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class TestTools {
	private static Log logger = LogFactory.getLog(TestTools.class);
	
	/**
	 * 答应对象内容
	 * **/
	public static <T> void printList(List<T> list,Printer printer){
		if(list != null && !list.isEmpty()){
			for(T t : list){
				printer.print(t);
			}
		}
	}

	
	/**
	 * 答应对象内容
	 * **/
	public static <T> void printList(List<T> list){
		if(list != null && !list.isEmpty()){
			System.err.println("size：" + list.size());
			try {
				for(T t : list){
					ObjecPrinter.print(t);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 答应对象内容
	 * **/
	public static <T> void printObject(T t){
		try {
			ObjecPrinter.print(t);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	static interface Printer{
		public <T> void print(T t);
	}
	
	
	static class ObjecPrinter{
		public static void print(Object t) throws Exception {
			if(t != null){
				Class clazz = t.getClass();
				Field[] fields = clazz.getDeclaredFields();
				System.out.println("----------------------------------");
				System.out.println("name\tvalue");  
				for(Field field : fields){
					  Object v = invokeMethod(t, field.getName(), null);  
					  System.out.println(field.getName() + "\t" + v);  
//			          System.out.println(field.getName() + "\t" + v + "\t" + field.getType());  
				}
			}
		}
		
	}
	
	private static Object invokeMethod(Object owner, String methodName,  
	            Object[] args) throws Exception {  
	        Class ownerClass = owner.getClass();  
	        methodName = methodName.substring(0, 1).toUpperCase()  
	                + methodName.substring(1);  
	        Method method = null;  
	        try {  
	            method = ownerClass.getMethod("get" + methodName);  
	        } catch (SecurityException e) {  
	        } catch (NoSuchMethodException e) {  
	            return " can't find 'get" + methodName + "' method";  
	        }  
	        return method.invoke(owner);  
	    }  
	
	public static void printSQL(StringBuilder builder,Log logger){
		if(builder != null){
			logger.debug("sql:"+builder.toString());
		}else{
			logger.debug("猿尼玛，用空语句去哄弄海豚！！～～,小心它咬你( ⊙ o ⊙ )！");
		}
	}
	
	public static void printSQL(String sql){
		if(sql != null){
			logger.debug("sql:"+sql);
		}else{
			logger.debug("猿尼玛，用空语句去哄弄海豚！！～～,小心它咬你( ⊙ o ⊙ )！");
		}
	}
	
	public static void printSQLConsole(String sql){
		if(sql != null){
			System.out.println(sql);
		}else{
			System.out.println("猿尼玛，用空语句去哄弄海豚！！～～,小心它咬你( ⊙ o ⊙ )！");
		}
	}
	
	public static long takeTime(long start){
		return System.currentTimeMillis() - start;
	}
}


