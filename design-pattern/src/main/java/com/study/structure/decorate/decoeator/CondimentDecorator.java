package com.study.structure.decorate.decoeator;

import com.study.structure.decorate.entity.Bevarage;

/**
 * 结构型模式 - 装饰者模式
 *
 * 装饰者模式的装饰者抽象类：
 * 1. 所有具体装饰者都要继承的类，
 * 2. 该抽象类继承了 被装饰的对象，或是实现了被装饰的对象实现的接口
 * 3. 必须包含被装饰类型的成员变量
 */
public abstract class CondimentDecorator extends Bevarage {

}
