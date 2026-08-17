# Projection and Predicate Pushdown

Yosegi reduces data read in two complementary dimensions:

- Projection pushdown selects required columns.
- Predicate pushdown selects candidate Spreads using block indexes.

## Projection Pushdown

Requested column names are represented as a logical selection tree. Nested paths can be selected independently, for example `user.age` without `user.name`.

The reader first processes enough block metadata to locate selected columns. `ColumnBinaryTree` then provides byte ranges for required ColumnBinary payloads, which are represented by read offsets. Gaps between required ranges can be skipped.

```text
requested columns
  -> ColumnNameNode
  -> ColumnBinaryTree
  -> BlockReadOffset[]
  -> selected physical reads
```

Projection therefore reduces column payload I/O, not necessarily all metadata processing.

Dynamic schema is supported because the ColumnBinary tree records which Spread contains a given logical path.

## Predicate Pushdown

Predicate pushdown evaluates an expression against `BlockIndexNode` information before selected column payloads are fully loaded.

```text
predicate
  -> IExpressionNode
  -> BlockIndexNode
  -> candidate Spread indexes
```

If no Spread can match, the remainder of the block can be skipped. If only some Spreads can match, only those Spread positions participate in subsequent column loading.

## Conservative Semantics

Predicate pushdown is a pruning optimization, not a final row filter.

```text
index proves no match -> skip
index may match       -> read
index cannot decide   -> read
```

False positives are acceptable because they only cause extra reads. False negatives are not acceptable because they change query results.

## Compound Expressions

AND expressions intersect candidate Spread sets. Other expression types must preserve the same conservative correctness principle.

## Expand and Flatten

Projection setup can be affected by read-time transformations. Logical requested names may need to be translated back to physical column paths, and structural nodes required to reconstruct arrays or nested values must still be loaded.

See [Expand and Flatten](expand_and_flatten.md).

## Read Flow

```text
Block
  -> Block Index
  -> Predicate pruning
  -> Candidate Spreads
  -> Column metadata
  -> Projection pruning
  -> Required byte ranges
  -> Decode selected data
```

See [Statistics and Block Indexes](statistics.md) for index semantics.
