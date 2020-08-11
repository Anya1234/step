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

/** Class representing a point of time */
public final class TimePoint {
  public static final int START_OF_DAY = 0;
  public static final int END_OF_DAY = 23 * 60 + 59;
  private final int time;
  private final boolean isStart;

  /** A comparator for sorting ranges by their start time in ascending order. */
  public static final Comparator<TimePoint> ORDER_BY_TIME =
      new Comparator<TimePoint>() {
        @Override
        public int compare(TimePoint a, TimePoint b) {
          return Long.compare(a.time, b.time);
        }
      };

  public TimePoint(int time, boolean isStart) {
    this.time = time;
    this.isStart = isStart;
  }

  /** Returns the time of a point. */
  public int getTime() {
    return time;
  }

  /** Returns the type of a point. */
  public boolean isStart() {
    return isStart;
  }
}
