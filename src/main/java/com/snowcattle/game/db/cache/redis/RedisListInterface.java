package com.snowcattle.game.db.cache.redis;



/**
 * 列表类型的缓存对象
 * 
 */
public interface RedisListInterface extends RedisInterface{
	/**
	 * 列表对象的子唯一主键属性
	 */
	public String getSubUniqueKey();
}
