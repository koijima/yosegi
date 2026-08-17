# Java Quick Start

This guide shows the basic write-and-read flow using an in-memory buffer.

## Write and Read Yosegi Data

```java
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import jp.co.yahoo.yosegi.config.Configuration;
import jp.co.yahoo.yosegi.message.formatter.json.JacksonMessageWriter;
import jp.co.yahoo.yosegi.message.parser.IParser;
import jp.co.yahoo.yosegi.message.parser.json.JacksonMessageReader;
import jp.co.yahoo.yosegi.reader.YosegiSchemaReader;
import jp.co.yahoo.yosegi.writer.YosegiRecordWriter;

public class JavaQuickStart {

  public static void main( final String[] args ) throws Exception {
    String[] jsonMessages = new String[] {
      "{\"col1\":100,\"col2\":\"aaa\"}",
      "{\"col1\":200,\"col2\":\"bbb\"}",
      "{\"col1\":300,\"col2\":\"ccc\"}"
    };

    JacksonMessageReader jsonReader = new JacksonMessageReader();
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    YosegiRecordWriter writer = new YosegiRecordWriter( out );

    for ( String json : jsonMessages ) {
      IParser parser = jsonReader.create( json );
      writer.addParserRow( parser );
    }
    writer.close();

    byte[] yosegiData = out.toByteArray();
    InputStream in = new ByteArrayInputStream( yosegiData );

    YosegiSchemaReader reader = new YosegiSchemaReader();
    reader.setNewStream( in, yosegiData.length, new Configuration() );

    JacksonMessageWriter jsonWriter = new JacksonMessageWriter();
    while ( reader.hasNext() ) {
      IParser parser = reader.next();
      System.out.println( new String( jsonWriter.create( parser ) ) );
    }
  }
}
```

Field order in the reconstructed JSON may differ from the input order.

## How It Works

The basic path is:

```text
JSON
  -> JacksonMessageReader
  -> IParser
  -> YosegiRecordWriter
  -> Yosegi bytes
  -> YosegiSchemaReader
  -> IParser
  -> JacksonMessageWriter
  -> JSON
```

`IParser` separates the Yosegi storage model from a specific input serialization format.

Always close the writer. Closing flushes buffered data and completes the final output.

## Next Steps

- [Yosegi Data Model](../data_model.md)
- [Yosegi File Format](../file_format.md)
- [Projection and Predicate Pushdown](../pushdown.md)
