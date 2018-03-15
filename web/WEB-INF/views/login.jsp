<%@page language="java" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="include-head.jsp"/>
    <title>Login - Amplee Radio</title>
</head>
<body>
    <div class="container">
        <div class="row">
                <div class="jumbotron col-md-6 col-md-offset-3">
                ${loginError}
                <div class="form-group form">
                    <form action="login" method="post">
                        <div>
                            <label>Username</label>
                            <input type="text" id="userId" name="userId" placeholder="user name" class="form-control"/>
                        </div>
                        <div>
                            <label>Password</label>
                            <input type="password" id="password" name="password" placeholder="password" class="form-control"/>
                        </div>
                        <button class="form-control btn btn-success" id="loginButton">Login</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
