<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>&nbsp;Amplee's&nbsp;radio&nbsp;&#9210;</title>
    <link type="text/css" href="skin/pink.flag/jplayer.pink.flag.css" rel="stylesheet" />
    <link rel="shortcut icon" href="skin/image/favicon.ico?" type="image/x-icon" />
</head>
<body>
<div id="jquery_jplayer_1" class="jp-jplayer"></div>
<div id="jp_container_1" class="jp-audio-stream" role="application" aria-label="media player">
    <div class="jp-type-single">
        <div class="jp-gui jp-interface">
            <div class="jp-volume-controls">
                <button class="jp-mute" role="button" tabindex="0">mute</button>
                <button class="jp-volume-max" role="button" tabindex="0">max volume</button>
                <div class="jp-volume-bar">
                    <div class="jp-volume-bar-value"></div>
                </div>
            </div>
            <div class="subdiv">

                <div class="jp-controls">
                    <button class="jp-play" role="button" tabindex="0">play</button>
                </div>
                <div class="jp-details">
                    <div class="jp-title">
                        <marquee scrollamount="1" scrolldelay="50" direction="left" truespeed="" id="track-title">&nbsp;</marquee>
                        <p>Listeners: <span id="listeners">&nbsp;</span></p>
                    </div>
                </div>
            </div>

            <div tabindex="0" class="onclick-menu" id="recent">
               <!-- AJAX function getTitles() works here -->
            </div>
        </div>

        <div class="jp-no-solution">
            <span>Update Required</span>
            To play the media you will need to either update your browser to a recent version or update your <a href="http://get.adobe.com/flashplayer/" target="_blank">Flash plugin</a>.
        </div>
    </div>

</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="js/jquery.jplayer.min.js"></script>
<script src="js/player.js"></script>
</body>
</html>