package com.vektor.pivot.processing;

import com.vektor.pivot.contracts.IRow;
import com.vektor.pivot.contracts.IRowset;
import com.vektor.pivot.exceptions.InvalidInputException;
import com.vektor.pivot.exceptions.InvalidSeparatorException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

/**
 * A class implementing {@link IRowset} that parses and loads the input data.
 */
public class Dataset implements IRowset {

    private List<String> categories;
    private List<IRow> rows;

    /**
     * @return The list of categories (a.k.a. the header) contained in the dataset.
     */
    @Override
    public List<String> getCategories() {
        return categories;
    }

    /**
     * @return The parsed dataset as a list of {@link IRow}.
     */
    @Override
    public List<IRow> getRows() {
        return Collections.unmodifiableList(rows);
    }

    /**
     * Class constructor.
     * @param filePath The path of the input dataset file
     * @param separator A custom separator to determine columns in the dataset.
     */
    public Dataset(String filePath, String separator) {
        validateSeparator(separator);
        try (Stream<String> inputLines = Files.lines(Paths.get(filePath))) {

            Iterator<String> inputIterator = inputLines.iterator();
            setCategories(inputIterator.next(), separator);

            validateCategories();

            rows = new ArrayList<>();

            inputIterator.forEachRemaining((row) -> {
                DatasetRow parsedRow = new DatasetRow(row, categories.size(), separator);
                rows.add(parsedRow);
            });

            checkTotalRows(rows.size());

        } catch (IOException e) {

            String errorMessage = String.format("File not found - %s", filePath);
            throw new InvalidInputException(errorMessage);

        } catch (NoSuchElementException e) {
            throw new InvalidInputException("File is empty");
        }
    }

    /**
     * Validation checks for the separator.
     * @param separator The {@link Dataset} column separator.
     */
    private void validateSeparator(String separator) {
        if (separator == null || separator.isEmpty())
            throw new InvalidSeparatorException("Invalid separator");
    }

    /**
     * Method to populate the categories field.
     * @param header The first line of the input dataset.
     * @param separator The column separator.
     */
    private void setCategories(String header, String separator) {
        if (!header.isEmpty()) {
            String[] allColumns = header.split(separator);
            String [] categories = Arrays.copyOfRange(allColumns, 0, allColumns.length - 1);
            this.categories = Arrays.asList(categories);
        }
    }

    /**
     * Method to check if the input categories are a non-empty list and are at least two.
     */
    private void validateCategories() {
        if (this.categories == null)
            throw new InvalidInputException("Invalid header.");
        else if (this.categories.size() < 2)
            throw new InvalidInputException("There should be at least one label and one numeric value");
    }

    /**
     * Method to check that the input file has at least one row containing records.
     * @param totalRows The total number of rows in the {@link Dataset}
     */
    private void checkTotalRows(int totalRows) {
        if (totalRows == 0)
            throw new InvalidInputException("There should be at least one line after the header");
    }
}
