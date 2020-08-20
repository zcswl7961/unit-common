package com.zcswl.pattern.strategy;

/**
 * 如果使用多重条件转移语句实现（即硬编码），不但使条件语句变得很复杂，而且增加、删除或更换算法要修改原代码，
 * 不易维护，违背开闭原则。如果采用策略模式就能很好解决该问题。
 *
 *
 * http://c.biancheng.net/view/1378.html
 * https://www.cnblogs.com/yimengyizhen/p/11062234.html
 *
 * 策略模式的代码层的实现：Arrays.sort
 * @author zhoucg
 * @date 2020-07-13 11:18
 */
public class StrategyExample {
}


/**
 * 定义策略算法的抽象基类
 */
abstract class Strategy {
    public abstract void AlgorithmInterface();
}

/**
 * 策略算法的具体实现:A
 */
class AlgorithmA extends Strategy {

    @Override
    public void AlgorithmInterface() {
        System.out.println("实现算法A");
    }
}

/**
 * 策略算法的具体实现：B
 */
class AlgorithmB extends Strategy {

    @Override
    public void AlgorithmInterface() {
        System.out.println("实现算法B");
    }
}

/**
 * 维护策略算法执行的上下文环境
 */
class Context2 {
    Strategy strategy = null;

    public Context2 (String type) {
        switch (type){
            case "A": strategy = new AlgorithmA();
                break;
            case "B": strategy = new AlgorithmB();
                break;

        }
    }

    /**
     * 策略执行
     */
    public void ContrxtInterface(){
        strategy.AlgorithmInterface();
    }

}
