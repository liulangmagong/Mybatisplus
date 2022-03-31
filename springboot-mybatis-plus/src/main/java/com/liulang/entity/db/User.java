package com.liulang.entity.db;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author wangshiyang
 * @since 2022/3/27
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    // 对应数据库中的主键
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private Integer age;
    private String email;

    @TableLogic // 逻辑删除注解
    private Integer deleted;

    // 字段添加填充内容
    // 在插入数据的时候，执行自动插入时间的操作
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    // 在更新数据的时候，执行自动更新时间的操作
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    // 乐观锁的version注解
    @Version
    private Integer version;
}
