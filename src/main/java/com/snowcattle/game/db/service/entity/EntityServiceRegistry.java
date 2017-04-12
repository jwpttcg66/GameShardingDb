package com.snowcattle.game.db.service.entity;

import com.snowcattle.game.db.common.GlobalConstants;
import com.snowcattle.game.db.common.Loggers;
import com.snowcattle.game.db.common.annotation.AsyncEntityServiceSave;
import com.snowcattle.game.db.common.loader.scanner.ClassScanner;
import com.snowcattle.game.db.service.config.DbConfig;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by jiangwenping on 17/4/11.
 */
@Service
public class EntityServiceRegistry {

    public static Logger logger = Loggers.dbServerLogger;

    @Autowired
    private DbConfig dbConfig;

    /**
     * 包体扫描
     */
    public ClassScanner messageScanner = new ClassScanner();

    /**
     * 注册map
     */
    private ConcurrentHashMap<String, Class> registryMap = new ConcurrentHashMap<String, Class>();

    public void startup() throws Exception {
        loadPackage(dbConfig.getEntiyServicePackageName(),
                GlobalConstants.ClassConstants.Ext);
    }

    public void shutdown() throws Exception {

    }



    public void loadPackage(String namespace, String ext) throws Exception {
        String[] fileNames = messageScanner.scannerPackage(namespace, ext);
        // 加载class,获取协议命令
        if(fileNames != null) {
            for (String fileName : fileNames) {
                String realClass = namespace
                        + "."
                        + fileName.subSequence(0, fileName.length()
                        - (ext.length()));
                Class<?> messageClass = Class.forName(realClass);

                logger.info("EntityServiceRegistry load:" + messageClass);
                AsyncEntityServiceSave asyncEntityServiceSave = messageClass.getAnnotation(AsyncEntityServiceSave.class);
                if(asyncEntityServiceSave != null) {
                    registryMap.put(messageClass.getSimpleName(), messageClass);
                }
            }
        }
    }

    public DbConfig getDbConfig() {
        return dbConfig;
    }

    public void setDbConfig(DbConfig dbConfig) {
        this.dbConfig = dbConfig;
    }

    public ConcurrentHashMap<String, Class> getRegistryMap() {
        return registryMap;
    }

    public void setRegistryMap(ConcurrentHashMap<String, Class> registryMap) {
        this.registryMap = registryMap;
    }

    public Collection getAllEntityServiceRegistry(){
        return registryMap.values();
    }

}
