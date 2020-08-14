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

/**
* checks if user is logged in
*/
function checkUser() {
  fetch('/check-user').then((response)=>
    response.json()).then((isLoggedIn)=> {
    if (isLoggedIn == false) {
      document.getElementById('addcontent').placeholder =
       'log in to leave comment';
      document.getElementById('addcontent').disabled = true;
      document.getElementById('color').disabled = true;
      document.getElementById('font_size').disabled = true;
      document.getElementById('button').disabled = true;
      document.getElementById('auth').value = 'login';
    }
  });
}

/**
* gets blobstore url to upload picture
*/
function getUploadUrl() {
  fetch('/upload-url').then((response)=>
    response.json()).then((url)=> {
    console.log(url);
    document.getElementById('addimage').action = url;
  });
}

/**
* fetches current comments from server and puts it to HTML
*/
function addComment() {
  fetch('/render-comments').then((response)=>
    response.json()).then((comments)=> {
    console.log(comments);
    for (let i = 0; i < comments.length; i++) {
      const p = document.createElement('p');
      const message = comments[i];
      p.innerText = message['username'] + ':    ' + message['text'];
      p.style.color = message['color'];
      p.style.fontSize = message['font_size'];
      console.log(message['im_time']);
      document.getElementById(message['im_time']).appendChild(p);
    }
  });
}

/**
* renders images and div class for comments
*/
function addImages() {
  fetch('/render-images').then((response)=>
    response.json()).then((images)=> {
    console.log(images);
    for (let i = 0; i < images.length; i++) {
      const image = images[i];
      const img = document.createElement('img');
      const div = document.createElement('div');
      img.src = image['url'];
      div.id = image['time'];
      document.body.append(img);
      document.body.append(div);
      renderCommentsForm(image['time']);
    }
  });
}

/**
* renders form which sends image time
* @param {string} time - time of uploading image
*/
function renderCommentsForm(time) {
  const form = document.getElementById('comment').cloneNode(true);
  form.hidden = false;
  const textarea = document.createElement('input');
  textarea.type = 'text';
  textarea.name = 'time';
  textarea.value = time;
  textarea.hidden = true;
  console.log(time);
  form.insertBefore(textarea, form.firstChild);
  document.body.append(form);
}

window.onload = function() {
  checkUser();
  getUploadUrl();
  addImages();
  addComment();
};
