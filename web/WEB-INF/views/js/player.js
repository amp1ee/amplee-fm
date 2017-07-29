var tit = '\xa0';
var jP = $("#jquery_jplayer_1");
var media = "http://***REMOVED***:9000/live";
var cur = 'amp';

function radioTitle() {
    // this is the URL of the json.xml file located on your server.
    var url = 'http://***REMOVED***:9000/json.xsl';
    // this is your mountpoint's name, mine is called /radio
    var mountpoint = '/live';

    $.ajax({  type: 'GET',
        url: url,
        async: true,
        jsonpCallback: 'parseMusic',
        contentType: "application/json",
        dataType: 'jsonp',
        success: function (json) {
            var space = String.fromCharCode(160);
            var rec = String.fromCharCode(9899);
            // this is the element we're updating that will hold the track title
                tit = json[mountpoint]['title'];
            getTitles();
            if (cur!==null) {
                if (cur !== 'dnb' || cur !== 'chl') {
                    document.title = tit.concat(space + rec + space);
                    $('#track-title').text(tit ? tit : space);
                    // this is the element we're updating that will hold the listeners count
                    $('#listeners').text(json[mountpoint]['listeners']);
                }
            } else document.title = "Amplee Radio"+space+rec+space;
        },
        error: function (e) {    console.log(e.message);
        }
    });
}

function getTitles() {
    var cUrl = 'titles';
    $.ajax({
        type: 'GET',
        url: cUrl,
        dataType: 'html',
        success: function (response) {
            $('#recent').html(response);
        }
    });

}

$(document).ready(function(){
    //language=JQuery
    setTimeout(function () {radioTitle();}, 2000);
    setInterval(function () {radioTitle();}, 15000);
    //
    var dateVar = new Date();
    var timezone = dateVar.getTimezoneOffset()/60 * (-1);
    document.cookie="TZOffset=" + timezone + ";path=/";
    //
    setInterval(function() {
        var lenta = document.title.replace(" ", String.fromCharCode(160));
        document.title = lenta.substr(1, lenta.length) + lenta.charAt(0);
    }, 300);

    jP.jPlayer({
        ready: function () {
            $(this).jPlayer("setMedia", {
                mp3: media
            });
        },

        cssSelectorAncestor: "#jp_container_1",
        supplied: "mp3",
        useStateClassSkin: true,
        autoBlur: false,
        smoothPlayBar: true,
        keyEnabled: true
    });

    setInterval(function () {
        var isPaused = jP.data().jPlayer.status.paused;

        if (isPaused) {
            jP.jPlayer("stop");
            jP.jPlayer("clearMedia");
            jP.jPlayer("setMedia", {
                mp3: media
            });
        }
    }, 9000);
});

var lastClicked;
lastClicked = false;
var lastAmp;
var lastChl;
var lastDnb;

function _setMedia(str) {

    var stream = $(".jp-audio-stream");
    var subdivs = $(".jp-details, .counter, .onclick-menu");
    cur = str;

        switch (str) {
            case 'chl': {
                if (!lastChl) {
                    lastChl = !lastChl;
                    lastDnb = false;
                    lastAmp = false;
                    media = "http://cast.loungefm.com.ua/chillout128";
                    jP.jPlayer("setMedia", {
                        mp3: media
                    });
                    stream.width(115);
                    subdivs.hide();
                    break;
                } else break;
            }
            case 'dnb': {
                if (!lastDnb) {
                    lastDnb = !lastDnb;
                    lastChl = false;
                    lastAmp = false;
                    media = "http://cast.loungefm.com.ua/terrace128";
                    jP.jPlayer("setMedia", {
                        mp3: media
                    });
                    stream.width(115);
                    subdivs.hide();
                    break;
                } else break;
            }
            case 'amp': {
                if (!lastAmp) {
                    lastAmp = !lastAmp;
                    lastChl = false;
                    lastDnb = false;
                    media = "http://***REMOVED***:9000/live";
                    jP.jPlayer("setMedia", {
                        mp3: media
                    });
                    stream.width(473);
                    document.querySelector('marquee').start();
                    subdivs.show();
                    break;
                } else break;
            }
        }
}

