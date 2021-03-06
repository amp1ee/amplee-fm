<%@ page import="com.amplee.radio.config.application.RecentTracks" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    RecentTracks rc = RecentTracks.getInstance();
    Double offset = null;
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("TZOffset")) {
                offset = Double.parseDouble(cookie.getValue());
            }
        }
    }

    List<String> titles = rc.getTitles(offset == null ? 0.0 : offset);

    int size = titles.size();
    int s;

    if (size < 10 && size > 0) {
        s = size;
    } else s = 10;
%>

<ul class="onclick-menu-content">

    <%  if (size > 0) {
        String curTrack = titles.get(size - 1);
        rc.setCurImage(curTrack);
        for (int i = size - 1; i >= size - s; i--) {
            String curTitle = titles.get(i);

            String imgTag = rc.getImageTag(curTitle, false);

    %>
        <li><%= imgTag %> <a class="_tooltip" role="button" tabindex="0" data-toggle="tooltip"
                             data-placement="right" title="Click to search on YouTube" onclick="ytSearch(this)">
            <span id="title" class="tit"><%= curTitle %> </span></a></li>
    <%  }
    }
    %>

</ul>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
<script type="text/javascript">
    var circle = String.fromCharCode(9899);
    var searchHint = 'Click to search on YouTube';

    $(document).ready(function () {
        var span = $(".onclick-menu-content li a .tit");
        span.each(function() {

            var count = $(this).text().length;
            var max = 65;// Max chars inside the li
            if (count > max) {
                $(this).attr('title', $(this).text());
            } else {
                $(this).attr('title', searchHint);
            }
        });

    });

    function ytSearch(element) {
        var title = $(element).attr('title');
        var searchQuery, win;

        if (title === undefined || title === searchHint) {
            title = $(element).text();
        }
        if (title)
            title = title.substr(title.indexOf(circle) + 1).trim();
        else
            return;
        searchQuery = encodeURIComponent(title);
        win = window.open('https://www.youtube.com/results?search_query='+ searchQuery, '_blank');
        win.focus();
    }

    function copyToClipBoard(txt) {
        var $temp = $("<input>");

        $("body").append($temp);
        $temp.val(txt);
        $temp.select();
        document.execCommand("copy");
        $temp.remove();
    }

</script>
