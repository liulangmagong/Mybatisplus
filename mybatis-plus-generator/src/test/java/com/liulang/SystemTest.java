package com.liulang;

/**
 * @author wangshiyang
 * @since 2022/3/31
 **/
public class SystemTest {
    public static void main(String[] args) {
        // 获取当前系统目录  即项目路径
        String projectPath = System.getProperty("user.dir");
        System.out.println(projectPath);
    }
}
