# Error Handling

Applications should distinguish unsupported optimizations, unsupported persistent representations, corrupt data, and ordinary I/O failures.

## General Principle

```text
unsupported optimization -> conservative fallback when supported
unsupported representation -> fail
corrupt required data -> fail
transient I/O failure -> application/storage retry policy
```

A reader should not guess how to interpret an unknown persistent encoding.

## Invalid or Truncated Files

Header, block metadata, offsets, compressed metadata, and column payload are all required according to the paths being read. Truncated or structurally inconsistent data should be treated as incomplete or corrupt rather than silently decoded.

## Unsupported Statistics

An unsupported Block Index is not file corruption. It means the predicate cannot be safely pruned using that index and the reader should keep the corresponding candidate data.

## Unknown Binary Maker or Compressor

A logical type alone is not enough to infer an unknown physical encoding or compression algorithm. If the persisted implementation is unavailable, decoding should fail rather than substitute another implementation.

## Complex Columns

Failures inside a child ColumnBinary can make the requested parent ARRAY, SPREAD, or UNION impossible to reconstruct. Error handling should therefore be considered recursively.

## Projection and Corruption

Projection can avoid reading unrelated payload, but the reader still needs enough metadata to locate selected columns. Projection does not guarantee partial recovery from arbitrary corruption elsewhere in the block.

## Writer Failures

Writer close is part of completing a valid file because buffered Spread data and the final block can still need to be emitted. If close fails, applications should treat the output as incomplete.

When reliability matters, write to temporary output and publish or commit the file only after successful completion.

## Error Reporting

Useful diagnostic context includes file/object identifier, block position, logical column path, maker/compressor identifiers, expected byte range, and original exception. Avoid logging complete record contents by default.

See [Compatibility](compatibility.md) for valid-but-unsupported files and [Limitations](limitations.md) for semantic caveats.
