<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<!-- by Leah Gotti -->
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Amplee's&nbsp;radio&nbsp;&#9899;&nbsp;</title>

    <script type="text/javascript">
        function timeFunction(){

            var dateVar = new Date();
            var timezone = dateVar.getTimezoneOffset()/60 * (-1);
            document.cookie="TZOffset=" + timezone + ";path=/";
            //alert("have set cookie. Redirecting to original page.");
            try {
                window.location.replace("${redirectUrl}");
            } catch (e) {
                console.log(e);
            }
        }

        timeFunction();

    </script>

</head>
<body>loading the page...
</body>
</html>