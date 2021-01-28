package com.icore.demo.design.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 *在动态代理中我们不再需要再手动的创建代理类，我们只需要编写一个动态处理器就可以了。真正的代理对象由JDK再运行时为我们动态的来创建。
 *
 *  注意Proxy.newProxyInstance()方法接受三个参数：
 *     ClassLoader loader:指定当前目标对象使用的类加载器,获取加载器的方法是固定的
 *     Class<?>[] interfaces:指定目标对象实现的接口的类型,使用泛型方式确认类型
 *     InvocationHandler:指定动态处理器，执行目标对象的方法时,会触发事件处理器的方法
 *
 *虽然相对于静态代理，动态代理大大减少了我们的开发任务，
 * 同时减少了对业务接口的依赖，降低了耦合度。但是还是有一点点小小的遗憾之处，
 * 那就是它始终无法摆脱仅支持interface代理的桎梏，因为它的设计注定了这个遗憾。
 * 回想一下那些动态生成的代理类的继承关系图，它们已经注定有一个共同的父类叫Proxy。
 * Java的继承机制注定了这些动态代理类们无法实现对class的动态代理，原因是多继承在Java中本质上就行不通。
 * 有很多条理由，人们可以否定对 class代理的必要性，但是同样有一些理由，相信支持class动态代理会更美好。
 * 接口和类的划分，本就不是很明显，只是到了Java中才变得如此的细化。如果只从方法的声明及是否被定义来考量，
 * 有一种两者的混合体，它的名字叫抽象类。实现对抽象类的动态代理，相信也有其内在的价值。此外，
 * 还有一些历史遗留的类，它们将因为没有实现任何接口而从此与动态代理永世无缘。如此种种，
 * 不得不说是一个小小的遗憾。但是，不完美并不等于不伟大，伟大是一种本质，Java动态代理就是佐例。
 *
 */

public class DynamicProxyHandler implements InvocationHandler {

    private Object object;

    public DynamicProxyHandler(final Object object) {
         this.object = object;
    }

    @Override
     public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("买房前准备");
                 Object result = method.invoke(object, args);
                 System.out.println("买房后装修");
                 return result;
             }

}
