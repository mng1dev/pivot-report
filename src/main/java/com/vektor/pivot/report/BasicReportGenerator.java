package com.vektor.pivot.report;


import com.mitchtalmadge.asciidata.table.ASCIITable;
import com.vektor.pivot.contracts.IAggregationNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that takes a {@link PivotReport} as input and by visiting the underlying {@link IAggregationNode}
 * stores its content in a {@link String} after formatting it.
 */
public class BasicReportGenerator extends ReportGenerator {

    private List<String> hierarchy;
    private IAggregationNode rootNode;
    private List<String[]> reportRows;

    /**
     * Class constructor
     * @param report The input {@link PivotReport} containing the aggregation results.
     */
    public BasicReportGenerator(PivotReport report) {
        super(report);
        hierarchy = report.getHierarchy();
        rootNode = report.getAggregationTree();
        reportRows = new ArrayList<>();
    }

    /**
     * Method to generate the report content.
     */
    @Override
    public void generate() {
        visitTree("",rootNode);
    }

    /**
     * @return The report content as a {@link String}.
     */
    @Override
    public String getReportContent() {
        List<String> tableHeaders = new ArrayList<>(this.hierarchy);
        tableHeaders.add("Aggr. Result");
        return ASCIITable.fromData(tableHeaders.toArray(new String[0]), reportRows.toArray(new String[0][0])).toString();
    }

    /**
     * Method to traverse the {@link IAggregationNode} and generate the human-readable report.
     * @param preamble Some input text, if needed for styling purposes.
     * @param node the currently visited {@link IAggregationNode}
     */
    @Override
    void visitTree(String preamble, IAggregationNode node) {

        if(node.getChildrenLabels().size() > 1) {
            for (String childLabel : node.getChildrenLabels()) {
                visitTree(preamble + childLabel + ",", node.getNode(childLabel));
            }
        }
        prepareReportRow(preamble, node);
    }



    private void prepareReportRow(String preamble, IAggregationNode node) {
        preamble += preamble.isEmpty()
                ? "TOTAL"
                : "";

        int numberOfCommas = (int) preamble.chars().filter(c -> c == ',').count();
        int missingCommas = this.hierarchy.size() - numberOfCommas;

        StringBuilder preambleBuilder = new StringBuilder(preamble);
        for (int i = 0; i < missingCommas; i++) {
            preambleBuilder.append(",");
        }

        preambleBuilder.append(node.getFunctionResult());
        String [] tableRow = preambleBuilder.toString().split(",");

        reportRows.add(tableRow);
    }

}
