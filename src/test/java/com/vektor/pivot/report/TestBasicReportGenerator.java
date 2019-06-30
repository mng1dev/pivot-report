package com.vektor.pivot.report;

import com.vektor.pivot.contracts.IAggregationNode;
import com.vektor.pivot.contracts.IRowset;
import com.vektor.pivot.processing.Dataset;
import com.vektor.pivot.storage.AggregationLevel;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class TestBasicReportGenerator {

    private PivotReport report;

    @Before
    public void setUp(){
        IRowset inputData = new Dataset("src/test/resources/inputdataset.csv", ",");
        IAggregationNode rootNode = new AggregationLevel();
        Function<List<Integer>, Integer> SUM_FUNCTION = (
                integers -> integers.stream().mapToInt(i->i).sum()
        );
        this.report = new PivotReport(
                inputData,
                Arrays.asList("Nation", "eyes", "Hair"),
                SUM_FUNCTION,
                rootNode
        );
    }

    @Test
    public void TestBasicReportGeneratorOutput() {
        BasicReportGenerator generator = new BasicReportGenerator(this.report);
        generator.generate();
        String expected =
                "╔═════════╤═══════╤════════╤══════════════╗\n" +
                "║ Nation  │ eyes  │ Hair   │ Aggr. Result ║\n" +
                "╠═════════╪═══════╪════════╪══════════════╣\n" +
                "║ France  │ Blue  │        │ 1004         ║\n" +
                "╟─────────┼───────┼────────┼──────────────╢\n" +
                "║ France  │ Green │ Black  │ 857          ║\n" +
                "╟─────────┼───────┼────────┼──────────────╢\n" +
                "║ France  │ Green │ Blonde │ 288          ║\n" +
                "╟─────────┼───────┼────────┼──────────────╢\n" +
                "║ France  │ Green │        │ 1145         ║\n" +
                "╟─────────┼───────┼────────┼──────────────╢\n" +
                "║ France  │       │        │ 2149         ║\n" +
                "╟─────────┼───────┼────────┼──────────────╢\n" +
                "║ Germany │ Blue  │        │ 389          ║\n" +
                "╟─────────┼───────┼────────┼──────────────╢\n" +
                "║ Germany │ Brown │        │ 753          ║\n" +
                "╟─────────┼───────┼────────┼──────────────╢\n" +
                "║ Germany │ Dark  │ Black  │ 468          ║\n" +
                "╟─────────┼───────┼────────┼──────────────╢\n" +
                "║ Germany │ Dark  │ Brown  │ 103          ║\n" +
                "╟─────────┼───────┼────────┼──────────────╢\n" +
                "║ Germany │ Dark  │        │ 571          ║\n" +
                "╟─────────┼───────┼────────┼──────────────╢\n" +
                "║ Germany │ Green │ Brown  │ 168          ║\n" +
                "╟─────────┼───────┼────────┼──────────────╢\n" +
                "║ Germany │ Green │ Red    │ 1442         ║\n" +
                "╟─────────┼───────┼────────┼──────────────╢\n" +
                "║ Germany │ Green │        │ 1610         ║\n" +
                "╟─────────┼───────┼────────┼──────────────╢\n" +
                "║ Germany │       │        │ 3323         ║\n" +
                "╟─────────┼───────┼────────┼──────────────╢\n" +
                "║ Italy   │       │        │ 148          ║\n" +
                "╟─────────┼───────┼────────┼──────────────╢\n" +
                "║ Spain   │ Blue  │        │ 852          ║\n" +
                "╟─────────┼───────┼────────┼──────────────╢\n" +
                "║ Spain   │ Brown │        │ 778          ║\n" +
                "╟─────────┼───────┼────────┼──────────────╢\n" +
                "║ Spain   │ Dark  │        │ 907          ║\n" +
                "╟─────────┼───────┼────────┼──────────────╢\n" +
                "║ Spain   │ Green │        │ 359          ║\n" +
                "╟─────────┼───────┼────────┼──────────────╢\n" +
                "║ Spain   │       │        │ 2896         ║\n" +
                "╟─────────┼───────┼────────┼──────────────╢\n" +
                "║ TOTAL   │       │        │ 8516         ║\n" +
                "╚═════════╧═══════╧════════╧══════════════╝\n";
        assertEquals(expected, generator.getReportContent());
    }

}
