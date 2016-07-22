package com.haikuo.utiltools;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MapSortUtil {
	
    /**
     * hashMap 按value 倒序
     * @param map
     * @return
     */
    public static <K, V extends Comparable<? super V>> Map<K, V> sorthashMapByValue( Map<K, V> map ){  
       List<Map.Entry<K, V>> list =  new LinkedList<Map.Entry<K, V>>( map.entrySet() );  
       Collections.sort( list, new Comparator<Map.Entry<K, V>>()  
       {  
           public int compare( Map.Entry<K, V> o1, Map.Entry<K, V> o2 )  
           {  
               return (o2.getValue()).compareTo( o1.getValue() );  
           }  
       } );  
 
       Map<K, V> result = new LinkedHashMap<K, V>();  
       for (Map.Entry<K, V> entry : list)  
       {  
           result.put( entry.getKey(), entry.getValue() );  
       }  
       return result;  
   }
}
