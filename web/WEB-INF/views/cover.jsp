<%@ page import="com.amplee.radio.config.application.RecentTracks" %><%--
  Created by IntelliJ IDEA.
  User: djamp
  Date: 14.08.2017
  Time: 18:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

</head>
<body>
<%  RecentTracks rc = RecentTracks.getInstance();
    String curCover = rc.getCurCoverImage(); %>
<%= curCover %>
</body>
</html>
