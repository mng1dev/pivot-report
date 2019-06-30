package com.vektor.pivot.processing;

import com.vektor.pivot.contracts.IRow;
import com.vektor.pivot.exceptions.InvalidInputException;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A class implementing {@link IRow} that stores {@link Dataset} records.
 */
class DatasetRow implements IRow {
    private List<String> labels;
    private int value;

    /**
     * Class constructor
     * @param row The raw text of the {@link Dataset} row.
     * @param numCategories The number of expected category columns.
     * @param separator The column separator.
     */
    DatasetRow(String row, int numCategories, String separator) {
        String [] rowContent = row.split(separator);
        validateRow(row, numCategories, separator);

        int penultimateElementIndex = rowContent.length - 1;
        this.labels = new ArrayList<>(Arrays.asList(Arrays.copyOfRange(rowContent,0,penultimateElementIndex)));
        this.value = Integer.parseInt(rowContent[rowContent.length - 1]);
    }

    /**
     * Method to validate that the dataset row is not malformed.
     * @param row The raw text of the {@link Dataset} row.
     * @param numCategories The number of expected category columns.
     * @param separator The column separator.
     */
    private void validateRow(String row, int numCategories, String separator) {
        String [] rowContent = row.split(separator);

        boolean isWrongNumberOfCategories = rowContent.length -1 != numCategories;
        if (isWrongNumberOfCategories)
            throw new InvalidInputException("There is a malformed tuple");

        String numericValue = rowContent[rowContent.length-1];
        if (!NumberUtils.isParsable(numericValue))
            throw new InvalidInputException("The last element of each row should be of numeric type");
    }

    /**
     * @return The category labels contained in the row.
     */
    @Override
    public List<String> getLabels() {
        return this.labels;
    }

    /**
     * @return The value associated to the category labels in the row.
     */
    @Override
    public int getValue() {
        return this.value;
    }

}
