# Expand and Flatten

Yosegi supports read-time transformations for complex data:

- Expand turns array elements into logical rows.
- Flatten exposes fields from a nested structure at a higher level.

These operations change the representation returned by the reader; they do not rewrite the physical file.

## Expand

Given:

```json
{"id":1,"values":[10,20,30]}
```

Expanding `values` conceptually produces:

```text
id    values
1     10
1     20
1     30
```

Expand changes row cardinality. Empty-array behavior and multiple-array semantics should be treated as implementation-defined unless explicitly documented and tested.

## Flatten

Given:

```json
{"id":1,"user":{"name":"Alice","age":30}}
```

Flattening `user` conceptually exposes:

```text
id    name     age
1     Alice    30
```

Flatten changes column nesting rather than row cardinality.

## Combining Operations

Arrays of structures can be expanded and then flattened to provide a tabular representation.

```text
ARRAY<SPREAD>
  -> Expand
  -> SPREAD per row
  -> Flatten
  -> primitive columns
```

## Dynamic Types

Expand and Flatten do not remove dynamic semantics. Missing fields, Union values, and heterogeneous arrays remain dynamic after transformation.

## Projection Interaction

The application-visible logical path after transformation can differ from the physical path stored in the file. Projection setup can therefore translate requested names back to physical paths and retain structural nodes needed for reconstruction.

Example:

```text
logical request: age
Flatten mapping: user.age
physical projection: user.age
```

## Name Collisions

Flatten can expose fields whose names collide with existing parent fields. Do not rely on a collision policy unless it is explicitly documented by the implementation.

See [Projection and Predicate Pushdown](pushdown.md) and [Limitations](limitations.md).
