package com.zcswl.leecode;

/**
 * @author xingyi
 * @date 2021/7/29 5:07 下午
 */
public class Test {

    public static void main(String[] args) {
        TestAA testAA = new TestAA();
        int x = 1;
        testAA.setTaskType(x);

        boolean s = testAA.getTaskType() != x;
        System.out.println(s);
    }


    public static class TestAA{
        private Integer taskType;

        public Integer getTaskType() {
            return taskType;
        }

        public void setTaskType(Integer taskType) {
            this.taskType = taskType;
        }
    }
}
