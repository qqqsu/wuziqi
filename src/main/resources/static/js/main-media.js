var startmusic = document.querySelector("#startmusic");
var playmusic = document.querySelector("#playmusic");
var winmusic = document.querySelector("#winmusic");
var losemusic = document.querySelector("#losemusic");
var msgmusic = document.querySelector("#msgmusic");

var cat = document.querySelector(".cat");
var catshow = function (text){
    cat.innerHTML= text;
    cat.classList.add("show");
    setTimeout(() => {
        cat.classList.remove("show");
    }, 1000);
}