package com.snowcattle.game.db.entity;


import com.snowcattle.game.db.common.annotation.EntitySave;
import com.snowcattle.game.db.common.annotation.FieldSave;
import com.snowcattle.game.db.common.annotation.MethodSaveProxy;

/**
 * Created by jiangwenping on 17/4/5.
 */
@EntitySave
public class BaseLongIDEntity extends AbstractEntity<Long> {

    @FieldSave
    private long id;

    @Override
    public Long getId() {
        return id;
    }


    @Override
    @MethodSaveProxy(proxy="id")
    public void setId(Long id) {
        this.id = id;
    }

}
