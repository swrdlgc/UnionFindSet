package com.swrd.unionfindset.basic;

public final class DynamicUnionFindSet extends FixUnionFindSet {
    private static final int CapacityIncrement = 1024;
    
    public DynamicUnionFindSet() {
        super(CapacityIncrement);
    }
    
    private void ensure(int newCapacity) {
        int capacity = getSize();
        if(capacity >= newCapacity) {
            return;
        }
        while(capacity < newCapacity) {
            capacity += CapacityIncrement;
        }
        
        int[] nparent = new int[capacity];
        int[] nweight = new int[capacity];
        
        int olength = getSize();
        System.arraycopy(parent, 0, nparent, 0, olength);
        System.arraycopy(weight, 0, nweight, 0, olength);
        
        this.parent = nparent;
        this.weight = nweight;
        init(olength, capacity);
    }
    
    @Override
    public int find(Integer x) {
        ensure(x);
        return super.find(x);
    }
    
    @Override
    public void union(Integer x, Integer y) {
        ensure(Math.max(x, y));
        super.union(x, y);
    }
}
