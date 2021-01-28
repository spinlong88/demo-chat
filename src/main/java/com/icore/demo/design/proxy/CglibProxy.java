package com.icore.demo.design.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 *
 * JDK实现动态代理需要实现类通过接口定义业务方法，对于没有接口的类，如何实现动态代理呢，这就需要CGLib了。
 * CGLib采用了非常底层的字节码技术，其原理是通过字节码技术为一个类创建子类，
 * 并在子类中采用方法拦截的技术拦截所有父类方法的调用，顺势织入横切逻辑。但因为采用的是继承，
 * 所以不能对final修饰的类进行代理。JDK动态代理与CGLib动态代理均是实现Spring AOP的基础。
 *
 * CGLIB代理总结： CGLIB创建的动态代理对象比JDK创建的动态代理对象的性能更高，
 * 但是CGLIB创建代理对象时所花费的时间却比JDK多得多。所以对于单例的对象，
 * 因为无需频繁创建对象，用CGLIB合适，反之使用JDK方式要更为合适一些。
 * 同时由于CGLib由于是采用动态创建子类的方法，对于final修饰的方法无法进行代理。
 */

public class CglibProxy implements MethodInterceptor {

    private Object target;

    public Object getInstance(final Object target) {
        this.target = target;
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(this.target.getClass());
        enhancer.setCallback(this);
        return enhancer.create();
    }

    public Object intercept(Object object, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        System.out.println("买房前准备");
        Object result = methodProxy.invoke(object, args);
        System.out.println("买房后装修");
        return result;
    }

}
