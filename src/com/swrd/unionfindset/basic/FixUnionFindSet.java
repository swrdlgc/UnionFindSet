package com.swrd.unionfindset.basic;

import java.util.Map;
import java.util.Set;
import java.util.stream.IntStream;

import com.swrd.unionfindset.IUnionFindSet;

public class FixUnionFindSet implements IUnionFindSet<Integer>{

    protected int[] parent;
    protected int[] weight;

    protected void init(int s, int e) {
        for(int i = s; i < e; ++i) {
            parent[i] = i;
            weight[i] = 1;
        }
    }
    
    public FixUnionFindSet(int size) {
        this.parent = new int[size];
        this.weight = new int[size];

        init(0, size);
    }

    private void link(int i, int p) {
        parent[i] = p;
        weight[p] += weight[i];
        weight[i] = 0;
    }

    @Override
    public int find(Integer x) {
        while (x != parent[x]) {
            int p = parent[parent[x]];
            link(x, p);
            x = p;
        }
        return x;
    }

    @Override
    public void union(Integer x, Integer y) {
        int px = find(x);
        int py = find(y);

        if (px == py) {
            return;
        }

        if (weight[px] > weight[py]) {
            link(py, px);
        } else {
            link(px, py);
        }
    }

    @Override
    public int getSize() {
        return parent.length;
    }

    @Override
    public void group() {
        int size = getSize();
        for(int i = 0; i < size; ++i) {
            int pi = find(i);
            if(parent[i] != pi) {
                link(i, pi);
            }
        }
    }

    @Override
    public Map<Integer, Set<Integer>> getUnionSetMap() {
        return IUnionFindSet.getUnionSetMap(IntStream.range(0, getSize()).boxed(), this);
    }

    // debug
    public void print() {
        int size = getSize();
        for(int i = 0; i < size; ++i) {
            System.err.println(String.format("i=%d, p=%d, w=%d", i, parent[i], weight[i]));
        }
    }
}