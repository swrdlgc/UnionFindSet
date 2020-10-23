package com.swrd.unionfindset;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface IUnionFindSet<T> {
    int find(T x);
    void union(T x, T y);
    default boolean isConnected(T x, T y) {
        return find(x) == find(y);
    }

    int getSize();
    void group();

    default Map<T, Set<T>> getUnionSetMap(Stream<T> stream) {
        group();
        
        Map<Integer, Set<T>> tmp = new HashMap<>(); // key: group id
        return stream.collect(Collectors.toMap(k->k, x->{
            int px = find(x);
            if(!tmp.containsKey(px)) {
                tmp.put(px, new HashSet<>());
            }
            
            Set<T> s = tmp.get(px);
            s.add(x);
            return s;
        }));
    }

    Map<T, Set<T>> getUnionSetMap();

    default List<Set<T>> getUnionSets(Map<T, Set<T>> map) {
        return map.values().stream().collect(Collectors.toList());
    }

    default List<Set<T>> getUnionSets() {
        return getUnionSets(getUnionSetMap());
    }
}
