package com.zcswl.airlift;

import com.google.inject.Binder;
import com.google.inject.Module;

import static io.airlift.jaxrs.JaxrsBinder.jaxrsBinder;

/**
 * @author xingyi
 * @date 2021/9/11
 */
public class ServiceModule implements Module {
    @Override
    public void configure(Binder binder) {
        jaxrsBinder(binder).bind(ServiceResource.class);
    }
}
