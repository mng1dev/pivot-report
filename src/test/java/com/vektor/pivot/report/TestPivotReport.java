package com.vektor.pivot.report;

import com.vektor.pivot.contracts.IRowset;
import com.vektor.pivot.contracts.IAggregationNode;
import com.vektor.pivot.exceptions.InvalidInputException;
import com.vektor.pivot.processing.Dataset;
import com.vektor.pivot.storage.AggregationLevel;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import static org.junit.Assert.*;

public class TestPivotReport {

    private static final Function<List<Integer>, Integer> SUM_FUNCTION = (
            integers -> integers.stream().mapToInt(i->i).sum()
    );

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private IRowset inputData;
    private IAggregationNode rootNode;

    @Before
    public void setUp(){
        this.inputData = new Dataset("src/test/resources/inputdataset.csv", ",");
        this.rootNode = new AggregationLevel();
    }


    @Test
    public void TestPivotReportEmptyHierarchy() {
        expectedException.expect(InvalidInputException.class);
        expectedException.expectMessage("Invalid input: Invalid aggregation order.");
        new PivotReport(
                this.inputData,
                Collections.emptyList(),
                SUM_FUNCTION,
                this.rootNode
        );
    }

    @Test
    public void TestPivotReportHierarchyTooLong() {
        expectedException.expect(InvalidInputException.class);
        expectedException.expectMessage("Invalid input: Invalid aggregation order.");

        new PivotReport(
                this.inputData,
                Arrays.asList("Nation", "eyes", "Hair", "Gender"),
                SUM_FUNCTION,
                this.rootNode
        );
    }

    @Test
    public void TestPivotReportHierarchyNonExistingCategory() {
        expectedException.expect(InvalidInputException.class);
        expectedException.expectMessage("Invalid input: Category \"Gender\" does not exist.");

        new PivotReport(
                this.inputData,
                Arrays.asList("Nation", "Gender"),
                SUM_FUNCTION,
                this.rootNode
        );
    }

    @Test
    public void TestPivotReportNullAggregationFunction() {
        expectedException.expect(InvalidInputException.class);
        expectedException.expectMessage("Invalid input: Aggregation function is null.");

        new PivotReport(
                this.inputData,
                Arrays.asList("Nation", "Hair"),
                null,
                this.rootNode
        );
    }

    @Test
    public void TestPivotReportCategoryIndexes() {
        PivotReport report = new PivotReport(
                this.inputData,
                Arrays.asList("Nation", "eyes", "Hair"),
                SUM_FUNCTION,
                this.rootNode
        );

        List<Integer> expectedIndexes = Arrays.asList(0, 1, 2);
        assertEquals(expectedIndexes, report.getCategoryIndexes());
    }

}
