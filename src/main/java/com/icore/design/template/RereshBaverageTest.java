package com.icore.design.template;

public class RereshBaverageTest {


    //总结：
    //模板方法模式的实现要素：1.抽象基类 2.具体子类。在抽象基类中要提供一些具体的基本方法，
    // 对于各种不同的实现子类而言是相同的，具有共性的。还有一些抽象方法，是对于那些我们只知道具体原则，
    // 而不知道实现细节，需要将她延迟到子类实现的一些步骤。还有一些我们可选的钩子函数。最后我们将基本方法，
    // 抽象方法和钩子函数按照我们业务逻辑的需求汇总成一个模板方法，这就构成了我们的算法框架，模板方法必须声明成final，不能被子类所复写。

    //模板方法模式的适用场景：
    //(1)算法或操作遵循相似的逻辑
    //(2)重构时（把相同的代码提取到父类中）
    //(3)重要复杂的算法，核心算法设计为模板方法
    //模板方法模式的优点：1.封装性好 2.复用性好 3.屏蔽细节 4.便于维护
    //模板方法模式的缺点：继承 java是一个单继承的语言，设想在一个已有的系统当中大量的使用到了继承，
    // 这个时候如果我们想要做一些重构，通过模板方法的模式抽取共性，因为我们的类已经出一个继承层次的某个结构之中，
    // 再通过模板方法引入新的继承的时候就会遇到困难。
    //具体子类：1.实现基类中的抽象方法 2.覆盖钩子方法。

    public static void main(String[] args) {
        System.out.println("咖啡制备中。。。");
        RereshBaverage coffee = new Coffee();
        coffee.prepreBvergeTemplage();
        System.out.println("咖啡制备完成！ ");

        System.out.println("\n*********************");
        System.out.println("茶制备中。。。");
        RereshBaverage tea = new Tea();
        tea.prepreBvergeTemplage();
        System.out.println("茶制备完成！");
    }

}
