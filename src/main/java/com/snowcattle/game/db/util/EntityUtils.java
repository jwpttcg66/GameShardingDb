package com.snowcattle.game.db.util;

import com.snowcattle.game.db.cache.redis.RedisInterface;
import com.snowcattle.game.db.common.annotation.FieldSave;
import com.snowcattle.game.db.entity.IEntity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jwp on 2017/3/22.
 * 实体辅助类
 */
public class EntityUtils {

    /**
     * 获取所有缓存的字段跟值
     * @param iEntity
     * @return
     */
    public static Map<String, String> getCacheValueMap(IEntity iEntity){
        Map<String, String> map = new HashMap<>();
        Field[] fields = getAllCacheFields(iEntity);
        for(Field field: fields){
            String fieldName = field.getName();
            String value = ObjectUtils.getFieldsValueStr(iEntity, fieldName);
            map.put(fieldName, value);
        }
        return map;
    }

    /**
     * 获取所有缓存的字段field
     * @param obj
     * @return
     */
    public static Field[] getAllCacheFields(IEntity obj){
        Class<?> clazz = obj.getClass();
        List<Field> fieldList = new ArrayList<>();
        for(;clazz!=Object.class;clazz=clazz.getSuperclass()){
            Field[] fields = clazz.getDeclaredFields();
            for(Field field: fields){
                //获取filed注解
                FieldSave fieldSave = field.getAnnotation(FieldSave.class);
                if(fieldSave != null) {
                    fieldList.add(field);
                }
            }
        }
        return fieldList.toArray(new Field[0]);
    }

    //获取rediskey
    public static String getRedisKey(RedisInterface redisInterface){
        return redisInterface.getRedisKeyEnumString() + redisInterface.getUniqueKey();
    }
}
