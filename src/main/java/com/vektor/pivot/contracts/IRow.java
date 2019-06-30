package com.vektor.pivot.contracts;

import java.util.List;

/**
 * Interface representing a data structure to pair together a set of labels and the numeric value associated to them.
 */
public interface IRow {
    List<String> getLabels();
    int getValue();
}
