package com.zcswl.pattern.state;

/**
 * 状态模式的定义：对有状态的对象，把复杂的判断逻辑提取到不同的状态对象中，允许状态对象在其内部发生改变时改变其行为
 *
 * @author zhoucg
 * @date 2020-07-13 14:15
 */
public class StateExample {

    public static void main(String[] args) {

    }
}

class ThreadContext {
    private ThreadState state;
    ThreadContext()
    {
        state=new New();
    }
    public void setState(ThreadState state)
    {
        this.state=state;
    }
    public ThreadState getState()
    {
        return state;
    }
    public void start()
    {
        ((New) state).start(this);
    }
    public void getCPU()
    {
        ((Runnable) state).getCPU(this);
    }
    public void suspend()
    {
        ((Running) state).suspend(this);
    }
    public void stop()
    {
        ((Running) state).stop(this);
    }
    public void resume()
    {
        ((Blocked) state).resume(this);
    }
}


/**
 * 抽象状态类：线程状态
 */
abstract class ThreadState {

    /**
     * 状态名
     */
    protected String stateName;
}

class New extends ThreadState {

    public New() {
        stateName = "新建状态";
        System.out.println("当前线程处于：新建状态");
    }

    public void start(ThreadContext threadContext) {
        System.out.print("调用start()方法-->");
        if (stateName.equals("新建状态")) {
            threadContext.setState(new Runnable());
        }
    }
}

//具体状态类：就绪状态
class Runnable extends ThreadState
{
    public Runnable()
    {
        stateName="就绪状态";
        System.out.println("当前线程处于：就绪状态.");
    }
    public void getCPU(ThreadContext hj)
    {
        System.out.print("获得CPU时间-->");
        if(stateName.equals("就绪状态"))
        {
            hj.setState(new Running());
        }
        else
        {
            System.out.println("当前线程不是就绪状态，不能获取CPU.");
        }
    }
}

//具体状态类：运行状态
class Running extends ThreadState
{
    public Running()
    {
        stateName="运行状态";
        System.out.println("当前线程处于：运行状态.");
    }
    public void suspend(ThreadContext hj)
    {
        System.out.print("调用suspend()方法-->");
        if(stateName.equals("运行状态"))
        {
            hj.setState(new Blocked());
        }
        else
        {
            System.out.println("当前线程不是运行状态，不能调用suspend()方法.");
        }
    }
    public void stop(ThreadContext hj)
    {
        System.out.print("调用stop()方法-->");
        if(stateName.equals("运行状态"))
        {
            hj.setState(new Dead());
        }
        else
        {
            System.out.println("当前线程不是运行状态，不能调用stop()方法.");
        }
    }
}

//具体状态类：阻塞状态
class Blocked extends ThreadState
{
    public Blocked()
    {
        stateName="阻塞状态";
        System.out.println("当前线程处于：阻塞状态.");
    }
    public void resume(ThreadContext hj)
    {
        System.out.print("调用resume()方法-->");
        if(stateName.equals("阻塞状态"))
        {
            hj.setState(new Runnable());
        }
        else
        {
            System.out.println("当前线程不是阻塞状态，不能调用resume()方法.");
        }
    }
}
//具体状态类：死亡状态
class Dead extends ThreadState
{
    public Dead()
    {
        stateName="死亡状态";
        System.out.println("当前线程处于：死亡状态.");
    }
}