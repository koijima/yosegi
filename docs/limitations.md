# Limitations and Data Semantics

Yosegi's dynamic data model provides flexibility, but applications should not assume a fixed schema or source-format semantics that the column representation does not explicitly guarantee.

## Dynamic Fields

Records can contain different field sets. New fields can appear after earlier rows have already been processed.

## Missing and Null

Primitive columns use null-like default positions for rows without registered values, but missing-value behavior should not be generalized across every column type.

Applications that require a strict distinction between "field absent" and "field explicitly null" should verify round-trip behavior for their specific parser/formatter path.

## Empty Arrays and Empty Structures

Empty arrays or nested structures do not necessarily establish a concrete child type. Array defaults and primitive defaults also differ, so applications should explicitly test any semantics that depend on distinguishing missing from empty values.

## Changing Types

The same field can contain multiple logical types and become a Union representation. This applies recursively inside nested structures and arrays.

Applications that require a fixed type must validate or normalize input themselves.

## Heterogeneous Arrays

Arrays can contain dynamically typed elements. External systems may impose stricter homogeneous-array requirements even when Yosegi core can represent the data.

## Field Ordering

Object field order should not be treated as semantically significant or expected to round-trip identically. Access fields by name.

## Persisted Implementations

Binary Maker, Compressor, and block implementation identifiers can be persisted. Removing or renaming required implementations can make existing files unreadable.

## Corrupt or Truncated Files

Malformed structural metadata, invalid offsets, or truncated required payload can prevent decoding. Projection does not guarantee recovery from arbitrary corruption elsewhere in the same block because structural metadata is still required.

## Pushdown Limitations

Not every predicate can be pruned. Unsupported or inconclusive statistics cause conservative reads rather than incorrect skipping.

## Performance Trade-offs

Spread size, block size, encoding, compression, dynamic schemas, arrays, and transformations can all affect CPU, memory, metadata overhead, and I/O. There is no universal optimal setting.

See [Performance Guide](performance.md).

## Integration Restrictions

Hadoop, Hive, Spark, Arrow, or other integrations can impose stricter type and schema rules than the core Yosegi data model. Core representability does not guarantee every integration can expose the value without transformation.
