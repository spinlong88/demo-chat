package com.icore.design.template;

public abstract  class RereshBaverage {
    /**
     * 制备饮料的模板方法
     * 封装了所有子类共同遵守的算法骨架
     * 定义一个操作中的算法的骨架，而将一些步骤延迟到子类中。
     * 模板方法使得子类可以不改变一个算法的结构即可重定义该算法的某些特定步骤。
     * 通俗的说的就是有很多相同的步骤的，在某一些地方可能有一些差别适合于这种模式，
     * 例如想要泡一杯茶或者一杯咖啡，第一步都是将水煮沸，第二部是加入咖啡或者茶，
     * 第三部就是将饮料倒入杯子中，第四部就是加入各种调味料。其中第一步和第三部都是一样的，
     * 这个就可以定义在基类，而第二步和第四步就是他们之间的差异就可以在具体的子类中去实现。下面就是代码实现。
     *
     * 定义抽象基类，为所有子类提供一个算法框架
     *
     */
    public final void prepreBvergeTemplage(){
        //步骤一 将水煮沸
        boilWater();
        //步骤二 炮制饮料
        brew();
        //步骤三 将饮料倒入杯中
        pourInCup();


        //步骤四 加入调味料
        if(isCusomerWantCondments()){
            addCondiments();
        }

    }

    /**
     * 基本方法，将水煮沸 这对所有子类而言是一个共同的行为，所以声明为private,无需向子类开放
     */
    private void boilWater(){
        System.out.println("将水煮沸");
    }

    /**
     * 抽象的基本方法 泡制饮料
     * 在算法框架中并不知道具体实现是什么样子的，所以做成了抽象方法，并且由于我们需要在子类中可见，便于复写而提供具体的实现所以将
     * 权限设置为protected
     */
    protected abstract void brew();

    private void pourInCup(){
        System.out.println("将饮料倒入杯中");
    }

    /**
     * 加入调味料
     */
    protected abstract void addCondiments();

    /**
     * 钩子函数
     * 提供一个默认或空的实现
     * 具体子类可以自行决定是否挂钩以及如何挂钩
     * 询问用户是否加入调料
     */
    protected boolean isCusomerWantCondments(){
        return true;
    }

}
