<%@page language="java" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="include-head.jsp"/>
    <title>Registration - Amplee Radio</title>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="jumbotron col-md-6 col-md-offset-3">
            ${registerError}
            ${registerSuccess}

            <div class="form-group form">
                <form action="register" method="post">
                    <div>
                        <label>Username</label>
                        <input type="text" id="userId" name="userId" placeholder="username" required class="form-control"/>
                    </div>
                    <div>
                        <label>Password</label>
                        <input type="password" id="password" name="password" placeholder="password" required class="form-control"/>
                    </div>
                    <div>
                        <label>E-mail</label>
                        <input type="email" id="email" name="email" placeholder="e-mail" required class="form-control"/>
                    </div>
                    <button class="form-control btn btn-success" id="registerButton">Register</button>
                </form>
            </div>
        </div>
    </div>

</div>
</body>
</html>