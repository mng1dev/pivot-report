package com.vektor.pivot.report;


import com.vektor.pivot.contracts.IAggregationNode;

/**
 * Abstract class that takes a {@link PivotReport} as input and traverses its {@link IAggregationNode} to represent
 * the information in a human readable manner.
 */
public abstract class ReportGenerator {

    ReportGenerator(PivotReport report) {

    }

    public abstract String getReportContent();
    abstract void visitTree(String preamble, IAggregationNode node);
    public abstract void generate();

}
