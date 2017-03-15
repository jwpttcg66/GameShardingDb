package com.snowcattle.game.db.cache.redis;



/**
 * 缓存键值
 */
public enum RedisKeyEnum {
	
	PLAYER("wp#"),
	ITEM("wi#"),
	MAIL("wm#"),
	MAIL_PAGE("wmp#"),
	
	ONLINE("wol#"),
	ONLINE_STATUS("wols#"),
	ONLINE_MSGQUE("wolmq#"),
	ONLINE_ROOMID_INC("wolri#"),
	ONLINE_DOUBLES_MATCH_QUEUE("woldmq#"),
	ONLINE_DOUBLES_MATCH_LOCK("woldmlk#"),
	ONLINE_MULTI_JOIN_LOCK("woldmjlk#"),
	ONLINE_DODGE_DOUBLES_MATCH_QUEUE("wdoldmq#"),

	//双打匹配队列
	ONLINE_SINGLE_TEAM_PK_MATCH_QUEUE("wdolstpmq#"),
	//双打匹配队列随机成员集合
	ONLINE_SINGLE_TEAM_PK_MATCH_SET("wdolstpms#"),
//	//双打匹配队列随机成员集合
//	ONLINE_SINGLE_TEAM_PK_MATCH_TIME_OUT_JOIN_QUEUE("wdolstpmtojq#"),
	//双打匹配队列超时成员集合
	ONLINE_SINGLE_TEAM_PK_MATCH_TIME_OUT_JOIN_ZSET("wdolstpmtojs#"),
	//双打匹配队列
	ONLINE_DOUBLE_TEAM_PK_MATCH_QUEUE("wdoldtpmq#"),
	//双打匹配队列随机成员集合
	ONLINE_DOUBLE_TEAM_PK_MATCH_SET("wdoldtpms#"),
//	//双打匹配队列随机成员集合
//	ONLINE_DOUBLE_TEAM_PK_MATCH_TIME_OUT_JOIN_QUEUE("wdoltdpmtojq#"),
	//双打匹配队列超时成员集合
	ONLINE_DOUBLE_TEAM_PK_MATCH_TIME_OUT_JOIN_ZSET("wdoldtpmtojs#"),

	SERVER_SESSIONS("gss#"),
	ROOM("groom#"),
	PLAYER_CHECK("gpc#"),
	PRE_ROOM("gpr#"),
	/*世界转发的聊天内容 这个比较特殊需要跟a5里面一致*/
	SYSTEM_CHAT_CONTENT("SYC"),

	/**
	 * 公会聊天 公会内容
	 */
	ONLINE_TONG_CHAT_INC("OTCI"),
	/**
	 * 公会聊天集合
	 */
	ONLINE_TONG_CHAT_LIST("OTCL"),

	/**
	 * 公会聊天 玩家读取信息
	 */
	ONLINE_PLAYER_CHAT_READ_INC("OPCRI"),
	;
	
	private String key;
	
	RedisKeyEnum(String key){
		this.key = key;
	}
	
	public static RedisKeyEnum geRedisKeyEnum(String key){
		RedisKeyEnum result = null;
		for(RedisKeyEnum temp: RedisKeyEnum.values()){
			if(temp.getKey().equals(key)){
				result = temp;
				break;
			}
		}
		return result;
	}
	
	public String getKey(){
		return this.key;
	}
}