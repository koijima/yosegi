# Yosegi

Yosegi is a schema-less columnar storage format.

It provides flexible data representation similar to JSON while supporting efficient column-oriented storage and reading.

## Why Yosegi?

Columnar storage is useful for large analytical datasets because readers can avoid loading columns that are not required. Many columnar formats expect a predefined schema at write time. Yosegi instead constructs its typed column structure dynamically from the records it receives.

### Key Features

- Schema-less writing
- Dynamic column structure
- Nested structures and arrays
- Union representation for changing field types
- Column-oriented storage
- Projection pushdown
- Predicate pushdown using block indexes
- Per-column binary encoding and compression
- Expand and Flatten operations for complex structures
- Block-based storage and processing

For the overall design, see the [Yosegi documentation](docs/) and [Yosegi Overview](docs/overview.md).

## Getting Started

### Java

See the [Java Quick Start](docs/java/quickstart.md).

### CLI

Command-line tools are provided by [yosegi-tools](https://github.com/yahoojapan/yosegi-tools).

### Apache Hadoop

Hadoop integration is provided by [yosegi-hadoop](https://github.com/yahoojapan/yosegi-hadoop).

### Apache Hive

Hive integration is provided by [yosegi-hive](https://github.com/yahoojapan/yosegi-hive).

### Apache Spark

Spark integration is provided by [yosegi-spark](https://github.com/yahoojapan/yosegi-spark).

## Documentation

Detailed documentation is available under [docs/](docs/).

The documentation covers:

- Logical data model and schema-less representation
- Physical file and block structure
- Binary encoding and compression
- Projection and predicate pushdown
- Statistics and block indexes
- Compatibility
- Configuration and limitations
- Expand and Flatten
- Error handling and performance
- Architecture and testing

## Building

### Requirements

- macOS or Linux
- Java 8 Update 92 or later (64-bit)
- Maven 3.3.9 or later

### Build

```console
mvn clean install
```

## Contributing

See [CONTRIBUTING.md](CONTRIBUTING.md).

## License

Yosegi is licensed under the [Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0).
