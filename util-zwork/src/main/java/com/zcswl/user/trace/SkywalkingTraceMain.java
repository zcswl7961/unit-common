package com.zcswl.user.trace;

import org.apache.skywalking.apm.agent.core.boot.ServiceManager;
import org.apache.skywalking.apm.agent.core.plugin.interceptor.enhance.EnhancedInstance;
import org.apache.skywalking.apm.toolkit.activation.trace.TraceAnnotationMethodInterceptor;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author xingyi
 * @date 2023/6/13
 */
public class SkywalkingTraceMain {

    public static void main(String[] args) throws Throwable {


        ServiceManager.INSTANCE.boot();
        EnhancedInstance enhancedInstance = new EnhancedInstance() {
            @Override
            public Object getSkyWalkingDynamicField() {
                return null;
            }

            @Override
            public void setSkyWalkingDynamicField(Object o) {

            }
        };
        TraceAnnotationMethodInterceptor methodInterceptor = new TraceAnnotationMethodInterceptor();

        Method testMethodWithDefaultValue = TestAnnotationMethodClass.class.getDeclaredMethod("testMethodWithDefaultValue");
        methodInterceptor.beforeMethod(enhancedInstance, testMethodWithDefaultValue, null, null, null);
        methodInterceptor.afterMethod(enhancedInstance, testMethodWithDefaultValue, null, null, null);

        /*assertThat(storage.getTraceSegments().size(), is(1));
        TraceSegment traceSegment = storage.getTraceSegments().get(0);
        List<AbstractTracingSpan> spans = SegmentHelper.getSpans(traceSegment);
        assertThat(spans.size(), is(1));
        AbstractTracingSpan tracingSpan = spans.get(0);
        assertThat(tracingSpan.getOperationName(), is(TestAnnotationMethodClass.class.getName() + "." + testMethodWithDefaultValue.getName() + "()"));
        SpanAssert.assertLogSize(tracingSpan, 0);
        SpanAssert.assertTagSize(tracingSpan, 0);*/
    }
}
