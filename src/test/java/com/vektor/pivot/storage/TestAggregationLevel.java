package com.vektor.pivot.storage;

import com.vektor.pivot.contracts.IAggregationNode;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class TestAggregationLevel {

    private static final Function<List<Integer>, Integer> SUM_FUNCTION = (
            integers -> integers.stream().mapToInt(i->i).sum()
    );

    @Rule
    public ExpectedException expectedException = ExpectedException.none();


    @Test
    public void TestAggregationLevelSizeSingleLeaf(){
        AggregationLevel root = new AggregationLevel();
        root.add(new ArrayList<>(Collections.singletonList("a")), 10);
        assertEquals(1, root.size());
    }

    @Test
    public void TestAggregationLevelSizeSingleLeafMultipleRows(){
        AggregationLevel root = new AggregationLevel();
        root.add(new ArrayList<>(Collections.singletonList("a")), 10);
        root.add(new ArrayList<>(Collections.singletonList("a")), 10);
        assertEquals(2, root.size());
    }

    @Test
    public void TestAggregationLevelSizeMultipleLeaves(){
        AggregationLevel root = new AggregationLevel();
        root.add(new ArrayList<>(Collections.singletonList("a")), 10);
        root.add(new ArrayList<>(Collections.singletonList("b")), 10);
        assertEquals(2, root.size());
    }

    @Test
    public void TestAggregationLevelSizeMultipleLayers(){
        AggregationLevel root = new AggregationLevel();
        root.add(new ArrayList<>(Arrays.asList("a", "b")), 10);
        root.add(new ArrayList<>(Arrays.asList("c", "d")), 10);
        assertEquals(2, root.size());
    }

    @Test
    public void TestAggregationLevelChildren(){
        AggregationLevel root = new AggregationLevel();
        root.add(new ArrayList<>(Collections.singletonList("a")), 10);
        root.add(new ArrayList<>(Collections.singletonList("b")), 10);
        assertEquals(2, root.getChildrenLabels().size());
        for (String childLabel: root.getChildrenLabels()) {
            IAggregationNode child = root.getNode(childLabel);
            assertTrue(child instanceof Leaf);
        }
    }

    @Test
    public void TestAggregationLevelChildrenMultipleLayers(){
        AggregationLevel root = new AggregationLevel();
        root.add(new ArrayList<>(Arrays.asList("a","c")), 10);
        root.add(new ArrayList<>(Arrays.asList("b","a")), 10);
        assertEquals(2, root.getChildrenLabels().size());
        for (String childLabel: root.getChildrenLabels()) {
            IAggregationNode child = root.getNode(childLabel);
            assertTrue(child instanceof AggregationLevel);
        }
    }

    @Test
    public void TestAggregationLevelValues(){
        AggregationLevel root = new AggregationLevel();
        root.add(new ArrayList<>(Arrays.asList("a","c")), 10);
        root.add(new ArrayList<>(Arrays.asList("b","a")), 15);
        assertEquals(
                Arrays.asList(10, 15),
                root.getValues()
        );
    }

    @Test
    public void TestAggregationLevelGetLabels(){
        AggregationLevel root = new AggregationLevel();
        root.add(new ArrayList<>(Collections.singletonList("a")), 10);
        root.add(new ArrayList<>(Collections.singletonList("c")), 10);
        assertEquals(Arrays.asList("a", "c"), root.getChildrenLabels());
    }

    @Test
    public void TestAggregationLevelGetFullPath(){
        AggregationLevel root = new AggregationLevel();
        root.add(new ArrayList<>(Arrays.asList("a", "b")), 1);
        root.add(new ArrayList<>(Arrays.asList("c", "d")), 2);
        assertEquals(
                Collections.singletonList(1),
                root.getNode("a").getNode("b").getValues()
        );
    }

    @Test
    public void TestAggregationLevelGetKeyShouldExist(){
        AggregationLevel root = new AggregationLevel();
        root.add(new ArrayList<>(Collections.singletonList("a")), 1);

        expectedException.expect(AssertionError.class);

        root.getNode("b");
    }

    @Test
    public void TestAggregationLevelPartialPathSingleBranch(){
        AggregationLevel root = new AggregationLevel();
        root.add(new ArrayList<>(Arrays.asList("a", "b")), 1);
        root.add(new ArrayList<>(Arrays.asList("c", "d")), 2);
        assertEquals(
                Collections.singletonList(2),
                root.getNode("c").getValues()
        );
    }

    @Test
    public void TestAggregationLevelPartialPathAggregateBranches(){
        AggregationLevel root = new AggregationLevel();
        root.add(new ArrayList<>(Arrays.asList("a", "b")), 1);
        root.add(new ArrayList<>(Arrays.asList("a", "d")), 2);
        assertEquals(
                Arrays.asList(1, 2),
                root.getNode("a").getValues()
        );
    }

    @Test
    public void TestAggregationLevelAggregate(){
        AggregationLevel root = new AggregationLevel();
        root.add(new ArrayList<>(Arrays.asList("a", "b")), 10);
        root.add(new ArrayList<>(Arrays.asList("a", "c")), 20);
        root.aggregate(SUM_FUNCTION);
        assertEquals(30, root.getFunctionResult().intValue());
    }

    @Test
    public void TestAggregationLevelFunctionResultNotNull(){
        AggregationLevel root = new AggregationLevel();

        expectedException.expect(AssertionError.class);

        assertEquals(30, root.getFunctionResult().intValue());
    }
}
