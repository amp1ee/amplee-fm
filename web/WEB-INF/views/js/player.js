var tit = '\xa0';
var jP = $("#jquery_jplayer_1");
var host = 'ampleefm.com';
var port = '9000';
var _mountPoint = '/live';
var address = 'http://' + host + ':' + port;
var mediaUrl = address + _mountPoint;
var jsonUrl = address + '/json.xsl';
var cur = 'amp';
var space = String.fromCharCode(160);
var rec = ' ' + String.fromCharCode(9899) + ' ';
var fa = $(".fa");
var p_width, marquee_dur;
var p_marquee = $('.jp-title p');

function radioTitle() {

    if (cur !== null) {

        if (cur === 'amp') {
            $.ajax({  type: 'GET',
                url: jsonUrl,
                async: true,
                jsonpCallback: 'parseMusic',
                contentType: "application/json",
                dataType: 'jsonp',
                success: function (json) {
                    tit = json[_mountPoint]['title'];
                    getTitles();
                    getCover(true);
                    document.title = "Amplee Radio @" + space + tit.concat(rec);
                    $('#track-title').text(tit ? tit : space);
                    setSpeed();
                },
                error: function (e) {
                    console.log(e.message);
                }
            });

        } else if (cur === 'chl' || cur === 'dnb') {
            var title = "Amplee Radio @ Lounge FM / ";
            getCover(false);
            document.title = cur ==='chl' ? title + "Chillout" + rec : title + "Terrace" + rec;
        }
    }
}
function setSpeed() {
    p_width = p_marquee.width();
    if (p_width > 350) {
        marquee_dur = 0.16 * (p_width / 7.7865) + 20;
        p_marquee.css('animation', 'marquee linear infinite');
        p_marquee.css('animation-duration', marquee_dur + 's');
    } else {
        p_marquee.css('animation', 'none');
        p_marquee.css('transform', 'translate(-50%,0)');
    }
}

var bars = '<i class="fa fa-bars gradient-icon" aria-hidden="true"></i>';

function getCover(isMain) {
    if (isMain) {
        $.ajax({
            type: 'GET',
            url: 'cover',
            dataType: 'html',
            success: function (response) {
                $('#nowPlayingImg').html(response);
            }
        });
    } else {
        // $('#nowPlayingImg').html('<img width="110px" align="middle" src="../skin/image/music-elems.png" alt="cover">');

    }

}

function getTitles() {
    $.ajax({
        type: 'GET',
        url: 'titles',
        dataType: 'html',

        success: function (response) {
            $('#recent').html(bars + response);
        }
    });

}

$(document).ready(function(){

    setTimeout(function () {
        $('.onclick-menu-content').hide();
    },
        2000
    );

    //language=JQuery
    setTimeout(function () {radioTitle();}, 2000);
    setInterval(function () {radioTitle();}, 15000);
    //
    var dateVar = new Date();
    var timezone = dateVar.getTimezoneOffset()/60 * (-1);
    document.cookie="TZOffset=" + timezone + ";path=/";

    jP.jPlayer({
        ready: function () {
            $(this).jPlayer("setMedia", {
                mp3: mediaUrl
            });
        },

        cssSelectorAncestor: "#jp_container_1",
        supplied: "mp3",
        useStateClassSkin: true,
        autoBlur: false,
        smoothPlayBar: true,
        keyEnabled: true
    });

    //initAnalyzer();

    setInterval(function () {
        var isPaused = jP.data().jPlayer.status.paused;

        if (isPaused) {
            //$(".subdiv .jp-controls a").find(fa).removeClass('fa-pause').addClass('fa-play');
            jP.jPlayer("stop");
            jP.jPlayer("clearMedia");
            jP.jPlayer("setMedia", {
                mp3: mediaUrl
            });
        }
    }, 9000);
});

//Toggle active tabs
$('#toggles').find('li').on('click', function() {
    $('#toggles').find('li').removeClass('active');
    $(this).addClass('active');
});

//Toggle play/pause icons
$(".subdiv .jp-controls a").click(function () {
    $(this).find(fa).toggleClass('fa-play fa-pause');

});

var canvas, ctx, src, context, analyzer, fbc_array, a_bars, bar_x, bar_width, bar_height;

function initAnalyzer() {
    context = new (window.AudioContext || window.webkitAudioContext)();
    analyzer = context.createAnalyser();
    canvas = document.getElementById('analyzer');
    ctx = canvas.getContext('2d');
    var audio = document.getElementById('jp_audio_0');
    src = context.createMediaElementSource(audio);
    audio.crossOrigin='anonymous';
    src.connect(analyzer);
    analyzer.connect(context.destination);
    frameLooper();
}

function frameLooper() {
    window.requestAnimationFrame(frameLooper);
    fbc_array = new Uint8Array(analyzer.frequencyBinCount);
    analyzer.getByteFrequencyData(fbc_array);
    ctx.clearRect(0,0,canvas.width, canvas.height);
    ctx.fillStyle = '#E0B35B';
    a_bars = 100;
    for (var i = 0; i < a_bars; i++) {
        bar_x = i*3;
        bar_width = 2;
        bar_height = -(fbc_array[i] / 2);
        ctx.fillRect(bar_x, canvas.height, bar_width, bar_height);
    }
}

var lastAmp, lastChl, lastDnb, last;
var stream = $(".jp-audio-stream"), subDivs = $(".jp-details, .counter, .onclick-menu");
//Switching the stations
function _setMedia(str) {
    cur = str;
    switch (str) {
        case 'chl': {
            if (!lastChl) {
                last = true;
                $('#nowPlayingImg').html('<img width="110" align="middle" ' +
                    'src="http://loungefm.com.ua/img/logo.png" alt="cover">');
                lastChl = !lastChl;
                lastDnb = false;
                lastAmp = false;
                mediaUrl = "http://cast.loungefm.com.ua/chillout128";
                jP.jPlayer("setMedia", {
                    mp3: mediaUrl
                });
                stream.width(115);
                subDivs.hide();
                break;
            } else {
                last = false;
                break;
            }
        }
        case 'dnb': {
            if (!lastDnb) {
                last = true;
                $('#nowPlayingImg').html('<img width="110" align="middle" ' +
                    'src="http://loungefm.com.ua/img/logo.png" alt="cover">');
                lastDnb = !lastDnb;
                lastChl = false;
                lastAmp = false;
                mediaUrl = "http://cast.loungefm.com.ua/terrace128";
                jP.jPlayer("setMedia", {
                    mp3: mediaUrl
                });
                stream.width(115);
                subDivs.hide();
                break;
            } else {
                last = false;
                break;
            }
        }
        case 'amp': {
            if (!lastAmp) {
                last = true;
                getCover(true);
                lastAmp = !lastAmp;
                lastChl = false;
                lastDnb = false;
                mediaUrl = media;
                jP.jPlayer("setMedia", {
                    mp3: mediaUrl
                });
                stream.width(473);
                subDivs.show();
                break;
            } else {
                last = false;
                break;
            }
        }
    }

        // if (last) {
        //     $(".jp-controls").find(fa).removeClass('fa-pause').addClass('fa-play');
        // }

}

