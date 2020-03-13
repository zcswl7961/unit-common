package com.zcswl.tools;

import com.zcswl.common.logging.Log;
import com.zcswl.common.logging.LogFactory;
import org.springframework.beans.BeanInstantiationException;
import org.springframework.util.Assert;
import org.springframework.util.ConcurrentReferenceHashMap;

import java.lang.reflect.Constructor;
import java.util.Collections;
import java.util.Set;

/**
 * 参考：org.springframework.beans.BeanUtils
 *
 * @author zhoucg
 * @date 2020-01-07 16:11
 */
public final class BeanUtils {

    private static final Log loggger = LogFactory.getLog(BeanUtils.class);

    /**
     * 未知类型
     */
    private static final Set<Class<?>> unknownEditorTypes =
            Collections.newSetFromMap(new ConcurrentReferenceHashMap<>(64));


    /**
     * new Instance by Class 通过一个无参的构造函数进行创建
     * @see Class#newInstance()
     * @param clazz
     * @param <T>
     * @return
     * @throws BeanInstantiationException
     */
    public static <T> T instantiate(Class<T> clazz) throws BeanInstantiationException {
        Assert.notNull(clazz, "Class must not be null");

        if(clazz.isInterface()) {
            throw new BeanInstantiationException(clazz, "Specified class is an interface");
        }

        try{
            //需要一个无参的公共构造函数
            return clazz.newInstance();
        } catch (InstantiationException ex) {
            throw new BeanInstantiationException(clazz, "Is it an abstract class?", ex);
        } catch (IllegalAccessException ex) {
            throw new BeanInstantiationException(clazz, "Is the constructor accessible?", ex);
        }

    }


    /**
     * Test
     * @param args
     * @throws NoSuchMethodException
     */
    public static void main(String[] args) throws NoSuchMethodException {

        /*TestBean instantiate = BeanUtils.instantiate(TestBean.class);
        Assert.isNull(instantiate,"获取");*/


        Constructor<TestBean> declaredConstructor = TestBean.class.getDeclaredConstructor();
        System.out.println(declaredConstructor);



    }


}
