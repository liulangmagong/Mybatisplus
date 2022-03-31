package com.liulang.config;

import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.optimize.JsqlParserCountOptimize;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @author wangshiyang
 * @since 2022/3/28
 * 在这里对mybatisPlus进行管理之后，那么主启动类上的MapperScan注解就可以配置到这里了
 * 直接放在这里的配置类上边即可，让它来做扫描
 **/
// 为了使得我们的Mapper生效，这里需要配置对应的包扫描
@MapperScan("com.liulang.dao.mapper")
@Configuration  // 表是这是一个配置类
public class MybatisPlusConfig {

    // 注册乐观锁插件
    // 这里是一个拦截器的实现，进行操作之前进行拦截，然后进行自动化的处理
    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
    }

    // 配置分页插件
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    // 配置逻辑删除
    @Bean
    public ISqlInjector sqlInjector() {
        return new LogicSqlInjector();
    }

    /**
     * SQL执行效率插件
     * 只在开发和测试环境下执行
     * 当前过程不是在开发过程中，可以在application.yml中配置为开发过程
     * @return
     */
    @Bean
    // 设置 dev test 环境开启，主要是为了保证效率
    @Profile({"dev", "test"})
    public PerformanceInterceptor performanceInterceptor(){
        PerformanceInterceptor performanceInterceptor = new PerformanceInterceptor();
        // 设置sql执行的最大时间，超过这个时间就不会继续执行了
        // 默认是毫秒值
        performanceInterceptor.setMaxTime(1000);
        // 将sql语句格式化，使其可以更加便于观察
        performanceInterceptor.setFormat(true);
        return new PerformanceInterceptor();
    }
}
