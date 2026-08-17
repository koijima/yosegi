# Yosegi File Format

This document describes the high-level physical organization of Yosegi data.

## Overview

A Spread is an in-memory structure. Before writing, its columns are converted into `ColumnBinary` objects. Multiple Spread representations are then grouped into blocks.

```text
Records
  -> Spread
  -> List<ColumnBinary>
  -> Block
  -> Yosegi file
```

## File Header

A Yosegi file begins with header information used to identify the format and block implementation. The standard format uses the `$CLM` magic and stores information required to select the block reader.

Persisted implementation identifiers are compatibility-sensitive; see [Compatibility](compatibility.md).

## Spread Flush

`YosegiRecordWriter` accumulates records in a Spread until configured size or row thresholds are reached. The Spread is then converted to column binaries and passed to the block writer.

The currently documented defaults are:

- Spread size threshold: 112 MiB
- Minimum Spread size: 16 MiB
- Maximum rows per Spread: 50,000
- Minimum maximum-row setting: 1,000

See [Configuration](configuration.md).

## ColumnBinary

`ColumnBinary` is the serialized intermediate representation of a column. Its metadata includes information such as:

- Binary Maker identifier
- Compressor identifier
- Column name and logical type
- Row count
- Data sizes and cardinality
- Binary start and length
- Child ColumnBinary objects for complex types

Metadata and actual column payload are separated when a block is constructed.

## Complex Columns

Nested, array, and Union structures are represented recursively through child ColumnBinary objects.

```text
SPREAD
├── STRING
└── INTEGER

ARRAY
└── element ColumnBinary

UNION
├── INTEGER
└── STRING
```

## ColumnBinaryTree

A block can contain binaries from multiple Spreads. `ColumnBinaryTree` organizes them by logical column path while retaining their Spread positions.

```text
age
├── Spread 0 -> missing
├── Spread 1 -> ColumnBinary
└── Spread 2 -> ColumnBinary
```

This organization is central to projection pushdown because metadata can identify the byte ranges belonging to selected logical paths.

## Block Structure

The standard pushdown-capable block contains, conceptually:

```text
Block
├── Block index
├── Spread count and row counts
├── Compressed column metadata
└── Column data
```

The exact byte layout is defined by the block implementation.

## Block Index

The block writer builds a `BlockIndexNode` hierarchy corresponding to logical column paths. This information is used later by predicate pushdown to obtain candidate Spread indexes.

## Column Metadata and Data

Column metadata records where encoded payload resides. The reader can use these offsets to load only selected byte ranges.

```text
metadata -> binaryStart / binaryLength -> BlockReadOffset -> selected reads
```

This is one of the mechanisms enabling physical I/O reduction for projection.

## Block Size

The standard writer uses a default block size of 64 MiB. Normal blocks are written according to the configured block sizing behavior; the final block is completed during writer close and may use variable-length output.

## Complete Write Path

```text
IParser
  -> YosegiRecordWriter
  -> Spread
  -> IColumnBinaryMaker
  -> ColumnBinary[]
  -> ColumnBinaryTree + BlockIndexNode
  -> Block
  -> YosegiWriter
  -> OutputStream
```

For encoding details, see [Encoding and Compression](encoding.md). For individual byte encodings, see [binary/](binary/).
