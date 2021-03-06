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


import java.util.Map;
import java.util.HashMap;
import org.apache.thrift.TEnum;

/**
 * This enum describes the state of an entry in quarantine.
 */
public enum ObjectStatus implements org.apache.thrift.TEnum {
  /**
   * This represents an entry that has been sent to quarantine, but has not been approved
   * for reingest.
   */
  QUARANTINED(0),
  /**
   * This represents an entry that has been approved by an administrator to be reingested
   * by the source pipeline.
   */
  APPROVED_FOR_REINGEST(1),
  /**
   * This represents an entry that has not been approved for reingest, but will remain in the system.
   * An entry could move from ARCHIVED back to APPROVED_FOR_REINGEST or QUARANTINED.
   */
  ARCHIVED(2),
  /**
   * This represents an entry that was sent as raw data to quarantine. If raw data is sent,
   * it cannot be reingested automatically.
   */
  CANNOT_BE_REINGESTED(3),
  /**
   * This represents an entry that has been approved and sent back to a pipeline to be reingested.
   */
  REINGESTED(4),
  /**
   * This represents an entry that is being prepared for reingest. We have two states for fault tolerant
   * purposes as well as making sure that one item is not reingested multiple times.
   */
  PREPARED_TO_REINGEST(5);

  private final int value;

  private ObjectStatus(int value) {
    this.value = value;
  }

  /**
   * Get the integer value of this enum value, as defined in the Thrift IDL.
   */
  public int getValue() {
    return value;
  }

  /**
   * Find a the enum type by its integer value, as defined in the Thrift IDL.
   * @return null if the value is not found.
   */
  public static ObjectStatus findByValue(int value) { 
    switch (value) {
      case 0:
        return QUARANTINED;
      case 1:
        return APPROVED_FOR_REINGEST;
      case 2:
        return ARCHIVED;
      case 3:
        return CANNOT_BE_REINGESTED;
      case 4:
        return REINGESTED;
      case 5:
        return PREPARED_TO_REINGEST;
      default:
        return null;
    }
  }
}
