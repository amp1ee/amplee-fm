var tit = '\xa0';
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
            var rec = String.fromCharCode(9210);
            // this is the element we're updating that will hold the track title
            tit = json[mountpoint]['title'];

            getTitles();
            document.title = tit.concat(space + rec + space);
            $('#track-title').text(tit ? tit : space);
            // this is the element we're updating that will hold the listeners count
            $('#listeners').text(json[mountpoint]['listeners']);
        },
        error: function (e) {    console.log(e.message);
        }
    });
}

function getTitles() {

    $.ajax({
        type: 'GET',
        url: 'titles',
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

    setInterval(function() {
        var lenta = document.title.replace(" ", String.fromCharCode(160));
        document.title = lenta.substr(1, lenta.length)+lenta.charAt(0);
    }, 300);

    $("#jquery_jplayer_1").jPlayer({
        ready: function () {
            $(this).jPlayer("setMedia", {
                mp3:"http://***REMOVED***:9000/live"
            });
        },
        /*//TODO: pause problem
        error: function (event) {
            $(this).jPlayer("play");
            alert(event.message);
        },*/
        cssSelectorAncestor: "#jp_container_1",
        supplied: "mp3",
        useStateClassSkin: true,
        autoBlur: false,
        smoothPlayBar: true,
        keyEnabled: true
    });

});
