package com.common.util.spring;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.ConcurrentReferenceHashMap;

import java.util.Collections;
import java.util.Set;

/**
 * Static convenience methods for JavaBeans: for instantiating beans,
 * checking bean property types, copying bean properties, etc.
 *
 * <p>Mainly for use within the framework, but to some degree also
 * useful for application classes.
 *
 * @author: zhoucg
 * @date: 2019-07-11
 */
public class BeanUtils {


    private static final Log logger = LogFactory.getLog(BeanUtils.class);

    private static final Set<Class<?>> unknownEditorTypes =
            Collections.newSetFromMap(new ConcurrentReferenceHashMap<>(64));


}
