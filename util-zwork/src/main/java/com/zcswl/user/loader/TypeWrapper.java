package com.zcswl.user.loader;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author xingyi
 * @date 2023/6/6
 */
@Data
@Builder
public class TypeWrapper {

    private String paramName;

    private String paramType;

    private boolean array;

    private boolean collect;

    private List<TypeWrapper> child;

    public TypeWrapper setParamName(String paramName) {
        this.paramName = paramName;
        return this;
    }

    public TypeWrapper setParamType(String paramType) {
        this.paramType = paramType;
        return this;
    }

    public TypeWrapper setArray(boolean array) {
        this.array = array;
        return this;
    }

    public TypeWrapper setCollect(boolean collect) {
        this.collect = collect;
        return this;
    }

    public TypeWrapper setChild(List<TypeWrapper> child) {
        this.child = child;
        return this;
    }




}
