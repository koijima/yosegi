# Yosegi Architecture

This document maps Yosegi's logical concepts to the main code-level layers.

## Write Path

```text
Application data
  -> IParser
  -> Spread
  -> IColumn
  -> IColumnBinaryMaker
  -> ColumnBinary
  -> IBlockWriter
  -> YosegiWriter
  -> OutputStream
```

## Read Path

```text
InputStream
  -> YosegiReader / block reader
  -> BlockIndex + ColumnBinary metadata
  -> predicate / projection pruning
  -> IColumnBinaryMaker
  -> IColumn / Spread
  -> IParser
  -> Application
```

## Message Layer

`IParser` separates input serialization from the storage model. JSON support can therefore convert JSON into `IParser` without making JSON the internal storage representation.

## Spread and Column Layer

`Spread` is the in-memory column-oriented representation. Column type is determined dynamically and columns are created as concrete values are encountered.

Complex columns recursively reuse the same machinery:

- Nested structures use Spread-backed columns.
- Arrays use a child Spread for elements plus parent/child row relationships.
- Union columns maintain child columns for different logical types.

## Encoding Layer

`IColumnBinaryMaker` converts logical columns to `ColumnBinary` and reconstructs them during reading. This layer is the boundary between logical data model and physical encoding.

Binary Maker and Compressor identifiers can be persisted and therefore require compatibility review.

## Block Layer

`ColumnBinaryTree` organizes ColumnBinary objects from multiple Spreads by logical path. `BlockIndexNode` organizes predicate-pruning indexes over the same logical hierarchy.

The block layer stores Spread row counts, index information, column metadata, and column payload.

## Read Optimization

```text
BlockIndexNode
  -> IExpressionNode
  -> candidate Spreads

ColumnNameNode
  -> ColumnBinaryTree
  -> BlockReadOffset
  -> selected column ranges
```

Predicate and projection optimizations should never alter logical results.

## Transformation Layer

Expand and Flatten operate on the read representation and can affect projection path translation because application-visible names can differ from physical stored paths.

## Extension Points

Important extension points include:

- `IParser`
- `IColumnBinaryMaker`
- Compressor implementations
- `IBlockWriter` / `IBlockReader`
- Expression, extractor, and filter implementations

## Persistent vs. Internal Classes

Before renaming or moving an implementation class, verify whether its fully qualified name is written into persistent metadata. Persisted identifiers are part of the compatibility surface even when the Java class otherwise appears internal.

## Debugging

For write problems, inspect values in this order:

```text
IParser -> Spread/IColumn -> ColumnBinary -> block metadata -> stored bytes
```

For read problems, reverse the path and additionally inspect candidate Spread indexes, projection names, and selected byte ranges when pushdown is involved.

See [Testing](testing.md) for layer-specific verification.
