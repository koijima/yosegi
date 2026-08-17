# Yosegi Data Model

Yosegi is schema-less at write time, but it maintains a typed column structure internally. Applications do not need to provide a complete schema before writing; the structure is derived dynamically from incoming records.

## Dynamic Columns

Given:

```json
{"id":1,"name":"Alice"}
{"id":2,"name":"Bob","age":30}
{"id":3,"name":"Carol"}
```

Yosegi can construct:

```text
id       name       age
----     -------    ----
1        Alice      NULL
2        Bob        30
3        Carol      NULL
```

A field containing only null or empty values does not by itself establish a concrete column type. A concrete value is required before the corresponding typed column can be created.

## Changing Field Types

A field is not required to have the same type in every record:

```json
{"value":100}
{"value":"unknown"}
{"value":200}
```

When an existing column receives another logical type, Yosegi can represent it as a Union column:

```text
value : UNION
├── INTEGER
└── STRING
```

This behavior applies recursively inside nested structures and arrays.

## Nested Structures

Nested objects use a child Spread-backed representation:

```text
root
├── id
└── user
    ├── name
    └── age
```

Child fields are discovered dynamically using the same mechanism as root fields.

## Arrays

Arrays use an `ArrayColumn` with an internal child Spread for elements. The parent representation preserves which child rows belong to each parent row.

```text
values : ARRAY
└── element column
```

Because the child representation is dynamic, array elements can themselves become nested, arrays, or Union values.

## Empty and Missing Values

Null, empty arrays, and empty nested structures are treated specially during Spread construction and do not necessarily create a column by themselves.

Default behavior depends on the column type. Primitive columns use null-like default cells, while array columns use an empty-array default representation. For this reason, do not generalize missing-value behavior into a single physical NULL rule.

## Column Types

Primitive logical types include:

```text
BOOLEAN BYTE BYTES DOUBLE FLOAT INTEGER LONG SHORT STRING
```

Complex types include:

```text
SPREAD ARRAY UNION
```

## Spread

A Spread is Yosegi's in-memory column-oriented representation of multiple records.

When a row is added, Yosegi determines each field type, creates a column when needed, converts changing types to Union representation when required, and writes the value at the current row position.

## From Logical Data to Storage

```text
Records
  -> IParser
  -> Spread
  -> Columns
  -> ColumnBinary
  -> Block
  -> Yosegi file
```

For the physical representation, see [Yosegi File Format](file_format.md).
