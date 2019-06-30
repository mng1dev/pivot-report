package com.vektor.pivot.contracts;

import java.util.List;

/**
 * Interface representing a data structure containing a list of {@link IRow}
 * and a header containing a list of categories.
 */
public interface IRowset {
    List<IRow> getRows();
    List<String> getCategories();
}
