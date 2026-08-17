# Testing Yosegi

Yosegi tests should protect logical correctness, persistent compatibility, optimization safety, and failure behavior.

## Test by Layer

```text
Parser change        -> message conversion tests
Spread/Column change -> data-model tests
Binary Maker change  -> encoding + compatibility tests
Block change         -> file + pushdown tests
Index/expression     -> pruning correctness tests
Persisted identifier -> old-file compatibility tests
```

## Round Trips

Current writer -> current reader round trips verify internal consistency, but they do not prove backward compatibility. Both sides can change in the same incompatible way and still pass.

## Compatibility Fixtures

Backward compatibility should be tested using files written by the actual older release and stored as immutable test resources.

```text
released writer -> fixture.yosegi -> current reader -> expected logical records
```

Do not regenerate compatibility fixtures with the current writer during tests.

## Data-Model Coverage

Cover primitive types, null and missing values, dynamic fields, type changes/Union, nested structures, arrays, heterogeneous array elements, and nested/recursive combinations.

## Binary Maker Coverage

Test logical round trip plus relevant ColumnBinary metadata, boundary values, sparse/null values, cardinality patterns, child-binary structure, and Compressor integration.

## Block and ColumnBinaryTree Coverage

Use multiple Spreads with columns appearing and disappearing between Spreads. Verify that logical paths remain aligned with the correct Spread positions and that projected read offsets select only required data.

## Projection Differential Testing

Compare:

```text
full read -> extract requested fields
```

with:

```text
projected read
```

The logical results must match. Where possible, separately assert reduced selected byte ranges or physical bytes read.

## Predicate Differential Testing

Compare:

```text
read without pruning -> apply predicate
```

with:

```text
read with pruning -> apply predicate
```

Final result sets must be equal.

False positives are acceptable; false negatives are not.

## Unsupported Indexes

An unsupported or inconclusive index must retain candidate data rather than return an unsafe empty candidate set.

## Expand and Flatten

Verify output values and row count for Expand, nesting changes for Flatten, and equivalence between full-read transformation and projected transformed reads.

## Failure Tests

Cover malformed or truncated headers, block metadata, ColumnBinary metadata, compressed data, payload, unknown Binary Makers, unknown Compressors, and failures from the underlying output stream.

Malformed input should not silently produce incorrect records.

## Performance Tests

Keep correctness tests separate from unstable timing assertions. Prefer deterministic metrics such as selected byte ranges, physical bytes read, candidate Spread count, or encoded size. Use dedicated benchmarks for throughput and latency.

## Persistent-Change Checklist

Before merging a persistent-format-related change, verify at least:

- Current writer -> current reader round trip
- Existing compatibility fixtures -> current reader
- Primitive, null/missing, nested, array, and Union cases
- Projection correctness
- Predicate correctness
- Unsupported-index fallback
- Relevant malformed-input behavior
- Binary Maker / Compressor identification
- Documentation updates

## Run Tests

```console
mvn test
```

See [Architecture](architecture.md), [Compatibility](compatibility.md), and [Performance Guide](performance.md).
