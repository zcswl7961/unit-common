package com.common.util.annotation;

/**
 * Created by zhoucg on 2018-12-18.
 */
@FirstAnno("这是一个水果注解")
public class Anno {

    @ValueAnnotation()
    private String test = "";

    @MethondAnnotation()
    public String getDefault() {
        return  "get Default Annotation";
    }

    @MethondAnnotation(name = "wl",url = "www.wl.com")
    public String getDefin() {
        return "get defin Anntation";
    }
}
