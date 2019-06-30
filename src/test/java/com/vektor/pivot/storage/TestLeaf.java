package com.vektor.pivot.storage;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import static org.junit.Assert.*;


public class TestLeaf {

    private static final Function<List<Integer>, Integer> SUM_FUNCTION = (
            integers -> integers.stream().mapToInt(i->i).sum()
    );

    @Test
    public void TestLeafAdd() {
        Leaf leaf = new Leaf();
        leaf.add(new ArrayList<>(Collections.emptyList()), 10);
        assertEquals(1, leaf.size());
        assertEquals(10, leaf.getValues().get(0).intValue());

    }

    @Test
    public void TestLeafHasNoLabels(){
        Leaf leaf = new Leaf();
        leaf.add(new ArrayList<>(Collections.emptyList()), 10);
        assertTrue(leaf.getChildrenLabels().isEmpty());
    }

    @Test
    public void TestLeafGetReturnsItself(){
        Leaf leaf = new Leaf();
        leaf.add(new ArrayList<>(Collections.emptyList()), 10);
        assertEquals(
                leaf.getNode(""),
                leaf
        );
    }

    @Test
    public void TestLeafAggregate(){
        Leaf leaf = new Leaf();
        leaf.add(new ArrayList<>(Collections.emptyList()), 10);
        leaf.add(new ArrayList<>(Collections.emptyList()), 20);
        leaf.aggregate(SUM_FUNCTION);
        assertEquals(30, leaf.getFunctionResult().intValue());
    }


}
