package com.vektor.pivot.client;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class TestPivotClient {

    @Test
    public void TestPivotClientOutput(){

        Function<List<Integer>, Integer> COUNT_FUNCTION = (List::size);

        String expected =
                "╔═════════╤═══════╤══════════════╗\n" +
                "║ Nation  │ eyes  │ Aggr. Result ║\n" +
                "╠═════════╪═══════╪══════════════╣\n" +
                "║ France  │ Blue  │ 2            ║\n" +
                "╟─────────┼───────┼──────────────╢\n" +
                "║ France  │ Green │ 2            ║\n" +
                "╟─────────┼───────┼──────────────╢\n" +
                "║ France  │       │ 4            ║\n" +
                "╟─────────┼───────┼──────────────╢\n" +
                "║ Germany │ Blue  │ 1            ║\n" +
                "╟─────────┼───────┼──────────────╢\n" +
                "║ Germany │ Brown │ 1            ║\n" +
                "╟─────────┼───────┼──────────────╢\n" +
                "║ Germany │ Dark  │ 2            ║\n" +
                "╟─────────┼───────┼──────────────╢\n" +
                "║ Germany │ Green │ 3            ║\n" +
                "╟─────────┼───────┼──────────────╢\n" +
                "║ Germany │       │ 7            ║\n" +
                "╟─────────┼───────┼──────────────╢\n" +
                "║ Italy   │       │ 1            ║\n" +
                "╟─────────┼───────┼──────────────╢\n" +
                "║ Spain   │ Blue  │ 1            ║\n" +
                "╟─────────┼───────┼──────────────╢\n" +
                "║ Spain   │ Brown │ 1            ║\n" +
                "╟─────────┼───────┼──────────────╢\n" +
                "║ Spain   │ Dark  │ 1            ║\n" +
                "╟─────────┼───────┼──────────────╢\n" +
                "║ Spain   │ Green │ 1            ║\n" +
                "╟─────────┼───────┼──────────────╢\n" +
                "║ Spain   │       │ 4            ║\n" +
                "╟─────────┼───────┼──────────────╢\n" +
                "║ TOTAL   │       │ 16           ║\n" +
                "╚═════════╧═══════╧══════════════╝\n";

        PivotClient client = new PivotClient(
                "src/test/resources/inputdataset.csv",
                Arrays.asList("Nation", "eyes"),
                COUNT_FUNCTION
        );

        assertEquals(expected, client.getPrintableReport());

    }

}
