# Yosegi Documentation

Yosegi is a schema-less columnar storage format. This documentation describes its logical data model, physical file representation, query optimizations, configuration, runtime behavior, and developer architecture.

## Getting Started

- [Java Quick Start](java/quickstart.md) — write and read Yosegi data using the Java API.
- [Yosegi Overview](overview.md) — understand the overall architecture and major features.

## Concepts

- [Yosegi Data Model](data_model.md) — dynamic fields, primitive values, nested structures, arrays, and Union columns.
- [Expand and Flatten](expand_and_flatten.md) — read-time transformations for arrays and nested structures.

## File Format

- [Yosegi File Format](file_format.md) — how Spreads become ColumnBinary data, blocks, and files.
- [Encoding and Compression](encoding.md) — Binary Makers, Compressors, and physical column representation.
- [Binary Format Documentation](binary/) — detailed encoding-specific documentation.
- [Yosegi Compatibility](compatibility.md) — compatibility boundaries for files, blocks, encodings, and APIs.

## Query Optimization

- [Projection and Predicate Pushdown](pushdown.md) — how Yosegi avoids unnecessary columns and Spreads.
- [Statistics and Block Indexes](statistics.md) — conservative pruning semantics and index behavior.
- [Detailed Statistics Documentation](statistics/) — implementation-specific statistics documentation.

## Reference

- [Configuration Reference](configuration.md)
- [Limitations and Data Semantics](limitations.md)
- [Error Handling](error_handling.md)
- [Performance Guide](performance.md)

## Java API

- [Java Documentation](java/)
- [Java Quick Start](java/quickstart.md)

## Apache Arrow

- [Apache Arrow Documentation](arrow/)

## Development

- [Architecture](architecture.md)
- [Testing](testing.md)
- [Development Guide](developing.md)
- [Code Conventions](code_conventions.md)

Repository-level information:

- [Project README](../README.md)
- [Contributing to Yosegi](../CONTRIBUTING.md)

## Related Projects

- [yosegi-tools](https://github.com/yahoojapan/yosegi-tools)
- [yosegi-hadoop](https://github.com/yahoojapan/yosegi-hadoop)
- [yosegi-hive](https://github.com/yahoojapan/yosegi-hive)
- [yosegi-spark](https://github.com/yahoojapan/yosegi-spark)

## Recommended Reading Order

For understanding Yosegi internals:

1. [Overview](overview.md)
2. [Data Model](data_model.md)
3. [Encoding and Compression](encoding.md)
4. [File Format](file_format.md)
5. [Projection and Predicate Pushdown](pushdown.md)
6. [Statistics and Block Indexes](statistics.md)
7. [Compatibility](compatibility.md)
8. [Architecture](architecture.md)
9. [Testing](testing.md)
