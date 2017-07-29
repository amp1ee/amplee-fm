<%@ page import="com.amplee.myspringtest.config.application.RecentTracks" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    RecentTracks rc = RecentTracks.getInstance();
    Double offset = null;
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
        for (Cookie cooky : cookies) {
            if (cooky.getName().equals("TZOffset")) {
                offset = Double.parseDouble(cooky.getValue());
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

    <%  if (size > 0)
        for (int i = size - 1; i >= size - s; i--) {
            String curTitle = titles.get(i);
            String imgTag = rc.getImageTag(curTitle);
    %>
    <li> <%= imgTag %> <a href="#"><%= curTitle %></a></li>
    <%  }   %>
</ul>