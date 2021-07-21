package com.study.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity(name = "entityName")
@Table(name = "tb_user")
@Data
public class User {

    /*
     * JPA自带的几种主键生成策略:
     *   TABLE： 使用一个特定的数据库表格来保存主键
     *   SEQUENCE： 根据底层数据库的序列来生成主键，条件是数据库支持序列。
     *              这个值要与generator一起使用，generator 指定生成主键使用的生成器（可能是oracle中自己编写的序列）
     *   IDENTITY： 主键由数据库自动生成（主要是支持自动增长的数据库，如mysql）
     *   AUTO： 主键由程序控制，也是GenerationType的默认值
     */
    @Id
    // 主键采用UUID策略
    // @GenericGenerator是Hibernate提供的主键生成策略注解，
    // 下面的 @GeneratedValue（JPA注解）使用 generator = "idGenerator" 引用了上面的 name = "idGenerator"主键生成策略
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    private String id;

    @Column(name = "username", unique = true, nullable = false, length = 64)
    private String username;

    @Column(name = "password", nullable = false, length = 64)
    private String password;

    @Column(name = "email", length = 64)
    private String email;

}
