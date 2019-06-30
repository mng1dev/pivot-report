package com.vektor.pivot.processing;

import com.vektor.pivot.exceptions.InvalidInputException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

public class TestDatasetRow {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void TestDatasetRowInvalidCategories(){
        String message = "Invalid input: There is a malformed tuple.";

        expectedException.expect(InvalidInputException.class);
        expectedException.expectMessage(message);

        new DatasetRow("l1l2,l3,3", 4, ",");

    }

    @Test
    public void TestDatasetRowEmpty(){
        String message = "Invalid input: There is a malformed tuple.";

        expectedException.expect(InvalidInputException.class);
        expectedException.expectMessage(message);

        new DatasetRow("", 2, ",");

    }

    @Test
    public void TestDatasetRowNoNumericColumn(){
        String message = "Invalid input: The last element of each row should be of numeric type.";

        expectedException.expect(InvalidInputException.class);
        expectedException.expectMessage(message);

        new DatasetRow("a,b,c", 2, ",");

    }

    @Test
    public void TestDatasetRowGetters(){
        DatasetRow row = new DatasetRow("France,Blue,Black,498", 3, ",");

        assertArrayEquals(
                new String[]{"France", "Blue", "Black"},
                row.getLabels().toArray()
        );
        assertEquals(498, row.getValue());
    }
}
