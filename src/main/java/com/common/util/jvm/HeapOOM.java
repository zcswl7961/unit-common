package com.common.util.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: zhoucg
 * @date: 2019-07-23
 */
public class HeapOOM {

    static class OOMObject {}

    public static void main(String[] args) {
        List<OOMObject> list = new ArrayList<>();
        while (true) {
            list.add(new OOMObject());
        }
    }
}
