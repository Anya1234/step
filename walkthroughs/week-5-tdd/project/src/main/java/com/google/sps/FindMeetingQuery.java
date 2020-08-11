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

    List<TimeRange> result = new ArrayList<>();
    Set<String> attendees = new HashSet<>(request.getAttendees());
    Set<String> allAttendees = new HashSet<>(request.getOptionalAttendees());
    long duration = request.getDuration();
    allAttendees.addAll(attendees);
    result = queryByAttendenceAndDuration(events, allAttendees, duration);
    if (result.isEmpty()) {
      result = queryByAttendenceAndDuration(events, attendees, duration);
    }
    return result;
  }

  public List<TimeRange> queryByAttendenceAndDuration(
      Collection<Event> events, Set<String> attendees, long duration) {
    List<TimeRange> result = new ArrayList<>();
    List<TimePoint> timePoints = new ArrayList<>();
    for (Event event : events) {
      Set<String> curAttendees = new HashSet<>(event.getAttendees());
      curAttendees.retainAll(attendees);
      if (!curAttendees.isEmpty()) {
        TimePoint startTime = new TimePoint(event.getWhen().start(), true);
        TimePoint endTime = new TimePoint(event.getWhen().end(), false);
        timePoints.add(startTime);
        timePoints.add(endTime);
      }
    }
    Collections.sort(timePoints, TimePoint.ORDER_BY_TIME);
    int numOngoingMeetings = 0;
    int previousPoint = TimePoint.START_OF_DAY;
    boolean currentTimeIsAvailable = true;
    for (TimePoint point : timePoints) {
      if (currentTimeIsAvailable && (point.getTime() - previousPoint >= duration)) {
        TimeRange freeRange = TimeRange.fromStartEnd(previousPoint, point.getTime(), false);
        result.add(freeRange);
      }
      if (point.isStart()) {
        numOngoingMeetings++;
      } else {
        numOngoingMeetings--;
      }
      currentTimeIsAvailable = numOngoingMeetings == 0;
      previousPoint = point.getTime();
    }
    if (currentTimeIsAvailable && (TimePoint.END_OF_DAY - previousPoint >= duration)) {
      TimeRange freeRange = TimeRange.fromStartEnd(previousPoint, TimePoint.END_OF_DAY, true);
      result.add(freeRange);
    }
    return result;
  }
}
