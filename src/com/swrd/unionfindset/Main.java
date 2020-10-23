package com.swrd.unionfindset;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.swrd.unionfindset.any.AnyUnionFindSet;
import com.swrd.unionfindset.any.FastAnyUnionFindSet;
import com.swrd.unionfindset.basic.DynamicUnionFindSet;
import com.swrd.unionfindset.basic.FixUnionFindSet;

public class Main {
    private static void testInitUnionFindSet(IUnionFindSet<Integer> ufs) {
        for(int i = 0; i < ufs.getSize(); ++i) {
            assert(ufs.find(i) == i);
            assert(ufs.isConnected(i, i));
            for(int j = 0; j < i; ++j) {
                assert(!ufs.isConnected(i, j));
            }
        }
    }

    private static int printInitUnionFindSet(IUnionFindSet<Integer> ufs) {
        Map<Integer, Set<Integer>> map = ufs.getUnionSetMap();
        List<Set<Integer>> set = ufs.getUnionSets(map);
        System.err.println(set);
        System.err.println(map);
        return set.size();
    }

    public static void testFixUnionFindSet() {
        FixUnionFindSet ufs = new FixUnionFindSet(10);
        testInitUnionFindSet(ufs);
        
        int size = 10;
        ufs.union(0, 1); size--;
        ufs.union(1, 4); size--;
        ufs.union(2, 3); size--;
        ufs.union(0, 3); size--;
        assert(printInitUnionFindSet(ufs) == size);
        assert(ufs.isConnected(0, 4));
        assert(!ufs.isConnected(0, 5));
        
        ufs.union(8, 7); size--;
        ufs.union(6, 7); size--;
        ufs.union(9, 5); size--;
        ufs.union(6, 9); size--;
        assert(printInitUnionFindSet(ufs) == size);
        assert(ufs.isConnected(8, 9));
        assert(!ufs.isConnected(1, 5));
        
        ufs.union(4, 9); size--;
        assert(printInitUnionFindSet(ufs) == size);
        assert(ufs.isConnected(1, 5));
    }

    public static void testDynamicUnionFindSet() {
        DynamicUnionFindSet ufs = new DynamicUnionFindSet();
        testInitUnionFindSet(ufs);
        assert(ufs.find(2000) == 2000);
        testInitUnionFindSet(ufs);
        assert(!ufs.isConnected(1000, 1500));
        ufs.union(1000, 1500);
        assert(ufs.isConnected(1000, 1500));
    }
    
    public static List<Set<String>> testAnyUnionFindSet(AnyUnionFindSet<String> ufs) {
        String s1 = "A1";
        String s2 = "B2";
        String s3 = "B0";
        assert(!ufs.isConnected(s1, s2));
        ufs.union(s1, s2);
        assert(ufs.isConnected(s1, s2));
        ufs.union(s1, s3);
        assert(ufs.isConnected(s2, s3));
        List<Set<String>> set = ufs.getUnionSets();
        System.err.println(set);
        return set;
    }
    
    public static void main(String[] args) {
        testFixUnionFindSet();
        testDynamicUnionFindSet();
        assert(
                testAnyUnionFindSet(new AnyUnionFindSet<String>())
        .equals(
                testAnyUnionFindSet(new FastAnyUnionFindSet<String>())
        ));
    }
}
