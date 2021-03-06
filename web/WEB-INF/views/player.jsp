<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
    <head>
        <meta charset="utf-8"/>
        <meta name="_csrf" content="${_csrf.token}" />
        <meta name="_csrf_header" content="${_csrf.headerName}" />
        <jsp:include page="include-head.jsp"/>
    </head>
    <body>
        <nav class="navbar-wrapper navbar-default navbar-static-top">
            <div class="container-fluid">
                <div class="navbar-header">
                    <button class="navbar-toggle collapsed" id="toggle" data-toggle="collapse" data-target=".headerContent" aria-expanded="false">
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="logo navbar-brand" onclick="reload()">
                    </a>
                </div>
                <div class="collapse navbar-collapse headerContent">
                    <ul class="nav navbar-nav" id="toggles">
                        <li class="active"><a tabindex="0" role="button" id="amp" onclick="_setMedia('amp')"><span>Amplee radio</span></a></li>
                        <li><a tabindex="0" role="button" id="chl" onclick="_setMedia('chl')"><span>Chillout</span></a></li>
                        <li><a tabindex="0" role="button" id="dnb" onclick="_setMedia('dnb')"><span>Terrace</span></a></li>
                    </ul>
                    <div class="nav navbar-nav" id="canvas">
                        <div class="row" id="stations">
                            <div class="col-md-3" id="nowPlayingImg">
                                <!--*Magic*here*-->
                            </div>
                            <div class="col-md-3">
                                <div class="jp-jplayer" id="jquery_jplayer_1"></div>
                                <div id="jp_container_1" class="jp-audio-stream" role="application" aria-label="media player">
                                    <div class="jp-type-single">
                                        <div class="jp-gui jp-interface">

                                            <div class="subdiv">
                                                <div class="jp-controls">
                                                    <a tabindex="0" role="button"><i class="fa fa-play jp-play fa-2x gradient-icon" aria-hidden="true"></i></a>
                                                </div>
                                                <div class="jp-details">
                                                    <div class="jp-title">
                                                        <%--<marquee scrollamount="1" scrolldelay="50" direction="left" truespeed="" id="track-title">&nbsp;</marquee>--%>
                                                        <p id="track-title">&nbsp;</p>
                                                    </div>
                                                </div>

                                            </div>
                                            <%--DO: draggable volume--%>
                                            <div class="jp-volume-controls">
                                                <a tabindex="0" role="button"><i class="fa fa-volume-off jp-mute gradient-icon" aria-hidden="true"></i></a>
                                                <a tabindex="0" role="button"><i class="fa fa-volume-up jp-volume-max gradient-icon" tabindex="1" role="button" aria-hidden="true"></i></a>
                                                <div class="jp-volume-bar">
                                                    <div class="jp-volume-bar-value"></div>
                                                </div>

                                            </div>

                                            <div tabindex="0" class="onclick-menu" id="recent" onclick="toggleList()">
                                                <i role="button" class="fa fa-bars gradient-icon" aria-hidden="true" ></i>
                                            </div>
                                        </div>

                                        <div class="jp-no-solution">
                                            <span>Update Required</span>
                                            To play the media you will need to either update your browser to a recent version or update your <a href="http://get.adobe.com/flashplayer/" target="_blank">Flash plugin</a>.
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <c:choose>

                        <c:when test="${empty loggedInUser}">
                            <div class="row navbar-right">
                                <div class="auth navbar-text"><a tabindex="0" href="login" target="_blank"><span>Login</span> </a></div>
                                <div class="auth navbar-text"><a tabindex="0" href="register" target="_blank"><span>Register</span> </a></div>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="row navbar-right">
                                <div class="auth navbar-text"><a><span>Hello ${loggedInUser}! &nbsp;</span></a></div>
                                <div class="auth navbar-text"><a tabindex="0" id="logout" href="logout">Logout</a></div>
                            </div>
                        </c:otherwise>
                    </c:choose>
                    <%--<canvas id="analyzer"></canvas>--%>
                </div>
            </div>
        </nav>
        <div>

        </div>


    <script src="js/jquery.jplayer.min.js"></script>
    <script src="js/player.js"></script>
    <script type="text/javascript">
            function reload() {
                window.location.reload(false);
            }
    </script>
    </body>
</html>
