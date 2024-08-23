package com.zcswl.user;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.zcswl.user.loader.InvokeClassLoader;
import com.zcswl.user.loader.ParamNameUtil;
import com.zcswl.user.loader.TypeWrapper;

import javax.security.sasl.SaslServer;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author xingyi
 * @date 2023/6/6
 */
public class JavaFunctionInvoke {


    public static void main(String[] args) throws ClassNotFoundException {
        Thread thread = new Thread();

        long l = Runtime.getRuntime().maxMemory();
        // 文件名
        String invokeFile = "/Users/zhoucg/IdeaProjects/unit-common/jar-package/target/jar-package-v0.0.1.jar";
        // 类名
        String className = "com.zcswl.MethodFunction";
        // 类方法
        String functionName = "SimpleFunction";

        // simple test invokeFile local
        // download from sftp and cache local file,after delete when deal finish
        File localFile = new File(invokeFile);


        File[] files = new File[] {localFile};
        URL[] urls =
                Arrays.stream(files)
                        .filter(file -> file.isFile() && file.getName().endsWith(".jar"))
                        .sorted()
                        .map(
                                file -> {
                                    try {
                                        return file.toURI().toURL();
                                    } catch (MalformedURLException e) {
                                        throw new RuntimeException("file to url error ", e);
                                    }
                                })
                        .toArray(URL[]::new);
        InvokeClassLoader invokeClassLoader = new InvokeClassLoader(urls, Thread.currentThread().getContextClassLoader());

        Class<?> aClass = invokeClassLoader.loadClass(className);
        Method[] declaredMethods = aClass.getDeclaredMethods();

        List<Method> invokeMethods = Arrays.stream(declaredMethods)
                .filter(method -> method.getName().equalsIgnoreCase(functionName))
                .collect(Collectors.toList());

        if (CollUtil.isEmpty(invokeMethods)) {
            // error
        }

        Method invokeMethod = CollUtil.getFirst(invokeMethods);

        List<TypeWrapper> paramNames = ParamNameUtil.getParamNames(invokeMethod, invokeClassLoader);
        String s = JSONUtil.toJsonStr(paramNames);
        System.out.println(s);

        Class<?> returnType = invokeMethod.getReturnType();
        System.out.println(returnType.getName());
        System.out.println(returnType.getTypeName());

        String arr = "[1,2,3]";
        JSONArray objects = JSONUtil.parseArray(arr);

    }
}
