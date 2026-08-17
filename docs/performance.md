# Performance Guide

Yosegi performance depends on data distribution, query shape, storage, encoding, compression, configuration, and JVM behavior. Measure representative workloads before tuning.

## Main Resources

```text
Storage: file size, transferred bytes
I/O: metadata and selected column ranges
CPU: encoding, compression, decompression, decoding
Memory: Spread construction and decoded structures
```

Optimizing one resource can increase another.

## Projection

Projection is especially valuable for wide datasets or large payload columns when queries require only a small subset of fields. Measure bytes avoided rather than only the number of columns skipped.

## Predicate Pruning

Predicate pruning is effective when Spread statistics separate the queried value ranges well. Overlapping or broad ranges provide little pruning benefit.

## Spread Size

Smaller Spreads can provide finer pruning granularity but increase metadata and processing overhead. Larger Spreads reduce structural overhead but make Spread-level pruning coarser and can increase in-memory working set.

## Block Size

Block size affects grouping, metadata frequency, and storage access patterns. Evaluate it using the production storage environment rather than assuming the same behavior for local disk, distributed filesystems, and object storage.

## Encoding and Cardinality

Encoding efficiency can depend on logical type, cardinality, repetition, value range, and data distribution. Evaluate realistic data rather than only synthetic sequential values.

## Compression

Compression trades CPU for fewer stored/transferred bytes. The best choice depends on whether the workload is primarily I/O-bound or CPU-bound and how selective projection is.

## Dynamic Schemas and Complex Data

Sparse changing schemas, Union columns, deep nesting, and large arrays can increase metadata, memory, and reconstruction cost. Record count alone is not a sufficient measure when arrays contain many elements.

## Expand

Expand can multiply downstream row count dramatically even when physical file reading is efficient. Measure both bytes read and rows produced.

## Write Path

```text
parse input
  -> build Spread
  -> convert columns
  -> encode
  -> compress
  -> build blocks
  -> write storage
```

Separate parsing cost from Yosegi encoding cost when the benchmark goal requires it.

## Benchmark Guidance

Use representative datasets and include at least:

- Full scan
- Narrow projection
- Selective predicate
- Non-selective predicate
- Nested projection
- Expand workload

Useful metrics include records/sec, physical bytes read, encoded file size, candidate/skipped Spreads, CPU, memory, and GC.

Prefer deterministic byte-count or structural assertions to tight wall-clock thresholds in normal tests.

## Recommended Tuning Order

1. Establish a baseline.
2. Verify projection actually skips unnecessary columns.
3. Verify predicate pruning actually eliminates Spreads.
4. Examine data distribution and statistics selectivity.
5. Evaluate Spread and Block layout.
6. Evaluate encoding and compression.
7. Measure memory and GC.
8. Repeat on production-like storage.

Avoid universal claims such as "always faster" unless accompanied by a reproducible benchmark methodology and environment.
