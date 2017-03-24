package com.snowcattle.game.db.service.entity;

import com.snowcattle.game.db.common.Loggers;
import com.snowcattle.game.db.common.annotation.*;
import com.snowcattle.game.db.entity.BaseEntity;
import com.snowcattle.game.db.entity.IEntity;
import com.snowcattle.game.db.service.jdbc.mapper.IDBMapper;
import com.snowcattle.game.db.service.proxy.EntityProxyWrapper;
import com.snowcattle.game.db.sharding.CustomerContextHolder;
import com.snowcattle.game.db.sharding.DataSourceType;
import org.apache.commons.collections.OrderedMap;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jiangwenping on 17/3/21.
 * 模版实体数据提服务
 */
public abstract class EntityService<T extends BaseEntity> implements IEntityService<T> {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    @Autowired
    private static ThreadLocal<SqlSession> threadLocal = new ThreadLocal<SqlSession>();


    private static final Logger logger = Loggers.dbLogger;

    /**
     * 插入实体
     *
     * @param entity
     * @return
     */
    @Override
    @DbOperation(operation = "insert")
    public long insertEntity(T entity) {
        long selectId = getShardingId(entity);
        CustomerContextHolder.setCustomerType(CustomerContextHolder.getShardingDBKeyByUserId(DataSourceType.jdbc_player_db, selectId));
        entity.setSharding_table_index(CustomerContextHolder.getShardingDBTableIndexByUserId(selectId));
        IDBMapper<T> idbMapper = getTemplateMapper(entity);
        long result = -1;
        try {
            result = idbMapper.insertEntity(entity);
            commitSession();
        } catch (Exception e) {
            rollbackSession();
            logger.error(e.toString(), e);
        } finally {
            closeSession();
        }
        return result;
    }

    /**
     * 查询实体
     *
     * @return
     */
    @DbOperation(operation = "query")
    public IEntity getEntity(T entity) {
        long selectId = getShardingId(entity);
        CustomerContextHolder.setCustomerType(CustomerContextHolder.getShardingDBKeyByUserId(DataSourceType.jdbc_player_db, selectId));
        entity.setSharding_table_index(CustomerContextHolder.getShardingDBTableIndexByUserId(selectId));
        IDBMapper<T> idbMapper = getTemplateMapper(entity);
        IEntity result = null;
        try {

            result = idbMapper.getEntity(entity);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        } finally {
            closeSession();
        }
        return result;
    }

    @DbOperation(operation = "queryList")
    public List<T> getEntityList(T entity) {
        long selectId = getShardingId(entity);
        CustomerContextHolder.setCustomerType(CustomerContextHolder.getShardingDBKeyByUserId(DataSourceType.jdbc_player_db, selectId));
        entity.setSharding_table_index(CustomerContextHolder.getShardingDBTableIndexByUserId(selectId));
        IDBMapper<T> idbMapper = getTemplateMapper(entity);
        List<T> result = null;
        try {
            idbMapper.getEntityList(entity);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        } finally {
            closeSession();
        }
        return result;
    }

    /**
     * 修改实体
     *
     * @param entity
     */
    @DbOperation(operation = "update")
    public void updateEntity(T entity) {
        long selectId = getShardingId(entity);
        CustomerContextHolder.setCustomerType(CustomerContextHolder.getShardingDBKeyByUserId(DataSourceType.jdbc_player_db, selectId));
        int sharding_table_index = CustomerContextHolder.getShardingDBTableIndexByUserId(selectId);
        Map hashMap = new HashMap<>();
        hashMap.put("sharding_table_index", sharding_table_index);
        hashMap.put("userId", entity.getUserId());
        hashMap.put("id", entity.getId());
        EntityProxyWrapper entityProxyWrapper = entity.getEntityProxyWrapper();
        //只有数据变化的时候才会更新
        if (entityProxyWrapper.getEntityProxy().isDirtyFlag()) {
            if (entityProxyWrapper != null) {
                hashMap.putAll(entityProxyWrapper.getEntityProxy().getChangeParamSet());
            }
            IDBMapper<T> idbMapper = getTemplateMapper(entity);
            try {
                idbMapper.updateEntityByMap(hashMap);
            } catch (Exception e) {
                logger.error(e.toString(), e);
            } finally {
                closeSession();
            }
        } else {
            logger.error("updateEntity cance " + entity.getClass().getSimpleName() + "id:" + entity.getId() + " userId:" + entity.getUserId());
        }
    }

    /**
     * 删除实体
     *
     * @param entity
     */
    @DbOperation(operation = "delete")
    public void deleteEntity(T entity) {
        long selectId = getShardingId(entity);
        ;
        CustomerContextHolder.setCustomerType(CustomerContextHolder.getShardingDBKeyByUserId(DataSourceType.jdbc_player_db, selectId));
        entity.setSharding_table_index(CustomerContextHolder.getShardingDBTableIndexByUserId(selectId));
        IDBMapper<T> idbMapper = getTemplateMapper(entity);
        try {
            idbMapper.deleteEntity(entity);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        } finally {
            closeSession();
        }
    }

    //获取分库主键
    protected long getShardingId(T entity) {
        long shardingId = entity.getUserId();
        if (entity.getEntityKeyShardingStrategyEnum().equals(EntityKeyShardingStrategyEnum.ID)) {
            shardingId = entity.getId();
        }
        return shardingId;
    }

    //获取分库主键
    protected long getShardingId(long id, long userId, EntityKeyShardingStrategyEnum entityKeyShardingStrategyEnum) {
        long shardingId = userId;
        if (entityKeyShardingStrategyEnum.equals(EntityKeyShardingStrategyEnum.ID)) {
            shardingId = id;
        }
        return shardingId;
    }

    public IDBMapper<T> getMapper(T entity) {
        DbMapper mapper = entity.getClass().getAnnotation(DbMapper.class);
        SqlSession sqlSession = getSession();
        return (IDBMapper<T>) sqlSessionTemplate.getSqlSessionFactory().openSession().getMapper(mapper.mapper());
    }

    /**
     * Function  : 获取sqlSession
     */
    public SqlSession getSession() {
        SqlSession session = threadLocal.get();

        if (session == null) {
            //如果sqlSessionFactory不为空则获取sqlSession，否则返回null
            session = (sqlSessionTemplate.getSqlSessionFactory() != null) ? sqlSessionTemplate.getSqlSessionFactory().openSession() : null;
            threadLocal.set(session);
        }
        return session;
    }

    /**
     * Function  : 关闭sqlSession
     */
    public void closeSession() {
        SqlSession session = threadLocal.get();
        if (session != null) {
            logger.debug("销毁");
            session.close();
            threadLocal.set(null);
        }
    }

    /**
     * Function  : 关闭sqlSession
     */
    public void rollbackSession() {
        SqlSession session = threadLocal.get();
        if (session != null) {
            session.rollback();
        }
    }

    public void commitSession(){
        SqlSession session = threadLocal.get();
        if (session != null) {
            session.commit();
        }
    }

    public IDBMapper<T> getTemplateMapper(T entity) {
        DbMapper mapper = entity.getClass().getAnnotation(DbMapper.class);
        return (IDBMapper<T>) sqlSessionTemplate.getMapper(mapper.mapper());
    }

}
