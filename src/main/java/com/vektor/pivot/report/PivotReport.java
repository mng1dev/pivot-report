package com.vektor.pivot.report;

import com.vektor.pivot.contracts.IRowset;
import com.vektor.pivot.contracts.IAggregationNode;
import com.vektor.pivot.exceptions.InvalidInputException;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Class that populates the {@link IAggregationNode} and calculates its aggregations.
 */
public class PivotReport {

    private IRowset dataset;
    private List<String> hierarchy;
    private Function<List<Integer>, Integer> aggregationFunction;

    private IAggregationNode aggregationTree;

    /**
     * @return The description of the aggregation hierarchy
     */
    List<String> getHierarchy() {
        return hierarchy;
    }

    /**
     * @return The {@link IAggregationNode} containing dataset values and aggregation function results.
     */
    IAggregationNode getAggregationTree() {
        return aggregationTree;
    }

    /**
     * Class constructor
     * @param rowSet The parsed input dataset.
     * @param aggregationOrder The description of the aggregation hierarchy
     * @param aggregationFunction The aggregation function, of type {@link Function}
     * @param aggregationTree The {@link IAggregationNode} where dataset values and aggregation results will be stored.
     */
    public PivotReport(
            IRowset rowSet,
            List<String> aggregationOrder,
            Function<List<Integer>, Integer> aggregationFunction,
            IAggregationNode aggregationTree
    ) {
        this.dataset = rowSet;

        validateHierarchy(aggregationOrder, this.dataset.getCategories());
        this.hierarchy = aggregationOrder;
        this.aggregationFunction = aggregationFunction;
        validateAggregationFunction();

        this.aggregationTree = aggregationTree;
        populateAggregationTree();

        generateReport();
    }

    /**
     * Method that triggers the calculation of the aggregation function at each aggregation level.
     */
    private void generateReport() {
        calculateAggregations(this.getAggregationTree());
    }

    /**
     * Recursive method to traverse the tree and apply the aggregation function on each of its children nodes.
     * @param current the currently visited node.
     */
    private void calculateAggregations(IAggregationNode current) {
        for (String child: current.getChildrenLabels()) {
            calculateAggregations(current.getNode(child));
        }
        current.aggregate(this.aggregationFunction);
    }

    /**
     * Method to populate the {@link IAggregationNode}.
     */
    private void populateAggregationTree() {
        List<Integer> categoryIndexes = getCategoryIndexes();

        this.dataset.getRows().forEach((row -> {
            List<String> hierarchyLabels = categoryIndexes.stream()
                    .map(index -> row.getLabels().get(index))
                    .collect(Collectors.toCollection(ArrayList::new));
            aggregationTree.add(hierarchyLabels, row.getValue());
        }));
    }

    /**
     * @return The ordinal number of the aggregation order taken from the {@link IRowset} header.
     */
    List<Integer> getCategoryIndexes() {
        return this.hierarchy.stream().map(
                (category -> this.dataset.getCategories().indexOf(category))
        ).collect(Collectors.toList());
    }

    /**
     * Method to check the passed aggregation order is nonempty and coherent with the available categories.
     * @param hierarchy The aggregation order
     * @param categories The categories available in the {@link IRowset}
     */
    private void validateHierarchy(List<String> hierarchy, List<String> categories) {
        if (hierarchy.isEmpty() || hierarchy.size() > categories.size())
            throw new InvalidInputException("Invalid aggregation order");

        for (String category : hierarchy) {
            if (!categories.contains(category))
                throw new InvalidInputException(String.format("Category \"%s\" does not exist", category));
        }
    }

    /**
     * Method to validate that the input aggregation function is not null.
     */
    private void validateAggregationFunction() {
        if (this.aggregationFunction == null)
            throw new InvalidInputException("Aggregation function is null");
    }


}
