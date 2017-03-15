package com.snowcattle.game.db.util;


import com.alibaba.fastjson.JSON;
import com.snowcattle.game.db.common.Loggers;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jwp on 2017/2/28.
 */
public class JsonUtils {

    /** 日志 */
    public static final Logger logger = Loggers.dbLogger;

    /** 默认字符串 */
    public final static String DEFAULT_STRING = "";

    /** 默认Long */
    private final static long DEFAULT_LONG = 0l;

    /** 默认Int {@value} */
    public final static int DEFAULT_INT = 0;

    /** 默认Float */
    public final static float DEFAULT_FLOAT = 0f;

    /** 默认Double */
    public final static double DEFAULT_DOUBLE = 0d;

    /** 默认Boolean */
    public final static boolean DEFAULT_BOOLEAN = false;

    /**
     * 获取json字符串
     * @param map
     * @return
     */
    public static String getJsonStr(Map<String, String> map){
        return JSON.toJSONString(map);
    }

    @SuppressWarnings("unchecked")
    public static Map<String,String> getMapFromJson(String json){
        return JSON.parseObject(json, Map.class);
    }

    /**
     *
     * @param jsonStr	必须是原始的 Map<Integer, List<Integer>> 转化的串
     * @return
     */
    public static Map<Integer, List<Integer>> getMapObject(String jsonStr) {

        Map<Integer, List<Integer>> map = new HashMap<Integer, List<Integer>>();
        if(null == jsonStr || StringUtils.isEmpty(jsonStr)) {
            return map;
        }
//        JSONArray jsonArr = JSONArray.fromObject(jsonStr);
//
//        String subObjStr = null;
//        JSONObject subJsonObj = null;
//
//        int key = 0;
//        List<Integer> mapValueList = null;
//
//        if(null != jsonArr && !jsonArr.isEmpty()) {
//            for(int i = 0; i < jsonArr.size(); i++) {
//                subObjStr = jsonArr.getString(i);
//                if(null != subObjStr && !StringUtils.isEmpty(subObjStr)) {
//                    subJsonObj = JSONObject.fromObject(subObjStr);
//
//                    key = Integer.parseInt(subJsonObj.getString("key"));
//                    mapValueList = JsonUtils.getListInteger(subJsonObj.getString("value"));
//
//                    map.put(key, mapValueList);
//                }
//            }
//        }

        return map;
    }

}

