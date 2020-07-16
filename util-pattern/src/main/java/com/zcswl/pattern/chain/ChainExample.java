package com.zcswl.pattern.chain;

/**
 * 责任链的调用模式：为了避免请求发送者与多个请求处理者耦合在一起，将所有请求的处理者通过前一对象记住其下一个对象的引用而连成
 * 一条链；当有请求发生时，可将请求沿着这条链传递，直到有对象处理它为止。
 *
 * @author zhoucg
 * @date 2020-07-13 14:01
 */
public class ChainExample {

    public static void main(String[] args) {
        Handler handler1 = new ConcreteHandler1();
        Handler handler2=new ConcreteHandler2();
        handler1.setNext(handler2);

        // 提交请求
        handler1.handlerRequest("two");
    }
}


/**
 * 抽象处理者角色
 */
abstract class Handler {
    private Handler next;

    public void setNext(Handler handler) {
        this.next = next;
    }

    public Handler getNext() {
        return next;
    }

    public abstract void handlerRequest(String request);
}

class ConcreteHandler1 extends Handler {

    @Override
    public void handlerRequest(String request) {
        if ("one".equals(request)) {
            System.out.println("具体处理者1负责处理该请求！");
        } else {
            if (null != getNext()) {
                getNext().handlerRequest(request);
            } else {
                System.out.println("没有人处理该请求");
            }
        }

    }
}

class ConcreteHandler2 extends Handler {

    @Override
    public void handlerRequest(String request) {
        if(request.equals("two"))
        {
            System.out.println("具体处理者2负责处理该请求！");
        }
        else
        {
            if(getNext()!=null)
            {
                getNext().handlerRequest(request);
            }
            else
            {
                System.out.println("没有人处理该请求！");
            }
        }
    }
}