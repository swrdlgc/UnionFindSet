package com.swrd.unionfindset.any;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public final class FastAnyUnionFindSet<T> extends AnyUnionFindSet<T> {
    private List<T> list = new ArrayList<>();      // id->T

    @Override
    protected void addElement(T x) {
        super.addElement(x);
        list.add(x);
    }

    @Override
    public Map<T, Set<T>> getUnionSetMap() {
        //return getUnionSetMap(list.stream());
        Map<T, Set<T>> rv = new HashMap<>();
        unionFind.getUnionSetMap().entrySet().forEach( e -> {
            rv.put(list.get(e.getKey()), 
                    e.getValue().stream().map(i->list.get(i)).collect(Collectors.toSet())
                    );
        });
        return rv;
    }
}
