package com.icore.demo.design.strategy;

public class LogContext {

    public void log(String msg){
        ILogStrategy strategy = new DbLog();
        try{
            strategy.log(msg);
        }catch (Exception e){
            //出错，记录到文件
            strategy = new FileLog();
            strategy.log(msg);
        }
    }

    //通过上面的示例，会看到策略模式的一种简单应用，也顺便了解一下基本的容错恢复机制的设计和实现。
    // 在实际的应用中，需要设计容错恢复的系统一般要求都比较高，应用也会比较复杂，但是基本的思路是差不多的。

}
