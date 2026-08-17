# Encoding and Compression

Yosegi separates logical column types from their physical representation.

```text
Logical Column
  -> Binary Maker
  -> Encoded Representation
  -> Compressor
  -> ColumnBinary
```

## Binary Maker

`IColumnBinaryMaker` converts an in-memory logical column into `ColumnBinary` metadata and payload, and provides the corresponding reconstruction path for reading.

The Binary Maker identifier is persisted in ColumnBinary metadata, so physical encoding can vary independently from logical type.

## ColumnBinary

ColumnBinary records information required to decode a column, including logical type, maker, compressor, row count, sizes, cardinality, byte location, payload, and child binaries for complex structures.

## Primitive Columns

Primitive values are encoded according to the selected maker. The same logical type can have multiple possible physical encodings.

Encoding choice can depend on value distribution, cardinality, repetition, range, or writer configuration. Do not infer a specific encoding merely from the logical type.

## Complex Columns

SPREAD, ARRAY, and UNION representations compose recursively through child ColumnBinary objects.

```text
ARRAY
└── element ColumnBinary

UNION
├── INTEGER ColumnBinary
└── STRING ColumnBinary
```

## Compression

Compression is separate from logical encoding. A Compressor operates on the encoded representation and its identifier is also persisted.

Reading conceptually reverses the path:

```text
stored bytes
  -> Compressor
  -> encoded representation
  -> Binary Maker
  -> logical column
```

## Statistics

A Binary Maker can also provide Block Index information. Encoding choice can therefore influence available predicate-pruning statistics.

## Compatibility

Changing a persisted maker or compressor identifier, or changing the meaning of an existing encoding under the same identifier, can break existing files.

See [Compatibility](compatibility.md).

## Detailed Encodings

Byte-level documentation for individual encodings belongs under [binary/](binary/).
