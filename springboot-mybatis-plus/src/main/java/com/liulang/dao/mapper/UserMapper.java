package com.liulang.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liulang.entity.db.User;
import org.springframework.stereotype.Repository;

/**
 * @author wangshiyang
 * @since 2022/3/27
 **/
// 在对应的Mapper上边继承基本的类 BaseMapper
// 这里传入的范型 对应的就是BaseMapper中定义的CRUD的方法要操作的对象
// 不需要再像mybatis那样配置一大堆文件了
@Repository // 表示这是持久层
public interface UserMapper extends BaseMapper<User> {
}
