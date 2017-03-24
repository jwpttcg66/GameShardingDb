package com.snowcattle.game.db.common.annotation;

import com.snowcattle.game.db.entity.IEntity;
import com.snowcattle.game.db.service.jdbc.mapper.IDBMapper;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by jwp on 2017/3/24.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface DbMapper {
    /**
     * @return
     */
    Class mapper();
}
