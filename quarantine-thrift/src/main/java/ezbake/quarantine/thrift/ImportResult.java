/*   Copyright (C) 2013-2014 Computer Sciences Corporation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. */

/**
 * Autogenerated by Thrift Compiler (0.9.1)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package ezbake.quarantine.thrift;

import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;

import org.apache.thrift.scheme.TupleScheme;
import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.protocol.TProtocolException;
import org.apache.thrift.EncodingUtils;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.server.AbstractNonblockingServer.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.Set;
import java.util.HashSet;
import java.util.EnumSet;
import java.util.Collections;
import java.util.BitSet;
import java.nio.ByteBuffer;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This object represents the result of an import operation.
 */
public class ImportResult implements org.apache.thrift.TBase<ImportResult, ImportResult._Fields>, java.io.Serializable, Cloneable, Comparable<ImportResult> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("ImportResult");

  private static final org.apache.thrift.protocol.TField TOTAL_RECORDS_FIELD_DESC = new org.apache.thrift.protocol.TField("totalRecords", org.apache.thrift.protocol.TType.I64, (short)1);
  private static final org.apache.thrift.protocol.TField RECORDS_IMPORTED_FIELD_DESC = new org.apache.thrift.protocol.TField("recordsImported", org.apache.thrift.protocol.TType.I64, (short)2);
  private static final org.apache.thrift.protocol.TField DUPLICATE_RECORDS_FIELD_DESC = new org.apache.thrift.protocol.TField("duplicateRecords", org.apache.thrift.protocol.TType.I64, (short)3);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new ImportResultStandardSchemeFactory());
    schemes.put(TupleScheme.class, new ImportResultTupleSchemeFactory());
  }

  public long totalRecords; // optional
  public long recordsImported; // optional
  public long duplicateRecords; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    TOTAL_RECORDS((short)1, "totalRecords"),
    RECORDS_IMPORTED((short)2, "recordsImported"),
    DUPLICATE_RECORDS((short)3, "duplicateRecords");

    private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

    static {
      for (_Fields field : EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // TOTAL_RECORDS
          return TOTAL_RECORDS;
        case 2: // RECORDS_IMPORTED
          return RECORDS_IMPORTED;
        case 3: // DUPLICATE_RECORDS
          return DUPLICATE_RECORDS;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final String _fieldName;

    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  private static final int __TOTALRECORDS_ISSET_ID = 0;
  private static final int __RECORDSIMPORTED_ISSET_ID = 1;
  private static final int __DUPLICATERECORDS_ISSET_ID = 2;
  private byte __isset_bitfield = 0;
  private _Fields optionals[] = {_Fields.TOTAL_RECORDS,_Fields.RECORDS_IMPORTED,_Fields.DUPLICATE_RECORDS};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.TOTAL_RECORDS, new org.apache.thrift.meta_data.FieldMetaData("totalRecords", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.RECORDS_IMPORTED, new org.apache.thrift.meta_data.FieldMetaData("recordsImported", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.DUPLICATE_RECORDS, new org.apache.thrift.meta_data.FieldMetaData("duplicateRecords", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(ImportResult.class, metaDataMap);
  }

  public ImportResult() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public ImportResult(ImportResult other) {
    __isset_bitfield = other.__isset_bitfield;
    this.totalRecords = other.totalRecords;
    this.recordsImported = other.recordsImported;
    this.duplicateRecords = other.duplicateRecords;
  }

  public ImportResult deepCopy() {
    return new ImportResult(this);
  }

  @Override
  public void clear() {
    setTotalRecordsIsSet(false);
    this.totalRecords = 0;
    setRecordsImportedIsSet(false);
    this.recordsImported = 0;
    setDuplicateRecordsIsSet(false);
    this.duplicateRecords = 0;
  }

  public long getTotalRecords() {
    return this.totalRecords;
  }

  public ImportResult setTotalRecords(long totalRecords) {
    this.totalRecords = totalRecords;
    setTotalRecordsIsSet(true);
    return this;
  }

  public void unsetTotalRecords() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __TOTALRECORDS_ISSET_ID);
  }

  /** Returns true if field totalRecords is set (has been assigned a value) and false otherwise */
  public boolean isSetTotalRecords() {
    return EncodingUtils.testBit(__isset_bitfield, __TOTALRECORDS_ISSET_ID);
  }

  public void setTotalRecordsIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __TOTALRECORDS_ISSET_ID, value);
  }

  public long getRecordsImported() {
    return this.recordsImported;
  }

  public ImportResult setRecordsImported(long recordsImported) {
    this.recordsImported = recordsImported;
    setRecordsImportedIsSet(true);
    return this;
  }

  public void unsetRecordsImported() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __RECORDSIMPORTED_ISSET_ID);
  }

  /** Returns true if field recordsImported is set (has been assigned a value) and false otherwise */
  public boolean isSetRecordsImported() {
    return EncodingUtils.testBit(__isset_bitfield, __RECORDSIMPORTED_ISSET_ID);
  }

  public void setRecordsImportedIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __RECORDSIMPORTED_ISSET_ID, value);
  }

  public long getDuplicateRecords() {
    return this.duplicateRecords;
  }

  public ImportResult setDuplicateRecords(long duplicateRecords) {
    this.duplicateRecords = duplicateRecords;
    setDuplicateRecordsIsSet(true);
    return this;
  }

  public void unsetDuplicateRecords() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __DUPLICATERECORDS_ISSET_ID);
  }

  /** Returns true if field duplicateRecords is set (has been assigned a value) and false otherwise */
  public boolean isSetDuplicateRecords() {
    return EncodingUtils.testBit(__isset_bitfield, __DUPLICATERECORDS_ISSET_ID);
  }

  public void setDuplicateRecordsIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __DUPLICATERECORDS_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case TOTAL_RECORDS:
      if (value == null) {
        unsetTotalRecords();
      } else {
        setTotalRecords((Long)value);
      }
      break;

    case RECORDS_IMPORTED:
      if (value == null) {
        unsetRecordsImported();
      } else {
        setRecordsImported((Long)value);
      }
      break;

    case DUPLICATE_RECORDS:
      if (value == null) {
        unsetDuplicateRecords();
      } else {
        setDuplicateRecords((Long)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case TOTAL_RECORDS:
      return Long.valueOf(getTotalRecords());

    case RECORDS_IMPORTED:
      return Long.valueOf(getRecordsImported());

    case DUPLICATE_RECORDS:
      return Long.valueOf(getDuplicateRecords());

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case TOTAL_RECORDS:
      return isSetTotalRecords();
    case RECORDS_IMPORTED:
      return isSetRecordsImported();
    case DUPLICATE_RECORDS:
      return isSetDuplicateRecords();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof ImportResult)
      return this.equals((ImportResult)that);
    return false;
  }

  public boolean equals(ImportResult that) {
    if (that == null)
      return false;

    boolean this_present_totalRecords = true && this.isSetTotalRecords();
    boolean that_present_totalRecords = true && that.isSetTotalRecords();
    if (this_present_totalRecords || that_present_totalRecords) {
      if (!(this_present_totalRecords && that_present_totalRecords))
        return false;
      if (this.totalRecords != that.totalRecords)
        return false;
    }

    boolean this_present_recordsImported = true && this.isSetRecordsImported();
    boolean that_present_recordsImported = true && that.isSetRecordsImported();
    if (this_present_recordsImported || that_present_recordsImported) {
      if (!(this_present_recordsImported && that_present_recordsImported))
        return false;
      if (this.recordsImported != that.recordsImported)
        return false;
    }

    boolean this_present_duplicateRecords = true && this.isSetDuplicateRecords();
    boolean that_present_duplicateRecords = true && that.isSetDuplicateRecords();
    if (this_present_duplicateRecords || that_present_duplicateRecords) {
      if (!(this_present_duplicateRecords && that_present_duplicateRecords))
        return false;
      if (this.duplicateRecords != that.duplicateRecords)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public int compareTo(ImportResult other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetTotalRecords()).compareTo(other.isSetTotalRecords());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTotalRecords()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.totalRecords, other.totalRecords);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetRecordsImported()).compareTo(other.isSetRecordsImported());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetRecordsImported()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.recordsImported, other.recordsImported);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetDuplicateRecords()).compareTo(other.isSetDuplicateRecords());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetDuplicateRecords()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.duplicateRecords, other.duplicateRecords);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("ImportResult(");
    boolean first = true;

    if (isSetTotalRecords()) {
      sb.append("totalRecords:");
      sb.append(this.totalRecords);
      first = false;
    }
    if (isSetRecordsImported()) {
      if (!first) sb.append(", ");
      sb.append("recordsImported:");
      sb.append(this.recordsImported);
      first = false;
    }
    if (isSetDuplicateRecords()) {
      if (!first) sb.append(", ");
      sb.append("duplicateRecords:");
      sb.append(this.duplicateRecords);
      first = false;
    }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class ImportResultStandardSchemeFactory implements SchemeFactory {
    public ImportResultStandardScheme getScheme() {
      return new ImportResultStandardScheme();
    }
  }

  private static class ImportResultStandardScheme extends StandardScheme<ImportResult> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, ImportResult struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // TOTAL_RECORDS
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.totalRecords = iprot.readI64();
              struct.setTotalRecordsIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // RECORDS_IMPORTED
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.recordsImported = iprot.readI64();
              struct.setRecordsImportedIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // DUPLICATE_RECORDS
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.duplicateRecords = iprot.readI64();
              struct.setDuplicateRecordsIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, ImportResult struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.isSetTotalRecords()) {
        oprot.writeFieldBegin(TOTAL_RECORDS_FIELD_DESC);
        oprot.writeI64(struct.totalRecords);
        oprot.writeFieldEnd();
      }
      if (struct.isSetRecordsImported()) {
        oprot.writeFieldBegin(RECORDS_IMPORTED_FIELD_DESC);
        oprot.writeI64(struct.recordsImported);
        oprot.writeFieldEnd();
      }
      if (struct.isSetDuplicateRecords()) {
        oprot.writeFieldBegin(DUPLICATE_RECORDS_FIELD_DESC);
        oprot.writeI64(struct.duplicateRecords);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class ImportResultTupleSchemeFactory implements SchemeFactory {
    public ImportResultTupleScheme getScheme() {
      return new ImportResultTupleScheme();
    }
  }

  private static class ImportResultTupleScheme extends TupleScheme<ImportResult> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, ImportResult struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetTotalRecords()) {
        optionals.set(0);
      }
      if (struct.isSetRecordsImported()) {
        optionals.set(1);
      }
      if (struct.isSetDuplicateRecords()) {
        optionals.set(2);
      }
      oprot.writeBitSet(optionals, 3);
      if (struct.isSetTotalRecords()) {
        oprot.writeI64(struct.totalRecords);
      }
      if (struct.isSetRecordsImported()) {
        oprot.writeI64(struct.recordsImported);
      }
      if (struct.isSetDuplicateRecords()) {
        oprot.writeI64(struct.duplicateRecords);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, ImportResult struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(3);
      if (incoming.get(0)) {
        struct.totalRecords = iprot.readI64();
        struct.setTotalRecordsIsSet(true);
      }
      if (incoming.get(1)) {
        struct.recordsImported = iprot.readI64();
        struct.setRecordsImportedIsSet(true);
      }
      if (incoming.get(2)) {
        struct.duplicateRecords = iprot.readI64();
        struct.setDuplicateRecordsIsSet(true);
      }
    }
  }

}

