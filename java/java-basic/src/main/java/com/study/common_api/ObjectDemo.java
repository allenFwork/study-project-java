package com.study.common_api;

import java.util.Objects;
import java.util.StringJoiner;

public class ObjectDemo {

    // 1.Object的toString方法使用
    public static void toStringUse() {

    }

    // 2.Object的equals方法使用
    public static void equalsUse() {
        User user1 = new User(1, "张三", "123456", "boy123", null);
        User user2 = new User(1, "张三", "123456", "boy123", null);
        User user3 = new User(1, "李四", "234567", "boy567", null);
        System.out.println(user1.equals(user2)); //true
        System.out.println(user1.equals(user3)); //false
    }

    // 3.Object的clone方法使用
    public static void cloneUse() throws CloneNotSupportedException {

        // 1.创建User对象
        int[] data = {1, 2, 3, 4, 5, 6, 7, 8, 10, 11, 12, 13, 14, 15, 0};
        User user1 = new User(1, "张三", "123456", "boy123", data);

        // 2.克隆对象
        /**
         * 原理细节：
         *   java.lang.Object方法：protected native Object clone() throws CloneNotSupportedException;
         *   clone方法在底层会帮我们创建一个对象，并把原对象中的数据拷贝过去
         * 实现细节（三步）：
         *   第一步：重写Object中的clone方法
         *   第二步：让javabean类实现Cloneable接口
         *   第三步：创建原对象并调用clone就可以了
         */
        User user2 = (User) user1.clone();
        int[] arr1 = user1.getData();
        int[] arr2 = user2.getData();
        arr1[0] = 100;
        System.out.println(arr2[0]);

        System.out.println(user1); //默认调用 toString方法
        System.out.println(user2);//默认调用 toString方法

        System.out.println(user1.getClass().getName() + "@" + Integer.toHexString(user1.hashCode()));
        System.out.println(user2.getClass().getName() + "@" + Integer.toHexString(user2.hashCode()));

        /**
         * 以后一般会用第三方工具进行克隆，步骤如下：
         * 1.第三方写的代码导入到项目中
         * 2.编写代码
         * Gson gson = new Gson();
         * 把对象变成一个字符串
         * String s= gson.toJson(u1);
         * 再把字符串变回对象就可以了
         * //User user = gson.fromJson(s, User.class);
         */

    }

    public static void main(String[] args) throws CloneNotSupportedException {
        equalsUse();
        cloneUse();
    }

}

/**
 * 实现 java.lang.Cloneable 接口
 * 如果一个接口里面没有抽象方法，表示当前的接口是一个标记性接口
 * 现在Cloneable表示一旦实现了，那么当前类的对象就可以被克降
 * 如果没有实现，当前类的对象就不能克隆
 */
class User implements Cloneable {
    private int id;
    private String name;
    private String password;
    private String path;
    private int[] data;

    public User() {
    }

    public User(int id, String name, String password, String path, int[] data) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.path = path;
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int[] getData() {
        return data;
    }

    public void setData(int[] data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "角色编号为：" + id + "，用户名为：" + name + "密码为：" + password + ", 游戏图片为:" + path + ", 进度:" + arrToString();
    }

    @Override //重写了equals方法
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                name.equals(user.name) &&
                password.equals(user.password) &&
                Objects.equals(path, user.path);
    }

    @Override //理论上要重写，这里先不研究
    public int hashCode() {
        return 0;
    }

    String arrToString() {
        StringJoiner sj = new StringJoiner(", ", "[", "]");
        for (int i = 0; i < data.length; i++) {
            sj.add(data[i] + "");
        }
        return sj.toString();
    }

    @Override //重写 clone 方法，实现深克隆
    protected Object clone() throws CloneNotSupportedException {
        /**
         * 默认的克隆方法，调用父类中的clone方法 （浅克隆）
         * 相当于让Java帮我们克隆一个对象，并把克隆之后的对象返回出去。
         */
        // return super.clone();

        //自定义克隆方法的实现实现（深克隆）
        //先把被克隆对象中的数组获取出来
        int[] data = this.data;
        //创建新的数组
        int[] newData = new int[data.length];
        //拷贝数组中的数据
        for (int i = 0; i < data.length; i++) {
            newData[i] = data[i];
        }
        //调用父类中的方法克隆对象
        User u = (User) super.clone();
        //因为父类中的克隆方法是浅克隆，替换克隆出来对象中的数组地址值
        u.data = newData;
        return u;
    }
}