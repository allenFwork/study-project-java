package com.study.proxy.statcProxy;

/**
 * 静态代理：聚合
 */
public class ProxyObject2 implements Target {

    /**
     * 装饰者模式：通过构造方法来注入具体的对象
     */
    private TargetObject target;

    public ProxyObject2(TargetObject target){
        this.target = target;
    }

    @Override
    public void sayHello() {
        System.out.println("dynamic proxy : polymerization : before ... ");
        target.sayHello();
        System.out.println("dynamic proxy : polymerization : after  ... ");
    }

    @Override
    public String test(String input) {
        System.out.println("dynamic proxy : polymerization : before ... ");
        String str = target.test(null);
        System.out.println("dynamic proxy : polymerization : after  ... ");
        return str;
    }
}
