# optimizing-merge-insertion-sort

Benchmarks a hybrid merge/insertion sort in Java to find the optimal cutoff parameter.

---

## About

This project is written in Java and explores how the cutoff threshold between merge sort and insertion sort affects runtime performance. For arrays smaller than the cutoff, insertion sort is used instead of continuing to recurse. The benchmark runs the sort across cutoff values 0 through 30, repeating each test multiple times and reporting average execution times so you can identify the best cutoff for large random integer arrays.

## Getting Started

### Prerequisites

- Java 21 or later
- Apache Maven 3.8 or later

### Building

**Unix**
```bash
mvn compile
```

**Windows**
```cmd
mvn compile
```

### Running

**Unix**
```bash
mvn exec:java -Dexec.mainClass="Main"
```

**Windows**
```cmd
mvn exec:java -Dexec.mainClass="Main"
```

Output is printed to stdout. Each test run shows the execution time (in milliseconds) for every cutoff value from 0 to 30, followed by the average across all test runs.

## Configuration

These constants are defined at the top of `main()` in `Main.java`. Edit them directly to change benchmark behavior.

| Constant        | Default     | Description                                                  |
|-----------------|-------------|--------------------------------------------------------------|
| `arraySize`     | `10000000`  | Number of elements in each generated test array              |
| `maxIntegerSize`| `10000000`  | Upper bound (inclusive) for randomly generated integers      |
| `tests`         | `10`        | Number of test iterations used to compute the average        |

The cutoff range (0 to 30 inclusive) is hardcoded in the inner loop in `main()`.

## License

MIT - see [LICENSE](LICENSE).
