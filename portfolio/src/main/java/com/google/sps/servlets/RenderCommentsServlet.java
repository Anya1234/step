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

package com.google.sps.servlets;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.gson.Gson;
import com.google.sps.data.Message;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 *  Servlet that adds comments
 */
@WebServlet("/render-comments")
public class RenderCommentsServlet extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    Query query = new Query("Message").addSort("time", SortDirection.ASCENDING);
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    PreparedQuery results = datastore.prepare(query);
    ArrayList<Message> messages = new ArrayList<>();
    for (Entity entity : results.asIterable()) {
      String text = (String) entity.getProperty("text");
      String color = (String) entity.getProperty("color");
      String font_size = (String) entity.getProperty("font_size");
      String username = (String) entity.getProperty("username");
      String im_time = (String) entity.getProperty("im_time");
      Message message = new Message(text, color, font_size, username, im_time);
      messages.add(message);
    }

    String json = convertToJson(messages);
    response.setContentType("application/json;");
    response.getWriter().println(json);
    System.out.println(json);
  }

  private String convertToJson(ArrayList<Message> message) {
    Gson gson = new Gson();
    String json = gson.toJson(message);
    return json;
  }
}
