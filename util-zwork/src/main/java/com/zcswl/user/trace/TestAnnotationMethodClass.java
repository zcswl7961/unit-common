package com.zcswl.user.trace;

import lombok.AllArgsConstructor;
import org.apache.skywalking.apm.toolkit.trace.Tag;
import org.apache.skywalking.apm.toolkit.trace.Trace;

/**
 * @author xingyi
 * @date 2023/6/13
 */
public class TestAnnotationMethodClass {

    @Trace(operationName = "testMethod")
    public void testMethodWithOperationName() {
    }

    @Trace(operationName = "testMethod")
    @Tag(key = "username", value = "arg[0]")
    public void testMethodWithTag(String username) {
    }

    @Trace(operationName = "testMethod")
    @Tag(key = "username", value = "returnedObj.username")
    public User testMethodWithReturnTag(String username, Integer age) {
        return new User(username, age);
    }

    @Trace
    public void testMethodWithDefaultValue() {
    }

    @AllArgsConstructor
    private class User {
        private String username;
        private Integer age;
    }
}
