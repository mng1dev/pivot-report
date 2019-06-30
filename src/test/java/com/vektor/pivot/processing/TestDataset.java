package com.vektor.pivot.processing;

import com.vektor.pivot.exceptions.InvalidInputException;
import com.vektor.pivot.exceptions.InvalidSeparatorException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class TestDataset  {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void TestDatasetInvalidSeparatorNull() {
        String path = "C:\\nonexistingfile";
        String message = "Invalid separator";

        expectedException.expect(InvalidSeparatorException.class);
        expectedException.expectMessage(message);

        new Dataset(path, null);
    }

    @Test
    public void TestDatasetInvalidSeparatorEmptyString() {
        String path = "C:\\nonexistingfile";
        String message = "Invalid separator";

        expectedException.expect(InvalidSeparatorException.class);
        expectedException.expectMessage(message);

        new Dataset(path, "");
    }

    @Test
    public void TestDatasetNonExistingFile() {
        String message = "Invalid input: File not found - C:\\nonexistingfile.";

        expectedException.expect(InvalidInputException.class);
        expectedException.expectMessage(message);

        new Dataset("C:\\nonexistingfile", ",");
    }

    @Test
    public void TestDatasetEmptyFile() {
        String message = "Invalid input: File is empty.";

        expectedException.expect(InvalidInputException.class);
        expectedException.expectMessage(message);

        new Dataset("src/test/resources/emptyfile.csv", ",");
    }

    @Test
    public void TestDatasetEmptyHeader() {
        String message = "Invalid input: Invalid header.";

        expectedException.expect(InvalidInputException.class);
        expectedException.expectMessage(message);

        new Dataset("src/test/resources/emptyheader.csv", ",");
    }

    @Test
    public void TestDatasetSingleColumn() {
        String message = "Invalid input: There should be at least one label and one numeric value.";

        expectedException.expect(InvalidInputException.class);
        expectedException.expectMessage(message);

        new Dataset("src/test/resources/singlecolumn.csv", ",");
    }

    @Test
    public void TestDatasetSingleLine() {
        String message = "Invalid input: There should be at least one line after the header.";

        expectedException.expect(InvalidInputException.class);
        expectedException.expectMessage(message);

        new Dataset("src/test/resources/singleline.csv", ",");
    }

    @Test
    public void TestDatasetNoNumericColumnFile() {
        String message = "Invalid input: The last element of each row should be of numeric type";

        expectedException.expect(InvalidInputException.class);
        expectedException.expectMessage(message);

        new Dataset("src/test/resources/nonumericvalues.csv", ",");
    }

    @Test
    public void TestDatasetMalformedFile() {
        String message = "Invalid input: There is a malformed tuple.";

        expectedException.expect(InvalidInputException.class);
        expectedException.expectMessage(message);

        new Dataset("src/test/resources/unevenlabels.csv", ",");
    }

    @Test
    public void TestDatasetProperFile() {
        Dataset dataset = new Dataset("src/test/resources/inputdataset.csv", ",");

        List<String> expectedCategories = Arrays.asList("Nation", "eyes", "Hair");

        assertEquals(expectedCategories, dataset.getCategories());
        assertEquals(16, dataset.getRows().size());

    }

}
