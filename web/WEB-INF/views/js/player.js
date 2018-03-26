var tit = '\xa0';
var jP = $("#jquery_jplayer_1");
var ip = '';
$.get("https://ipinfo.io", function(response) {
    console.log(response.ip);
    ip = response.ip;
}, "jsonp")

ip = (ip.length > 0) ? ip : '***REMOVED***';
var port = '9000';
var media = 'http://' + ip + ':' + port +'/live';
var cur = 'amp';
var space = String.fromCharCode(160);
var rec = String.fromCharCode(9899);
/*var refresh = '<i class="fa fa-refresh fa-spin fa-fw gradient-icon"></i>';*/
var fa = $(".fa");
var url = 'http://' + ip + ':' + port + '/json.xsl';
var _mountPoint, p_width, marquee_dur;
var p_marquee = $('.jp-title p');

// function oneByOne() {
//     var delay = 0;
//     $('ul li:lt(10)').each(function(){
//         //^^ do for every instance less than the 10th (starting at 0)
//         $(this).delay(delay).animate({
//             opacity:0
//         },500);
//         delay += 500;
//     });
// }

//
// var playPause = $(".jp-play and .jp-pause");
//
//
// playPause.mouseover(
//     function () {
//         $(this).css({"background":"linear-gradient(90deg, #f09e02, #e0b61a)", "transition":"background 0.5s ease"});
//
//         setTimeout(function () {
//             $(this).css("background", "var(--whitey-color)");
//         }, 2500);
//
//     });
// playPause.mouseout(
//     function () {
//         $(this).css("background", "linear-gradient(90deg, var(--whitey-color)");
//
//         setTimeout(function () {
//             $(this).css("background", "var(--whitey-color)");
//         }, 2500);
//
//     });
_mountPoint = '/live';

function radioTitle() {

    if (cur !== null) {

        if (cur === 'amp') {
            $.ajax({  type: 'GET',
                url: url,
                async: true,
                jsonpCallback: 'parseMusic',
                contentType: "application/json",
                dataType: 'jsonp',
                success: function (json) {

                    tit = json[_mountPoint]['title'];
                    getTitles();
                    getCover(true);
                    document.title = "Amplee Radio @" + space + tit.concat(space + rec + space);
                    $('#track-title').text(tit ? tit : space);
                    //setting up 'marquee' (title) speed:
                    setSpeed();

                },
                error: function (e) {
                    console.log(e.message);
                }
            });

        } else if (cur === 'chl' || cur === 'dnb') {
            var ampfm = "Amplee Radio @ Lounge FM / ";
            getCover(false);
            document.title = cur ==='chl' ? ampfm + "Chillout" + space + rec + space : ampfm + "Terrace" + space + rec + space;
            // console.log(cur);
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

    // setInterval(function() {
    //     var lenta = document.title.replace(" ", String.fromCharCode(160));
    //     document.title = lenta.substr(1, lenta.length) + lenta.charAt(0);
    // }, 300);

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

    //initAnalyzer();

    setInterval(function () {
        var isPaused = jP.data().jPlayer.status.paused;

        if (isPaused) {
            //$(".subdiv .jp-controls a").find(fa).removeClass('fa-pause').addClass('fa-play');
            jP.jPlayer("stop");
            jP.jPlayer("clearMedia");
            jP.jPlayer("setMedia", {
                mp3: media
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

//Parallax background scrolling
// $(window).on('scroll', function() {
//     smoothBackgroundScroll("../skin/image/bg2_2.png");
// });
//
// function smoothBackgroundScroll(imgsrc) {
//     function loadImageHeight(url, width) {
//         var img = new Image();
//         img.src = url;
//         if (width) {
//             img.width = width;
//         }
//         return img.height;
//     }
//
//     var dh, wh, ih, st, posy, backh, backw;
//     if (!this._smoothBackgroundScroll) {
//         var bcksize = $(document.body).css('background-size');
//         var bmatch = /(\w+)\s*(\w+)/.exec(bcksize);
//         if (!bmatch || bmatch.length < 3) {
//             backh = loadImageHeight(imgsrc);
//         } else {
//             backh = parseInt(bmatch[2]);
//             if (isNaN(backh)) {
//                 backw = parseInt(bmatch[1]);
//                 backh = loadImageHeight(imgsrc, parseInt(backw));
//             }
//         }
//         this._smoothBackgroundScroll = {
//             dh: $(document).height()
//             , wh: $(window).height()
//             , ih: backh
//         }
//     }
//     dh = this._smoothBackgroundScroll.dh;
//     wh = this._smoothBackgroundScroll.wh;
//     ih = this._smoothBackgroundScroll.ih;
//     st = $(document).scrollTop();
//     posy = ((dh - ih) * st / (dh - wh))/20;
//     document.body.style.backgroundPosition = 'center ' + posy + 'px';
// }

var canvas, ctx, src, context, analyzer, fbc_array, a_bars, bar_x, bar_width, bar_height;

function initAnalyzer() {
    context = new (window.AudioContext || window.webkitAudioContext)();
    analyzer = context.createAnalyser();
    canvas = document.getElementById('analyzer');
    ctx = canvas.getContext('2d');
    var audi = document.getElementById('jp_audio_0');
    src = context.createMediaElementSource(audi);
    audi.crossOrigin='anonymous';
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
                    $('#nowPlayingImg').html('<img width="110" align="middle" src="http://loungefm.com.ua/img/logo.png" alt="cover">');
                    lastChl = !lastChl;
                    lastDnb = false;
                    lastAmp = false;
                    media = "http://cast.loungefm.com.ua/chillout128";
                    jP.jPlayer("setMedia", {
                        mp3: media
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
                    $('#nowPlayingImg').html('<img width="110" align="middle" src="http://loungefm.com.ua/img/logo.png" alt="cover">');
                    lastDnb = !lastDnb;
                    lastChl = false;
                    lastAmp = false;
                    media = "http://cast.loungefm.com.ua/terrace128";
                    jP.jPlayer("setMedia", {
                        mp3: media
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
                    media = "http://***REMOVED***:9000/live";
                    jP.jPlayer("setMedia", {
                        mp3: media
                    });
                    stream.width(473);
                    // document.querySelector('marquee').start();
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

