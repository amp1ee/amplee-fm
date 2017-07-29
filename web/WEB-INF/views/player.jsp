<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Amplee's&nbsp;radio&nbsp;&#9899;&nbsp;</title>
        <link type="text/css" href="skin/pink.flag/jplayer.pink.flag.css" rel="stylesheet" />
        <link rel="shortcut icon" href="skin/image/favicon.ico?" type="image/x-icon" />
        <link href="https://fonts.googleapis.com/css?family=Arimo" rel="stylesheet">
        <%--<link href="https://fonts.googleapis.com/css?family=Pacifico" rel="stylesheet">--%>
        <link href="https://fonts.googleapis.com/css?family=ABeeZee" rel="stylesheet">
        <link rel="stylesheet" href="font-awesome-4.7.0/css/font-awesome.min.css">

    </head>
    <body>
    <div class="wrapper">
        <header>
            <div class="link"><span id="asterisk">*</span>
                This site is currently under development.</div>
            <div class="header_container">
                <div class="column"><button class="logo" onclick="reload()"><a href="#" title="Reload radio"></a></button></div>
                    <script type="text/javascript">
                    function reload() {
                        window.location.reload(false);
                    }
                    function animateRotate(angle) {
                        // caching the object for performance reasons
                            var elem = $('.logo');
                            $({deg: 0}).animate({deg: angle}, {
                                duration: 2000,
                                step: function (now) {
                                    elem.css({
                                        transform: 'rotate(' + now + 'deg)'
                                    });
                                }
                            });

                    }
                </script>
                <div class="column"><span><a class="tgl_st" href="#" id="amp" onclick="_setMedia('amp')">Amplee radio</a></span></div>
                <div class="column"><span><a class="tgl_st" href="#" id="chl" onclick="_setMedia('chl')">Chillout</a></span></div>
                <div class="column"><span><a class="tgl_st" href="#" id="dnb" onclick="_setMedia('dnb')">Terrace</a></span></div>
            </div>
        </header>
    <div id="stations">
        <div id="hell"><h1><span>&nbsp;Welcome to Amplee Radio!&nbsp;</span></h1></div>
        <div class="rows">
            <div id="jquery_jplayer_1" class="jp-jplayer "></div>
            <div id="jp_container_1" class="jp-audio-stream" role="application" aria-label="media player">
                <div class="jp-type-single">
                    <div class="jp-gui jp-interface">
                        <div class="subdiv">
                            <div class="jp-controls">
                                <i class="fa fa-play" aria-hidden="true"></i><button class="jp-play" role="button" tabindex="0">play</button>
                            </div>

                            <div class="jp-details">
                                <div class="jp-title">
                                    <marquee scrollamount="1" scrolldelay="50" direction="left" truespeed="" id="track-title">&nbsp;</marquee>
                                </div>
                            </div>

                        </div>

                        <div class="jp-volume-controls">
                            <button class="jp-mute" role="button" tabindex="0">mute</button>
                            <button class="jp-volume-max" role="button" tabindex="0">max volume</button>
                            <div class="jp-volume-bar">
                                <div class="jp-volume-bar-value"></div>
                            </div>
                            <div class="counter">Listeners: <span id="listeners">&nbsp;</span></div>

                        </div>
                        <div tabindex="0" class="onclick-menu" id="recent"></div>

                    </div>

                    <div class="jp-no-solution">
                        <span>Update Required</span>
                        To play the media you will need to either update your browser to a recent version or update your <a href="http://get.adobe.com/flashplayer/" target="_blank">Flash plugin</a>.
                    </div>
                </div>
            </div>
        </div>
    </div>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <script src="js/jquery.jplayer.min.js"></script>
        <script src="js/player.js"></script>
        <footer>
            <!--bigmir)net TOP 100-->
            <script type="text/javascript" language="javascript">
                bmN=navigator,bmD=document,bmD.cookie='b=b',i=0,bs=[],bm={v:16950850,s:16950850,t:32,c:bmD.cookie?1:0,n:Math.round((Math.random()* 1000000)),w:0};
                for(var f=self;f!=f.parent;f=f.parent)bm.w++;
                try{if(bmN.plugins&&bmN.mimeTypes.length&&(x=bmN.plugins['Shockwave Flash']))bm.m=parseInt(x.description.replace(/([a-zA-Z]|\s)+/,''));
                else for(var f=3;f<20;f++)if(eval('new ActiveXObject("ShockwaveFlash.ShockwaveFlash.'+f+'")'))bm.m=f}catch(e){;}
                try{bm.y=bmN.javaEnabled()?1:0}catch(e){;}
                try{bmS=screen;bm.v^=bm.d=bmS.colorDepth||bmS.pixelDepth;bm.v^=bm.r=bmS.width}catch(e){;}
                r=bmD.referrer.replace(/^w+:\/\//,'');if(r&&r.split('/')[0]!=window.location.host){bm.f=escape(r).slice(0,400);bm.v^=r.length}
                bm.v^=window.location.href.length;for(var x in bm) if(/^[vstcnwmydrf]$/.test(x)) bs[i++]=x+bm[x];
                bmD.write('<a href="http://top.bigmir.net/show/radio/" target="_blank" onClick="img=new Image();img.src="//www.bigmir.net/?cl=16950850";"><img src="//c.bigmir.net/?'+bs.join('&')+'"  width="160" height="19" border="0" alt="bigmir)net TOP 100" title="bigmir)net TOP 100"></a>');
            </script>
            <noscript>
                <a href="http://top.bigmir.net/show/radio" target="_blank"><img src="//c.bigmir.net/?v16950850&s16950850&t32" width="160" height="19" alt="bigmir)net TOP 100" title="bigmir)net TOP 100" border="0" /></a>
            </noscript>
            <!--bigmir)net TOP 100-->
        </footer>
    </div>
    </body>
</html>
