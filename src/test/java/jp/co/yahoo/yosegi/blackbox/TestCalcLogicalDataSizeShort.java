/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jp.co.yahoo.yosegi.blackbox;

import jp.co.yahoo.yosegi.message.objects.*;
import jp.co.yahoo.yosegi.binary.*;
import jp.co.yahoo.yosegi.binary.maker.*;
import jp.co.yahoo.yosegi.spread.column.filter.*;
import jp.co.yahoo.yosegi.spread.column.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class TestCalcLogicalDataSizeShort {

  public static Stream<Arguments> data1() throws IOException {
    return Stream.of(
      arguments( "jp.co.yahoo.yosegi.binary.maker.UnsafeOptimizeDumpLongColumnBinaryMaker" ) ,
      arguments( "jp.co.yahoo.yosegi.binary.maker.UnsafeOptimizeLongColumnBinaryMaker" ) ,
      arguments( "jp.co.yahoo.yosegi.binary.maker.RleLongColumnBinaryMaker" ) ,
      arguments( "jp.co.yahoo.yosegi.binary.maker.OptimizedNullArrayLongColumnBinaryMaker" ),
      arguments( "jp.co.yahoo.yosegi.binary.maker.OptimizedNullArrayDumpLongColumnBinaryMaker" )
    );
  }

  public static ColumnBinary create( final IColumn column , final String targetClassName ) throws IOException {
    ColumnBinaryMakerConfig defaultConfig = new ColumnBinaryMakerConfig();
    ColumnBinaryMakerCustomConfigNode configNode = new ColumnBinaryMakerCustomConfigNode( "root" , defaultConfig );

    IColumnBinaryMaker maker = FindColumnBinaryMaker.get( targetClassName );
    return maker.toBinary( defaultConfig , null , new CompressResultNode() , column );
  }

  @ParameterizedTest
  @MethodSource( "data1" )
  public void T_notNull_1( final String targetClassName ) throws IOException {
    IColumn column = new PrimitiveColumn( ColumnType.SHORT , "column" );
    column.add( ColumnType.SHORT , new ShortObj( (short)1 ) , 0 );
    column.add( ColumnType.SHORT , new ShortObj( (short)2 ) , 1 );
    column.add( ColumnType.SHORT , new ShortObj( (short)3 ) , 2 );
    column.add( ColumnType.SHORT , new ShortObj( (short)4 ) , 3 );
    column.add( ColumnType.SHORT , new ShortObj( (short)5 ) , 4 );
    column.add( ColumnType.SHORT , new ShortObj( (short)6 ) , 5 );
    column.add( ColumnType.SHORT , new ShortObj( (short)7 ) , 6 );
    column.add( ColumnType.SHORT , new ShortObj( (short)8 ) , 7 );
    column.add( ColumnType.SHORT , new ShortObj( (short)9 ) , 8 );
    column.add( ColumnType.SHORT , new ShortObj( (short)10 ) , 9 );

    ColumnBinary columnBinary = create( column , targetClassName );
    assertEquals( columnBinary.logicalDataSize , Short.BYTES * 10 );
  }

  @ParameterizedTest
  @MethodSource( "data1" )
  public void T_notNull_2( final String targetClassName ) throws IOException {
    IColumn column = new PrimitiveColumn( ColumnType.SHORT , "column" );
    column.add( ColumnType.SHORT , new ShortObj( (short)1 ) , 0 );
    column.add( ColumnType.SHORT , new ShortObj( (short)1 ) , 1 );
    column.add( ColumnType.SHORT , new ShortObj( (short)1 ) , 2 );
    column.add( ColumnType.SHORT , new ShortObj( (short)1 ) , 3 );
    column.add( ColumnType.SHORT , new ShortObj( (short)1 ) , 4 );
    column.add( ColumnType.SHORT , new ShortObj( (short)1 ) , 5 );
    column.add( ColumnType.SHORT , new ShortObj( (short)1 ) , 6 );
    column.add( ColumnType.SHORT , new ShortObj( (short)1 ) , 7 );
    column.add( ColumnType.SHORT , new ShortObj( (short)1 ) , 8 );
    column.add( ColumnType.SHORT , new ShortObj( (short)1 ) , 9 );

    ColumnBinary columnBinary = create( column , targetClassName );
    assertEquals( columnBinary.logicalDataSize , Short.BYTES * 10 );
  }

  @ParameterizedTest
  @MethodSource( "data1" )
  public void T_hasNull_1( final String targetClassName ) throws IOException {
    IColumn column = new PrimitiveColumn( ColumnType.SHORT , "column" );
    column.add( ColumnType.SHORT , new ShortObj( (short)1 ) , 1 );
    column.add( ColumnType.SHORT , new ShortObj( (short)2 ) , 2 );
    column.add( ColumnType.SHORT , new ShortObj( (short)3 ) , 4 );
    column.add( ColumnType.SHORT , new ShortObj( (short)4 ) , 5 );

    ColumnBinary columnBinary = create( column , targetClassName );
    assertEquals( columnBinary.logicalDataSize , Short.BYTES * 4 );
  }

  @ParameterizedTest
  @MethodSource( "data1" )
  public void T_hasNull_2( final String targetClassName ) throws IOException {
    IColumn column = new PrimitiveColumn( ColumnType.SHORT , "column" );
    column.add( ColumnType.SHORT , new ShortObj( (short)1 ) , 5 );
    column.add( ColumnType.SHORT , new ShortObj( (short)2 ) , 6 );

    ColumnBinary columnBinary = create( column , targetClassName );
    assertEquals( columnBinary.logicalDataSize , Short.BYTES * 2 );
  }

  @ParameterizedTest
  @MethodSource( "data1" )
  public void T_hasNull_3( final String targetClassName ) throws IOException {
    IColumn column = new PrimitiveColumn( ColumnType.SHORT , "column" );
    column.add( ColumnType.SHORT , new ShortObj( (short)1 ) , 1 );
    column.add( ColumnType.SHORT , new ShortObj( (short)2 ) , 5 );

    ColumnBinary columnBinary = create( column , targetClassName );
    assertEquals( columnBinary.logicalDataSize , Short.BYTES * 2 );
  }

  @ParameterizedTest
  @MethodSource( "data1" )
  public void T_hasNull_4( final String targetClassName ) throws IOException {
    IColumn column = new PrimitiveColumn( ColumnType.SHORT , "column" );
    column.add( ColumnType.SHORT , new ShortObj( (short)1 ) , 1 );
    column.add( ColumnType.SHORT , new ShortObj( (short)1 ) , 2 );
    column.add( ColumnType.SHORT , new ShortObj( (short)1 ) , 4 );
    column.add( ColumnType.SHORT , new ShortObj( (short)1 ) , 5 );

    ColumnBinary columnBinary = create( column , targetClassName );
    assertEquals( columnBinary.logicalDataSize , Short.BYTES * 4 );
  }

  @ParameterizedTest
  @MethodSource( "data1" )
  public void T_hasNull_5( final String targetClassName ) throws IOException {
    IColumn column = new PrimitiveColumn( ColumnType.SHORT , "column" );
    column.add( ColumnType.SHORT , new ShortObj( (short)1 ) , 5 );
    column.add( ColumnType.SHORT , new ShortObj( (short)1 ) , 6 );

    ColumnBinary columnBinary = create( column , targetClassName );
    assertEquals( columnBinary.logicalDataSize , Short.BYTES * 2 );
  }

  @ParameterizedTest
  @MethodSource( "data1" )
  public void T_hasNull_6( final String targetClassName ) throws IOException {
    IColumn column = new PrimitiveColumn( ColumnType.SHORT , "column" );
    column.add( ColumnType.SHORT , new ShortObj( (short)1 ) , 1 );
    column.add( ColumnType.SHORT , new ShortObj( (short)1 ) , 5 );

    ColumnBinary columnBinary = create( column , targetClassName );
    assertEquals( columnBinary.logicalDataSize , Short.BYTES * 2 );
  }

}
