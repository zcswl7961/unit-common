package com.zcswl.google.guice;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Scope;
import com.google.inject.Scopes;

/**
 * guice 设置对应的module
 * @author xingyi
 * @date 2021/9/11
 */
public class BindModule implements Module {
    @Override
    public void configure(Binder binder) {
        // 表示通过@Inject @DefaultAnnotation 注解注入对应的DefaultService2
        binder.bind(Service.class).annotatedWith(DefaultAnnotation.class)
                .to(DefaultService2.class).in(Scopes.SINGLETON);

        binder.bind(Service.class).to(DefaultService.class);
    }
}
