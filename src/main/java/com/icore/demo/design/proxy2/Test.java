package com.icore.demo.design.proxy2;

/**
 * 静态代理在使用时,需要定义接口或者父类,被代理对象与代理对象一起实现相同的接口或者是继承相同父类.
 * 下面举个案例来解释:
 * 模拟保存动作,定义一个保存动作的接口:IUserDao.java,然后目标对象实现这个接口的方法UserDao.java,
 * 此时如果使用静态代理方式,就需要在代理对象(UserDaoProxy.java)中也实现IUserDao接口.调用的时候通过调用代理对象的方法来调用目标对象.
 * 需要注意的是,代理对象与目标对象要实现相同的接口,然后通过调用相同的方法来调用目标对象的方法
 *
 * 动态代理有以下特点:
 * 1.代理对象,不需要实现接口
 * 2.代理对象的生成,是利用JDK的API,动态的在内存中构建代理对象(需要我们指定创建代理对象/目标对象实现的接口的类型)
 * 3.动态代理也叫做:JDK代理,接口代理
 *
 *
 *JDK中生成代理对象的API
 * 代理类所在包:java.lang.reflect.Proxy
 * JDK实现代理只需要使用newProxyInstance方法,但是该方法需要接收三个参数,完整的写法是:
 *
 * static Object newProxyInstance(ClassLoader loader, Class<?>[] interfaces,InvocationHandler h )
 *总结:
 * 代理对象不需要实现接口,但是目标对象一定要实现接口,否则不能用动态代理
 */

public class Test {

    public static void main(String[] args) {
        //目标对象
        UserDao target = new UserDao();

        //代理对象,把目标对象传给代理对象,建立代理关系
        UserDaoProxy proxy = new UserDaoProxy(target);

        proxy.save();//执行的是代理的方法



        // 目标对象
        IUserDao target2 = new UserDao();
        // 【原始的类型 class cn.itcast.b_dynamic.UserDao】
        System.out.println(target.getClass());

        // 给目标对象，创建代理对象
        IUserDao proxy2 = (IUserDao) new ProxyFactory(target2).getProxyInstance();
        // class $Proxy0   内存中动态生成的代理对象
        System.out.println(proxy.getClass());

        // 执行方法   【代理对象】
        proxy2.save();



        //目标对象
        UserDao target3 = new UserDao();

        //代理对象
        UserDao proxy3 = (UserDao)new ProxyFactory2(target3).getProxyInstance();

        //执行代理对象的方法
        proxy3.save();


    }
}
