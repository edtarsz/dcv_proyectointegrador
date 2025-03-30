<%@ page isErrorPage="true" %>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Error</title>
    </head>
    <body>
        <h1>Error</h1>
        <p>Se produjo un error: <%= exception.getMessage()%></p>
        <pre>
            <% exception.printStackTrace(new java.io.PrintWriter(out));%>
        </pre>
    </body>
</html>