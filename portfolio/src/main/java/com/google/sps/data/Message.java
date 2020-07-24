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

package com.google.sps.data;

/** item coding comment. */
public final class Message {

  private final long id;
  private final String text;
  private final String color;
  private final String font_size;

  public Message(long id, String text, String color, String font_size) {
    this.id = id;
    this.text = text;
    this.color = color;
    this.font_size = font_size;
  }
}
