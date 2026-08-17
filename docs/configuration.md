# Yosegi Configuration

Yosegi behavior is customized through `jp.co.yahoo.yosegi.config.Configuration` and related APIs.

This reference intentionally lists only property names and defaults that have been explicitly confirmed for the core library. Integration projects can define additional settings.

## Writer

### `spread.size`

| Item | Value |
|---|---|
| Scope | Writer |
| Default | 112 MiB |
| Minimum | 16 MiB |

Approximate Spread-size threshold used to flush accumulated records for binary conversion.

### `record.writer.max.rows`

| Item | Value |
|---|---|
| Scope | Writer |
| Default | 50000 |
| Minimum | 1000 |

Maximum row-count threshold for a Spread. A Spread is flushed when the configured size threshold or row threshold is reached.

### Block size

The standard writer uses a default block size of 64 MiB. The exact public configuration key should be documented only after confirming the corresponding core API/property definition.

## Reader

### `spread.reader.read.column.names`

| Item | Value |
|---|---|
| Scope | Reader |
| Purpose | Projection column selection |

Requested nested paths are converted into a column-selection hierarchy used by projection pushdown.

## Predicate Pushdown

Predicate pushdown is configured through expression/filter APIs rather than being described here as a single scalar property. See [Projection and Predicate Pushdown](pushdown.md).

## Expand and Flatten

Expand and Flatten are read-time transformations. Their exact property/API names should be added to this reference when confirmed as part of the public configuration surface.

## Encoding and Compression

Binary Maker and Compressor selection can affect persistent representation. Exact configuration identifiers should be listed here only after confirming their public definitions.

See [Encoding and Compression](encoding.md) and [Compatibility](compatibility.md).

## Configuration Categories

It is useful to distinguish:

- Read-time behavior: projection, predicate processing, Expand, Flatten
- Physical grouping: Spread and Block sizing
- Persistent encoding: Binary Maker and Compressor selection

Persistent encoding changes require additional compatibility review.
