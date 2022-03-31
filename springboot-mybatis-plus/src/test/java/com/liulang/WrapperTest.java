package com.liulang;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liulang.dao.mapper.UserMapper;
import com.liulang.entity.db.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

/**
 * @author wangshiyang
 * @since 2022/3/31
 **/
@SpringBootTest
public class WrapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    void contextLoads(){
        // 查询name不为空的用户，邮箱不为空并且年龄大于等于12 的用户
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.isNotNull("name")
                .isNotNull("email")
                .ge("age", 12);
        userMapper.selectList(wrapper).forEach(System.out::println);
    }

    /**
     * 查询名为为***的用户
     */
    @Test
    void test2(){
        // 查询名为为li的用户
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("name", "wang");
        // 查询一个，如果多个结果使用List或者map
        System.out.println(userMapper.selectOne(wrapper));
    }

    /**
     * between，selectCount的使用测试
     */
    @Test
    void test3(){
        // 查询年龄在20-30岁之间的用户
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.between("age", 20, 30);
        // 统计结果个数（统计计算是不是要拿到代码中逻辑处理？）
        System.out.println(userMapper.selectCount(wrapper));
    }

    /**
     * 模糊查询
     * 名字中不含***的用户
     * likeRight和likeLeft的区别就是 % 在左边还是在右边的区别：e%  %e
     * selectMaps 得到多个结果
     * {update_time=2022-03-27 23:53:32.0, deleted=0, name=Jack, id=2, version=1, age=22, email=test2@baomidou.com}
     * {update_time=2022-03-27 23:08:42.0, deleted=0, name=Tom, id=3, version=1, age=28, email=test3@baomidou.com}
     * {update_time=2022-03-27 23:08:42.0, deleted=0, name=Sandy, id=4, version=1, age=21, email=test4@baomidou.com}
     */
    @Test
    void test4(){
        // 查询年龄在20-30岁之间的用户
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper
                .notLike("name", "e")
                .likeRight("email", "t");
        List<Map<String, Object>> maps = userMapper.selectMaps(wrapper);
        maps.forEach(System.out::println);
    }

    /**
     * 内查询  sql里边嵌入一个SQL
     * SELECT id,name,age,email,deleted,create_time,update_time,version FROM user WHERE deleted=0 AND id IN (select id from user where id < 3)
     */
    @Test
    void test5(){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        // id 在子查询中查出来
        wrapper.inSql("id", "select id from user where id < 3");
        List<Object> objects = userMapper.selectObjs(wrapper);
        objects.forEach(System.out::println);
    }

    /**
     * 排序
     */
    @Test
    void test6(){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        List<User> users = userMapper.selectList(wrapper);
        users.forEach(System.out::println);
    }
}
