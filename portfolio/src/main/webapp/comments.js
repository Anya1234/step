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
*fetches current comments from server and puts it to HTML
*/

function addComment() {
  fetch('/handle-comments').then((response)=>
    response.json()).then((comments)=> {
    console.log(comments);
    for (let i = 0; i < comments.length; i++) {
      const p = document.createElement('p');
      const message = comments[i];
      console.log(message['text']);
      p.innerText = message['text'];
      p.style.color = message['color'];
      p.style.fontSize = message['font_size'];
      document.body.prepend(p);
    }
  });
}

addComment();
