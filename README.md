[![Build Status](https://travis-ci.com/zevektor/pivot-report.svg?branch=master)](https://travis-ci.com/zevektor/pivot-report)  [![codecov](https://codecov.io/gh/zevektor/pivot-report/branch/master/graph/badge.svg)](https://codecov.io/gh/zevektor/pivot-report)


# Pivot Report

## Overview

Basic implementation of all the required data structures to generate a pivot report starting from a CSV dataset.


## Usage Example:
```java
import com.vektor.pivot.client.PivotClient;

public class SampleApp {
    public static void main(String [] args) {
        PivotClient client = new PivotClient(
                "inputdata.csv",
                (Arrays).asList("Nation", "eyes", "Hair"),
                (integers -> integers.stream().mapToInt(i->i).sum()) // SUM FUNCTION
        );
        System.out.println(client.getPrintableReport());
    }
}
```

## Output Example:

```text
╔═════════╤═══════╤════════╤══════════════╗
║ Nation  │ eyes  │ Hair   │ Aggr. Result ║
╠═════════╪═══════╪════════╪══════════════╣
║ France  │ Blue  │        │ 1004         ║
╟─────────┼───────┼────────┼──────────────╢
║ France  │ Green │ Black  │ 857          ║
╟─────────┼───────┼────────┼──────────────╢
║ France  │ Green │ Blonde │ 288          ║
╟─────────┼───────┼────────┼──────────────╢
║ France  │ Green │        │ 1145         ║
╟─────────┼───────┼────────┼──────────────╢
║ France  │       │        │ 2149         ║
╟─────────┼───────┼────────┼──────────────╢
║ Germany │ Blue  │        │ 389          ║
╟─────────┼───────┼────────┼──────────────╢
║ Germany │ Brown │        │ 753          ║
╟─────────┼───────┼────────┼──────────────╢
║ Germany │ Dark  │ Black  │ 468          ║
╟─────────┼───────┼────────┼──────────────╢
║ Germany │ Dark  │ Brown  │ 103          ║
╟─────────┼───────┼────────┼──────────────╢
║ Germany │ Dark  │        │ 571          ║
╟─────────┼───────┼────────┼──────────────╢
║ Germany │ Green │ Brown  │ 168          ║
╟─────────┼───────┼────────┼──────────────╢
║ Germany │ Green │ Red    │ 1442         ║
╟─────────┼───────┼────────┼──────────────╢
║ Germany │ Green │        │ 1610         ║
╟─────────┼───────┼────────┼──────────────╢
║ Germany │       │        │ 3323         ║
╟─────────┼───────┼────────┼──────────────╢
║ Italy   │       │        │ 148          ║
╟─────────┼───────┼────────┼──────────────╢
║ Spain   │ Blue  │        │ 852          ║
╟─────────┼───────┼────────┼──────────────╢
║ Spain   │ Brown │        │ 778          ║
╟─────────┼───────┼────────┼──────────────╢
║ Spain   │ Dark  │        │ 907          ║
╟─────────┼───────┼────────┼──────────────╢
║ Spain   │ Green │        │ 359          ║
╟─────────┼───────┼────────┼──────────────╢
║ Spain   │       │        │ 2896         ║
╟─────────┼───────┼────────┼──────────────╢
║ TOTAL   │       │        │ 8516         ║
╚═════════╧═══════╧════════╧══════════════╝
```
