package com.common.jdk.lambda;

import java.util.HashMap;

/**
 * Created by zhoucg on 2019-01-28.
 */
public class HashMapListsJoiner<K,O> extends ListsJoiner<K,HashMap<String,O>> {

    private HashMapListsJoiner(RowCreator<HashMap<String, O>> rowCreator, RowUpdater rowUpdater) {
        super(rowCreator, rowUpdater);
    }

    public static <K,O> HashMapListsJoiner create() {
        return new HashMapListsJoiner<K,O>(() -> new HashMap<>(), (RowUpdater<HashMap<String, O>>) (o, name, obj) -> {
            o.put(name, (O) obj);
            return o;
        });
    }
}
