package com.haikuo.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by liuhaikuo on 2017/1/19.
 */
public class PropertiesUtil<T> {



    /**
     * 把t1的属性copy到t2 属性名相同,类型相同,并且属性非final修饰,空值不覆盖
     * @param source 被copy的对象
     * @param target copy的目标对象
     * @param <T>
     * @return
     */
    public  static  <T> void copy(T source ,T target)throws Exception{
        copy(source,target,false);
    }

    /**
     * 把t1的属性copy到t2 属性名相同,类型相同,并且属性非final修饰
     * @param source 被copy的对象
     * @param target copy的目标对象
     * @param isNullAble true 空值允许复制  false 不允许
     * @param <T>
     * @return
     */
    public  static  <T> void copy(T source ,T target,boolean isNullAble)throws Exception{
        Class clz = source.getClass();
        Field[] fields =clz.getDeclaredFields();

        Class clzT = target.getClass();
        Field[] targetfields = clzT.getDeclaredFields();

        Map<String,Field> targetFieldsMap = new HashMap<String ,Field>();
        for(Field f : targetfields){
            targetFieldsMap.put(f.getName(),f);
        }

        for (Field f : fields){
            String modify =  Modifier.toString(f.getModifiers());
            f.setAccessible(true);
            Object val = f.get(source);
            if(modify.indexOf("final")<0&&(((val==null||val.equals(""))&&isNullAble)
                    ||(val!=null&&!val.equals(""))
                    )){//final类型排除
//                f.get 仅能获取 public 属性的值 获取private会报错
//                Object o = f.get(invoice);
//               若要使用 ,需要设置  setAccessible(true)
                Field tmp = targetFieldsMap.get(f.getName());
                if(tmp!=null&& f.getType().equals(tmp.getType())){
                    tmp.setAccessible(true);
                    tmp.set(target,val);
                }

            }
        }
    }

}
