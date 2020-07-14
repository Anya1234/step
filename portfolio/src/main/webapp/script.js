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

/*
* Sets a time of non-doing for a function
*/


/**
 * Gives a random greeting to the page with the interval 3 sec.
 */
var upd_time = 1500;

function addRandomGreeting() {
  const greetings = 
            ['Hello!', 'Привет!', 'مرحبا!', 'Ciao!', 'Buna!', '你好！', 'Bonjour!', '¡Hola!'];

  // Pick a random greeting.
  const greeting = greetings[Math.floor(Math.random() * greetings.length)];

  // Add it to the page.
  const greetingContainer = document.getElementById('greeting-container');
  greetingContainer.innerText = greeting;
}

setInterval(addRandomGreeting, upd_time);


