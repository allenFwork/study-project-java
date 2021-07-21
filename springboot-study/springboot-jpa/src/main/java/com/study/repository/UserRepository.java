package com.study.repository;

import com.study.entity.User;
import org.hibernate.PersistentObjectException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 使用Spring Data创建查询只需四步:
 *   1. 声明一个接口继承自 Repository 或 Repository 的一个子接口，对于Spring Data Jpa通常是JpaRepository
 *   2. 在接口中声明查询方法
 *   3. 使用 JavaConfig 或 XML configuration配置Spring，让 Spring 为声明的接口创建代理对象
 *     3.1 JavaConfig参见上文
 *     3.2 使用Xml配置，可以像 spring-jpa.xml 这样使用 jpa 命名空间进行配置
 *   4. 注入Repository实例并使用
 */
public interface UserRepository extends JpaRepository<User, String> {

    List<User> findByUsername(String username);

    /*
     * 自定义查询Using @Query
     * @Query 注解的使用非常简单，只需在声明的方法上面标注该注解，同时提供一个 JPQL 查询语句即可
     */
    @Query("select u from User u where u.email = ?1")
    User getByEmail(String email);

    @Query("select u from User u where u.username = ?1 and u.password = ?2")
    User getByUsernameAndPassword(String username, String password);

    @Query("select u from User u where u.username like %?1%")
    List<User> getByUsernameLike(String username);


    /*
     * 使用命名参数 Using Named Parameters
     * 默认情况下，Spring Data JPA使用基于位置的参数绑定，如前面所有示例中所述。
     * 这使得查询方法在重构参数位置时容易出错。
     * 要解决此问题，可以使用 @Param注解 为方法参数指定具体名称并在查询中绑定名称，如以下示例所示：
     */
    @Query("select u from User u where u.id = :id")
    User getById(@Param("id") String userId);

    @Query("select u from User u where u.username = :username or u.email = :email")
    User getByUsernameOrEmail(@Param("username") String username, @Param("email") String email);


    /*
     * Using SpEL Expressions
     * 从Spring Data JPA release 1.4开始，Spring Data JPA支持名为entityName的变量。
     * 它的用法是select x from #{#entityName} x。
     * entityName的解析方式如下：如果实体类在@Entity注解上设置了name属性，则使用它。
     * 否则，使用实体类的简单类名。为避免在@Query注解使用实际的实体类名，就可以使用#{#entityName}进行代替。
     * 如以上示例中，@Query注解的查询字符串里的User都可替换为#{#entityName}
     */
    @Query("select u from #{#entityName} u where u.email = ?1")
    User getByEmail2(String email);


    /*
     * 原生查询 Native Queries
     * @Query注解还支持通过将nativeQuery标志设置为true来执行原生查询，同样支持基于位置的参数绑定及命名参数
     */
    @Query(value = "select * from tb_user u where u.email = ?1", nativeQuery = true)
    User queryByEmail3(String email);

    @Query(value = "select * from tb_user u where u.email = :email", nativeQuery = true)
    User queryByEmail4(@Param("email") String email);

    /*
     * 注意：Spring Data Jpa目前不支持对原生查询进行动态排序，
     * 但可以通过自己指定计数查询countQuery来使用原生查询进行分页、排序
     */
    @Query(value = "select * from tb_user u where u.username like %?1%",
           countQuery = "select count(1) from tb_user u where u.username = %?1%",
           nativeQuery = true)
    Page<User> queryByUsernameLike(String username, Pageable pageable);


    /*
     * 自定义修改、删除 Modifying Queries
     * 单独使用@Query注解只是查询，如涉及到修改、删除则需要再加上@Modifying注解
     *
     * 注意：Modifying queries can only use void or int/Integer as return type
     */
    @Transactional()
    @Modifying
    @Query("update User u set u.password = ?2 where u.username = ?1")
    int updatePasswordByUsername(String username, String password);

    @Transactional()
    @Modifying
    @Query("delete from User where username = ?1")
    void deleteByUsername(String username);

}
