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
 * This struct represents the response of an query for IDs.
 */
public class IdsResponse implements org.apache.thrift.TBase<IdsResponse, IdsResponse._Fields>, java.io.Serializable, Cloneable, Comparable<IdsResponse> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("IdsResponse");

  private static final org.apache.thrift.protocol.TField IDS_FIELD_DESC = new org.apache.thrift.protocol.TField("ids", org.apache.thrift.protocol.TType.LIST, (short)1);
  private static final org.apache.thrift.protocol.TField TOTAL_RESULTS_FIELD_DESC = new org.apache.thrift.protocol.TField("totalResults", org.apache.thrift.protocol.TType.I64, (short)2);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new IdsResponseStandardSchemeFactory());
    schemes.put(TupleScheme.class, new IdsResponseTupleSchemeFactory());
  }

  public List<IdAndStatus> ids; // optional
  public long totalResults; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    IDS((short)1, "ids"),
    TOTAL_RESULTS((short)2, "totalResults");

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
        case 1: // IDS
          return IDS;
        case 2: // TOTAL_RESULTS
          return TOTAL_RESULTS;
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
  private static final int __TOTALRESULTS_ISSET_ID = 0;
  private byte __isset_bitfield = 0;
  private _Fields optionals[] = {_Fields.IDS,_Fields.TOTAL_RESULTS};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.IDS, new org.apache.thrift.meta_data.FieldMetaData("ids", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, IdAndStatus.class))));
    tmpMap.put(_Fields.TOTAL_RESULTS, new org.apache.thrift.meta_data.FieldMetaData("totalResults", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(IdsResponse.class, metaDataMap);
  }

  public IdsResponse() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public IdsResponse(IdsResponse other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.isSetIds()) {
      List<IdAndStatus> __this__ids = new ArrayList<IdAndStatus>(other.ids.size());
      for (IdAndStatus other_element : other.ids) {
        __this__ids.add(new IdAndStatus(other_element));
      }
      this.ids = __this__ids;
    }
    this.totalResults = other.totalResults;
  }

  public IdsResponse deepCopy() {
    return new IdsResponse(this);
  }

  @Override
  public void clear() {
    this.ids = null;
    setTotalResultsIsSet(false);
    this.totalResults = 0;
  }

  public int getIdsSize() {
    return (this.ids == null) ? 0 : this.ids.size();
  }

  public java.util.Iterator<IdAndStatus> getIdsIterator() {
    return (this.ids == null) ? null : this.ids.iterator();
  }

  public void addToIds(IdAndStatus elem) {
    if (this.ids == null) {
      this.ids = new ArrayList<IdAndStatus>();
    }
    this.ids.add(elem);
  }

  public List<IdAndStatus> getIds() {
    return this.ids;
  }

  public IdsResponse setIds(List<IdAndStatus> ids) {
    this.ids = ids;
    return this;
  }

  public void unsetIds() {
    this.ids = null;
  }

  /** Returns true if field ids is set (has been assigned a value) and false otherwise */
  public boolean isSetIds() {
    return this.ids != null;
  }

  public void setIdsIsSet(boolean value) {
    if (!value) {
      this.ids = null;
    }
  }

  public long getTotalResults() {
    return this.totalResults;
  }

  public IdsResponse setTotalResults(long totalResults) {
    this.totalResults = totalResults;
    setTotalResultsIsSet(true);
    return this;
  }

  public void unsetTotalResults() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __TOTALRESULTS_ISSET_ID);
  }

  /** Returns true if field totalResults is set (has been assigned a value) and false otherwise */
  public boolean isSetTotalResults() {
    return EncodingUtils.testBit(__isset_bitfield, __TOTALRESULTS_ISSET_ID);
  }

  public void setTotalResultsIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __TOTALRESULTS_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case IDS:
      if (value == null) {
        unsetIds();
      } else {
        setIds((List<IdAndStatus>)value);
      }
      break;

    case TOTAL_RESULTS:
      if (value == null) {
        unsetTotalResults();
      } else {
        setTotalResults((Long)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case IDS:
      return getIds();

    case TOTAL_RESULTS:
      return Long.valueOf(getTotalResults());

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case IDS:
      return isSetIds();
    case TOTAL_RESULTS:
      return isSetTotalResults();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof IdsResponse)
      return this.equals((IdsResponse)that);
    return false;
  }

  public boolean equals(IdsResponse that) {
    if (that == null)
      return false;

    boolean this_present_ids = true && this.isSetIds();
    boolean that_present_ids = true && that.isSetIds();
    if (this_present_ids || that_present_ids) {
      if (!(this_present_ids && that_present_ids))
        return false;
      if (!this.ids.equals(that.ids))
        return false;
    }

    boolean this_present_totalResults = true && this.isSetTotalResults();
    boolean that_present_totalResults = true && that.isSetTotalResults();
    if (this_present_totalResults || that_present_totalResults) {
      if (!(this_present_totalResults && that_present_totalResults))
        return false;
      if (this.totalResults != that.totalResults)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public int compareTo(IdsResponse other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetIds()).compareTo(other.isSetIds());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetIds()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.ids, other.ids);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetTotalResults()).compareTo(other.isSetTotalResults());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTotalResults()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.totalResults, other.totalResults);
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
    StringBuilder sb = new StringBuilder("IdsResponse(");
    boolean first = true;

    if (isSetIds()) {
      sb.append("ids:");
      if (this.ids == null) {
        sb.append("null");
      } else {
        sb.append(this.ids);
      }
      first = false;
    }
    if (isSetTotalResults()) {
      if (!first) sb.append(", ");
      sb.append("totalResults:");
      sb.append(this.totalResults);
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

  private static class IdsResponseStandardSchemeFactory implements SchemeFactory {
    public IdsResponseStandardScheme getScheme() {
      return new IdsResponseStandardScheme();
    }
  }

  private static class IdsResponseStandardScheme extends StandardScheme<IdsResponse> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, IdsResponse struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // IDS
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list26 = iprot.readListBegin();
                struct.ids = new ArrayList<IdAndStatus>(_list26.size);
                for (int _i27 = 0; _i27 < _list26.size; ++_i27)
                {
                  IdAndStatus _elem28;
                  _elem28 = new IdAndStatus();
                  _elem28.read(iprot);
                  struct.ids.add(_elem28);
                }
                iprot.readListEnd();
              }
              struct.setIdsIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // TOTAL_RESULTS
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.totalResults = iprot.readI64();
              struct.setTotalResultsIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, IdsResponse struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.ids != null) {
        if (struct.isSetIds()) {
          oprot.writeFieldBegin(IDS_FIELD_DESC);
          {
            oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.ids.size()));
            for (IdAndStatus _iter29 : struct.ids)
            {
              _iter29.write(oprot);
            }
            oprot.writeListEnd();
          }
          oprot.writeFieldEnd();
        }
      }
      if (struct.isSetTotalResults()) {
        oprot.writeFieldBegin(TOTAL_RESULTS_FIELD_DESC);
        oprot.writeI64(struct.totalResults);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class IdsResponseTupleSchemeFactory implements SchemeFactory {
    public IdsResponseTupleScheme getScheme() {
      return new IdsResponseTupleScheme();
    }
  }

  private static class IdsResponseTupleScheme extends TupleScheme<IdsResponse> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, IdsResponse struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetIds()) {
        optionals.set(0);
      }
      if (struct.isSetTotalResults()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetIds()) {
        {
          oprot.writeI32(struct.ids.size());
          for (IdAndStatus _iter30 : struct.ids)
          {
            _iter30.write(oprot);
          }
        }
      }
      if (struct.isSetTotalResults()) {
        oprot.writeI64(struct.totalResults);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, IdsResponse struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        {
          org.apache.thrift.protocol.TList _list31 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
          struct.ids = new ArrayList<IdAndStatus>(_list31.size);
          for (int _i32 = 0; _i32 < _list31.size; ++_i32)
          {
            IdAndStatus _elem33;
            _elem33 = new IdAndStatus();
            _elem33.read(iprot);
            struct.ids.add(_elem33);
          }
        }
        struct.setIdsIsSet(true);
      }
      if (incoming.get(1)) {
        struct.totalResults = iprot.readI64();
        struct.setTotalResultsIsSet(true);
      }
    }
  }

}

