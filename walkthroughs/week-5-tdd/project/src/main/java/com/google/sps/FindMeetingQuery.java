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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class FindMeetingQuery {
  public Collection<TimeRange> query(Collection<Event> events, MeetingRequest request) {

    ArrayList<TimeRange> result = new ArrayList<>();
    List<TimePoint> timePoints = new ArrayList<>();
    Set<String> attendees = new HashSet<>(request.getAttendees());
    Set<String> allAttendees = new HashSet<>(request.getOptionalAttendees());
    long duration = request.getDuration();
    allAttendees.addAll(attendees);
    result = queryByAttendenceAndDuration(events, allAttendees, duration);
    if (result.size() == 0) {
      result = queryByAttendenceAndDuration(events, attendees, duration);
    }
    return result;
  }

  public ArrayList<TimeRange> queryByAttendenceAndDuration(
      Collection<Event> events, Set<String> attendees, long duration) {
    ArrayList<TimeRange> result = new ArrayList<>();
    List<TimePoint> timePoints = new ArrayList<>();
    for (Event event : events) {
      Set<String> curAttendees = new HashSet<>(event.getAttendees());
      curAttendees.retainAll(attendees);
      if (!curAttendees.isEmpty()) {
        TimePoint startTime = new TimePoint(event.getWhen().start(), "start");
        TimePoint endTime = new TimePoint(event.getWhen().end(), "end");
        timePoints.add(startTime);
        timePoints.add(endTime);
      }
    }
    Collections.sort(timePoints, TimePoint.ORDER_BY_TIME);
    int meetsNum = 0;
    int prev = TimePoint.START_OF_DAY;
    boolean isEmpty = true;
    for (TimePoint point : timePoints) {
      if (isEmpty && (point.getTime() - prev >= duration)) {
        TimeRange freeRange = TimeRange.fromStartEnd(prev, point.getTime(), false);
        result.add(freeRange);
      }
      if (point.getType() == "start") {
        meetsNum++;
      } else {
        meetsNum--;
      }
      isEmpty = meetsNum == 0;
      prev = point.getTime();
    }
    if (isEmpty && (TimePoint.END_OF_DAY - prev >= duration)) {
      TimeRange freeRange = TimeRange.fromStartEnd(prev, TimePoint.END_OF_DAY, true);
      result.add(freeRange);
    }
    return result;
  }
}
