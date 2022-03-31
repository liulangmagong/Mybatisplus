package com.liulang.handler;

import ch.qos.logback.classic.Logger;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component  //一定不要忘记了把我们的处理器放入ioc容器
public class MyMetaObjectHandler implements MetaObjectHandler {
    Logger logger = (Logger) LoggerFactory.getLogger(MyMetaObjectHandler.class);

    /**
     * 插入数据时的填充策略
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
            logger.info("meta insert fill ...");
            //setFieldValByName(String fieldName, Object fieldVal, MetaObject metaObject)
        this.setFieldValByName("createTime",new Date(),metaObject);
        this.setFieldValByName("updateTime",new Date(),metaObject);
    }

    /**
     * 更新时的填充策略
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateTime",new Date(),metaObject);
    }
}