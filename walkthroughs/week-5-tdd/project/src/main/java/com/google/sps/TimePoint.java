// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps;

import java.util.Comparator;

/**
 * Class representing a point of time
 */
public final class TimePoint {
  public static final int START_OF_DAY = getTimeInMinutes(0, 0);
  public static final int END_OF_DAY = getTimeInMinutes(23, 59);
  private final int time;
  private final String type;

  /**
   * A comparator for sorting ranges by their start time in ascending order.
   */
  public static final Comparator<TimePoint> ORDER_BY_TIME = new Comparator<TimePoint>() {
    @Override
    public int compare(TimePoint a, TimePoint b) {
      return Long.compare(a.time, b.time);
    }
  };

  public TimePoint(int time, String type) {
    this.time = time;
    this.type = type;
  }

  /**
   * Returns the time of a point.
   */
  public int getTime() {
    return time;
  }

  /**
   * Returns the type of a point.
   */
  public String getType() {
    return type;
  }

   public static int getTimeInMinutes(int hours, int minutes) {
    if (hours < 0 || hours >= 24) {
      throw new IllegalArgumentException("Hours can only be 0 through 23 (inclusive).");
    }

    if (minutes < 0 || minutes >= 60) {
      throw new IllegalArgumentException("Minutes can only be 0 through 59 (inclusive).");
    }

    return (hours * 60) + minutes;
  }
}
