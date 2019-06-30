package com.vektor.pivot.client;

import com.vektor.pivot.processing.Dataset;
import com.vektor.pivot.report.BasicReportGenerator;
import com.vektor.pivot.report.ReportGenerator;
import com.vektor.pivot.report.PivotReport;
import com.vektor.pivot.storage.AggregationLevel;

import java.util.List;
import java.util.function.Function;

/**
 * A Client that generates a Pivot Report starting from a CSV dataset.
 */
public class PivotClient {

    private ReportGenerator generator;

    /**
     * The {@link PivotClient} constructor.
     * @param inputPath The path of the input CSV file
     * @param aggregationOrder The description of the aggregation hierarchy
     * @param aggregationFunction The aggregation function, of type {@link Function}
     */
    public PivotClient(
            String inputPath,
            List<String> aggregationOrder,
            Function<List<Integer>, Integer> aggregationFunction
    ) {
        Dataset dataset = new Dataset(inputPath, ",");
        AggregationLevel aggregationTree = new AggregationLevel();

        PivotReport report = new PivotReport(
                dataset,
                aggregationOrder,
                aggregationFunction,
                aggregationTree
        );

        generator = new BasicReportGenerator(report);
        generator.generate();
    }

    /**
     * Get the report in a human-readable format.
     * @return The report as a {@link String}, ready to be printed on screen or stored to a file.
     */
    public String getPrintableReport() {
        return generator.getReportContent();
    }

}
