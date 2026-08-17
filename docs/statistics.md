# Statistics and Block Indexes

Yosegi uses block indexes to answer a conservative question:

> Can this Spread possibly contain a value that satisfies the predicate?

The index is not intended to identify final matching rows.

## Conservative Evaluation

```text
proves no match -> skip
may match       -> candidate
cannot decide   -> candidate
```

This preserves correctness while allowing data skipping.

## Spread-Level Pruning

Indexes are associated with logical columns across Spreads. A predicate can therefore return candidate Spread indexes, for example:

```text
Spread 0: id 1..50       -> skip
Spread 1: id 51..100     -> skip
Spread 2: id 101..150    -> candidate
```

## BlockIndexNode

The index hierarchy follows logical column paths, including nested structures.

```text
root
├── id
└── user
    └── age
```

## Index Types

Yosegi provides type-specific block-index implementations, including boolean and ordered range-style indexes, as well as unsupported representations when safe pruning is unavailable.

Range indexes summarize boundaries such as minimum and maximum values. A value inside the range is only a possible match; it is not guaranteed to exist.

## Unsupported Indexes

Unsupported or inconclusive index information is not data corruption. It disables pruning for that path and causes conservative reading.

## Dynamic Schemas

A column may be absent from some Spreads. Index evaluation must preserve missing-column semantics and must not invent arbitrary range values for absent data.

## Encoding Relationship

Binary Makers can contribute block-index information. As a result, index capabilities can depend on the physical encoding implementation.

## Optimization Only

Statistics must not change logical query results. With useful statistics, less data is read; without useful statistics, more data is read and normal row-level processing still determines the result.

See [Projection and Predicate Pushdown](pushdown.md) for the complete read flow and [statistics/](statistics/) for implementation-specific details.
