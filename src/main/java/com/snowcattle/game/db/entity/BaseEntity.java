package com.snowcattle.game.db.entity;

import com.snowcattle.game.db.common.annotation.EntitySave;
import com.snowcattle.game.db.common.annotation.FieldSave;
import com.snowcattle.game.db.common.annotation.MethodProxy;

import java.util.Date;

/**
 * Created by jiangwenping on 17/3/16.
 */
@EntitySave
public abstract  class BaseEntity implements ISoftDeleteEntity<Long>{

    @FieldSave
    private boolean deleted;

    @FieldSave
    private Date deleteTime;

    public boolean isDeleted() {
        return deleted;
    }

    @MethodProxy
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public Date getDeleteTime() {
        return deleteTime;
    }

    @MethodProxy
    public void setDeleteTime(Date deleteTime) {
        this.deleteTime = deleteTime;
    }
}
