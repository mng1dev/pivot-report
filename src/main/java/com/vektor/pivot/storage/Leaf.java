package com.vektor.pivot.storage;

import com.vektor.pivot.contracts.IAggregationNode;

import java.util.*;
import java.util.function.Function;

/**
 * Class implementing {@link IAggregationNode} that represents a leaf node, where the values of the more
 * granular aggregation levels are stored.
 */
class Leaf implements IAggregationNode {

    private List<Integer> values;
    private Integer functionResult;

    /**
     * Class constructor
     */
    Leaf() {
        values = new ArrayList<>();
    }

    /**
     * Method to store a numeric value.
     * The "path" parameter is an empty list, meaning the more granular aggregation level has been reached.
     * @param path The remaining aggregation level that need to be traversed before inserting the value. Expected to be empty.
     * @param value The value associated to the category labels.
     */
    @Override
    public void add(List<String> path, int value) {
        assert path.isEmpty();
        values.add(value);
    }

    /**
     * Method to return the {@link Leaf} itself, since there are no child nodes. Unused method.
     * @param path the desired child node.
     * @return the current {@link Leaf}
     */
    @Override
    @Deprecated
    public IAggregationNode getNode(String path) {
        return this;
    }

    /**
     * @return The total number of values included in this {@link Leaf}
     */
    @Override
    public int size() {
        return values.size();
    }

    /**
     * @return The numeric values belonging to the aggregation level of the current {@link Leaf}.
     */
    @Override
    public List<Integer> getValues() {
        return this.values;
    }

    /**
     * @return The labels of the children nodes of this {@link Leaf}, which consist of an empty list.
     */
    @Override
    public List<String> getChildrenLabels() {
        return Collections.unmodifiableList(Collections.emptyList());
    }

    /**
     * @return The result of the aggregation function, if already applied.
     */
    @Override
    public Integer getFunctionResult() {
        assert this.functionResult != null;
        return this.functionResult;
    }

    /**
     * Applies the aggregation function to the values contained in the current {@link AggregationLevel}.
     * @param function The aggregation function that will be applied.
     */
    @Override
    public void aggregate(Function<List<Integer>, Integer> function) {
        this.functionResult = function.apply(this.getValues());
    }

}
