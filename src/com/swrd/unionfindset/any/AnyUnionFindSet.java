package com.swrd.unionfindset.any;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import com.swrd.unionfindset.IUnionFindSet;
import com.swrd.unionfindset.basic.DynamicUnionFindSet;
import com.swrd.unionfindset.basic.FixUnionFindSet;

public class AnyUnionFindSet<T> implements IUnionFindSet<T>{
    protected IUnionFindSet<Integer> unionFind;

    private AtomicInteger size = new AtomicInteger();
    private Map<T, Integer> map = new HashMap<>(); // T->id

    public AnyUnionFindSet(int capacity) {
        this.unionFind = new FixUnionFindSet(capacity);
    }

    public AnyUnionFindSet() {
        this.unionFind = new DynamicUnionFindSet();
    }

    protected void addElement(T x) {
        map.put(x, size.getAndIncrement());
    }

    private int getIndex(T x) {
        if (!map.containsKey(x)) {
            addElement(x);
        }
        return map.get(x);
    }

    @Override
    public int find(T x) {
        return unionFind.find(map.get(x));
    }

    @Override
    public void union(T x, T y) {
        int xi = getIndex(x);
        int yi = getIndex(y);
        
        unionFind.union(xi, yi);
    }

    @Override
    public int getSize() {
        //return size.get();
        return unionFind.getSize();
    }

    @Override
    public void group() {
        unionFind.group();
    }

    @Override
    public Map<T, Set<T>> getUnionSetMap() {
        return IUnionFindSet.getUnionSetMap(map.keySet().stream(), this);
    }

    @Override
    public List<Set<T>> getUnionSets(Map<T, Set<T>> map) {
        return map.values().stream().collect(Collectors.toList());
    }

    @Override
    public List<Set<T>> getUnionSets() {
        return getUnionSets(getUnionSetMap());
    }
}