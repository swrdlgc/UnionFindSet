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

    int getSize();
    void group();

    Map<T, Set<T>> getUnionSetMap();
    List<Set<T>> getUnionSets(Map<T, Set<T>> map);
    List<Set<T>> getUnionSets();

    static <T> Map<T, Set<T>> getUnionSetMap(Stream<T> stream, IUnionFindSet<T> uf) {
        uf.group();

        Map<Integer, Set<T>> tmp = new HashMap<>();
        return stream.collect(Collectors.toMap(k->k, x->{
            int px = uf.find(x);
            if(!tmp.containsKey(px)) {
                tmp.put(px, new HashSet<>());
            }
            
            Set<T> s = tmp.get(px);
            s.add(x);
            return s;
        }));
    }
}
