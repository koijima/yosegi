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

package jp.co.yahoo.yosegi.spread.analyzer;

import jp.co.yahoo.yosegi.spread.column.ColumnType;

public class LongColumnAnalizeResult implements IColumnAnalizeResult {

  private final String columnName;
  private final int columnSize;
  private final boolean sortFlag;
  private final int nullCount;
  private final int rowCount;
  private final int uniqCount;

  private final long min;
  private final long max;

  private final int nullIgnoreRleRowGroupCount;
  private final int nullIgnoreRleMaxRowGroupLength;

  /**
   * Set and initialize results.
   */
  public LongColumnAnalizeResult(
      final String columnName ,
      final int columnSize ,
      final boolean sortFlag ,
      final int nullCount ,
      final int rowCount ,
      final int uniqCount ,
      final long min ,
      final long max ,
      final int nullIgnoreRleRowGroupCount , 
      final int nullIgnoreRleMaxRowGroupLength ) {
    this.columnName = columnName;
    this.columnSize = columnSize;
    this.sortFlag = sortFlag;
    this.nullCount = nullCount;
    this.rowCount = rowCount;
    this.uniqCount = uniqCount;
    this.min = min;
    this.max = max;
    this.nullIgnoreRleRowGroupCount = nullIgnoreRleRowGroupCount;
    this.nullIgnoreRleMaxRowGroupLength = nullIgnoreRleMaxRowGroupLength;
  }

  @Override
  public String getColumnName() {
    return columnName;
  }

  @Override
  public ColumnType getColumnType() {
    return ColumnType.LONG;
  }

  @Override
  public int getColumnSize() {
    return columnSize;
  }

  @Override
  public boolean maybeSorted() {
    return sortFlag;
  }

  @Override
  public int getNullCount() {
    return nullCount;
  }

  @Override
  public int getRowCount() {
    return rowCount;
  }

  @Override
  public int getUniqCount() {
    return uniqCount;
  }

  @Override
  public int getLogicalDataSize() {
    return Long.BYTES * rowCount;
  }

  @Override
  public int getRowStart() {
    return 0;
  }

  @Override
  public int getRowEnd() {
    return columnSize - 1;
  }

  @Override
  public int getNullIgnoreRleGroupCount() {
    return nullIgnoreRleRowGroupCount;
  }

  @Override
  public int getNullIgonoreRleMaxRowGroupLength() {
    return nullIgnoreRleMaxRowGroupLength;
  }

  public long getMin() {
    return min;
  }

  public long getMax() {
    return max;
  }

}
