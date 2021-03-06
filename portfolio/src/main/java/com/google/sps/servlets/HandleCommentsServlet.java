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
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 *  Servlet that adds comments
 */
@WebServlet("/handle-comments")
public class HandleCommentsServlet extends HttpServlet {

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String im_time = request.getParameter("time");
    String text = request.getParameter("add-content");
    String color = request.getParameter("color");
    String font_size = request.getParameter("font_size");
    long time = System.currentTimeMillis();

    System.out.println(im_time);
    UserService userService = UserServiceFactory.getUserService();
    if (!userService.isUserLoggedIn()) {
      String urlToRedirect = "/comment.html";
      response.sendRedirect(userService.createLoginURL(urlToRedirect));
      return;
    }
    String username = userService.getCurrentUser().getNickname();

    if (text.length() == 0) {
      return;
    }

    Entity commentEntity = new Entity("Message");
    commentEntity.setProperty("text", text);
    commentEntity.setProperty("color", color);
    commentEntity.setProperty("font_size", font_size);
    commentEntity.setProperty("time", time);
    commentEntity.setProperty("username", username);
    commentEntity.setProperty("im_time", im_time);

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    datastore.put(commentEntity);

    response.sendRedirect("comment.html");
  }
}
