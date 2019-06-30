package com.vektor.pivot.contracts;


import java.util.List;
import java.util.function.Function;

/**
 * Interface representing a traversable tree-like data structure.
 */
public interface IAggregationNode {

    IAggregationNode getNode(String child);
    List<Integer> getValues();
    List<String> getChildrenLabels();
    Integer getFunctionResult();
    void aggregate(Function<List<Integer>, Integer> function);

    void add(List<String> path, int value);
    int size();

}
