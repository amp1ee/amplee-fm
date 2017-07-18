<%@ page import="com.amplee.myspringtest.config.application.RecentTracks" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    RecentTracks rc = RecentTracks.getInstance();
%>
<% List<String> titles = rc.getTitles(); %>
<% int size = titles.size(); %>
<% int s = 0;

if (size < 10 && size > 0) {
    s = size;
} else s = 10;
%>
<ul class="onclick-menu-content">

<%  if (size > 0)
        for (int i = size - 1; i >= size - s; i--) { %>
    <li><button onclick=""> <%= titles.get(i) %></button></li>
    <% } %>
</ul>


