package com.snowcattle.game.db.cache.redis;



import java.util.Map;

/**
 * 获取所有需import java.util.Map;
必须为"uid"
 * 2、po的所有需要保存的属性必须和数据库里的属性命名一致
 */
public interface RedisInterface {
	public Map<String,String> getAllFeildsToHash();
	public String getUniqueKey();
	public int getFieldLength();
	public RedisKeyEnum getRedisKeyEnum();
}

