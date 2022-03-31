package com.liulang;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liulang.dao.mapper.UserMapper;
import com.liulang.entity.db.User;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @author wangshiyang
 * @since 2022/3/27
 **/
@SpringBootTest
public class MybatisPlusApplicationTest {

    // 在测试类中，注入的时候一定要使用 @Autowired 进行注入
    @Autowired
    private UserMapper userMapper;

    // 创建一个记录器
    private static final Logger logger = LoggerFactory.getLogger(MybatisPlusApplicationTest.class);

    /**
     * userMapper中没有定义任何方法  所有的方法都是继承的父类的方法
     * 但是需要使用自定义的方法的时候，也可以自己在接口中定义自己的方法
     */
    @Test
    public void contextLoad(){
        // 查询全部用户
        // 方法参数为一个Mapper，是一个条件构造器  这里暂时不用 传入 null 即可
        logger.info("输出SQL：");
        List<User> users = userMapper.selectList(null);
        users.forEach(System.out::println);
    }

    /**
     * 测试插入操作
     */
    @Test
    public void testInsert(){
        User user = new User();
        user.setName("mary");
        user.setAge(14);
        user.setEmail("17854250704@163.com");

        int result = userMapper.insert(user);
        System.out.println(result);
        // 通过结果可以看到，虽然我们没有设置ID，但是它会自动的生成ID
    }

    /**
     * 测试更新操作
     */
    @Test
    public void testUpdate(){
        User user = new User();
//        user.setName("wangshiyang");
        user.setAge(22);
        user.setId(2L);
//        user.setEmail("17854250704@163.com");

        int result = userMapper.updateById(user);
        System.out.println(result);
        // 通过结果可以看到，虽然我们没有设置ID，但是它会自动的生成ID
    }

    /**
     * 测试乐观锁成功
     */
    @Test
    public void testOptimisticLocker(){
        // 1、查询用户信息
        User user = userMapper.selectById(1L);
        // 2、修改用户信息
        user.setName("liu");
        user.setEmail("2720411539@qq.com");
        // 3、执行更新操作  在单线程的情况下，这里肯定不会出现任何问题的
        userMapper.updateById(user);
    }

    /**
     * 测试乐观锁失败  多线程的情况
     * 这里的多线程是模拟的多线程：
     *  线程1查询并设置了新的数据，已经获取了版本号version 但是没有进行更新操作
     *  线程2也修改了这个数据，并且先于线程1进行了更新操作 修改了版本号
     */
    @Test
    public void testOptimisticLocker2(){
        // 线程1
        // 1、线程1查询用户信息
        User user = userMapper.selectById(1L);
        // 2、线程1修改用户信息
        user.setName("wang");
        user.setEmail("2720411539@qq.com");

        // 线程2
        // 1、线程2查询用户信息
        User user2 = userMapper.selectById(1L);
        // 2、线程2修改用户信息
        user2.setName("li");
        user2.setEmail("2720411539@qq.com");
        // 3、线程2执行更新操作  在单线程的情况下，这里肯定不会出现任何问题的
        userMapper.updateById(user2);

        // 3、线程1执行更新操作  在单线程的情况下，这里肯定不会出现任何问题的
        // 如果没有乐观锁，就会覆盖插队线程2更新的值
        // 解决这一问题，可以使用自旋锁进行操作 多次尝试提交
        userMapper.updateById(user);
    }

    /**
     * 测试根据单个ID查询
     */
    @Test
    public void testSelectById(){
        User user = userMapper.selectById(1L);
        System.out.println(user);
    }

    /**
     * 测试同时查询多个ID对应的数据
     */
    @Test
    public void testSelectBatchIdsByList(){
        // 参数是一个集合
        List<User> users = userMapper.selectBatchIds(Arrays.asList(1, 2, 3));
        users.forEach(System.out::println);
    }

    /**
     * 测试条件查询一：使用map操作
     * 对于复杂的查询，一般不会使用map进行封装，而是会用Mapper进行封装
     * 这里直接使用Object，因为key对应的都是字符串，但是value对应的类型是各种样的
     */
    @Test
    public void testSelectByMap(){
        HashMap<String, Object> map = new HashMap<>();
        // SELECT id,name,age,email,create_time,update_time,version FROM user WHERE name = ?
        map.put("name", "li");
        // SELECT id,name,age,email,create_time,update_time,version FROM user WHERE name = ? AND age = ?
        map.put("age", 18);
        List<User> users = userMapper.selectByMap(map);
        users.forEach(System.out::println);
    }

    /**
     * 测试分页查询
     * 和前端对接的话，只需要前端传入两个参数：当前页，显示条数即可
     * 底层也是limit的操作，但是通过mybatisPlus进行封装之后，在编写代码的时候就是面向对象的写法
     * SELECT id,name,age,email,create_time,update_time,version FROM user LIMIT 0,5
     */
    @Test
    public void testPage(){
        // 参数一：当前页
        // 参数二：页面大小
        // 这里要注意的是，传入参数的当前页是实际的页数，但是在底层查询的时候由于有0页的存在，所以就
        // -1查询
        Page<User> page = new Page<>(2, 5);
        userMapper.selectPage(page, null);

        page.getRecords().forEach(System.out::println);
        // SELECT COUNT(1) FROM user 执行getTotal的时候会进行一次统计总数的操作
        System.out.println(page.getTotal());
    }

    /**
     * 测试删除
     */
    @Test
    public void testDeleteById(){
        userMapper.deleteById(1L);
        // 批量删除
//        userMapper.deleteBatchIds(Arrays.asList(1L, 2L, 3L));
//        // 通过条件删除
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("name", "wang");
//        userMapper.deleteByMap(map);
    }

}
