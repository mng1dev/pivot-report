package com.vektor.pivot.storage;

import com.vektor.pivot.contracts.IAggregationNode;

import java.util.*;
import java.util.function.Function;

/**
 * Class implementing {@link IAggregationNode} that represents a n-ary tree node, each depth level corresponds to the
 * values of the subsequent aggregation level.
 */
public class AggregationLevel implements IAggregationNode {

    private final Map<String, IAggregationNode> children;
    private Integer functionResult;

    /**
     * Constructor.
     */
    public AggregationLevel() {
        children = new TreeMap<>();
    }

    /**
     * @return The numeric values belonging to the aggregation level of the current {@link AggregationLevel}.
     */
    @Override
    public List<Integer> getValues() {
        List<Integer> childrenValues = new ArrayList<>();
        for (IAggregationNode child: children.values()) {
            childrenValues.addAll(child.getValues());
        }
        return childrenValues;
    }

    /**
     * @return The {@link List} of labels for the next category in the hierarchical order.
     */
    @Override
    public List<String> getChildrenLabels() {
        return Collections.unmodifiableList(new ArrayList<>(children.keySet()));
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

    /**
     * Method to add a new child {@link IAggregationNode} to the current one.
     * If the "path" parameter contains only one element, it will create a {@link Leaf} to store the actual value.
     * Otherwise another {@link AggregationLevel} is created.
     * @param path The remaining aggregation level that need to be traversed before inserting the value.
     * @param value The value associated to the category labels.
     */
    @Override
    public void add(List<String> path, int value) {
        String label = path.remove(0);

        if (!this.children.containsKey(label)) {
            IAggregationNode treeNode = path.isEmpty()
                    ? new Leaf()
                    : new AggregationLevel();

            this.children.put(
                    label,
                    treeNode
            );
        }

        this.children.get(label).add(path, value);
    }

    /**
     * @param path The label of the wanted {@link IAggregationNode}.
     * @return The child ${@link IAggregationNode} labelled as requested, if available.
     */
    @Override
    public IAggregationNode getNode(String path) {
        assert this.children.containsKey(path);
        return this.children.get(path);
    }


    /**
     * @return The total number of values included in this {@link AggregationLevel}
     */
    @Override
    public int size() {
        return children.values().parallelStream().mapToInt(IAggregationNode::size).sum();
    }
}
