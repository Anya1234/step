/*
* Plays audio by key number
*/

function playNote(e) {
  var audio = document.querySelector(`audio[data-key = "${e}"]`);
  audio.currentTime = 0;  //if someone presses the key before audio has ended, it should start from the begin
  audio.play();
}

/*
* Plays after clicking
*/

$(document).ready(function(){
  $(".key").click(function(){
    key = $(this).attr('data-key');
    playNote(key);
  });
});



