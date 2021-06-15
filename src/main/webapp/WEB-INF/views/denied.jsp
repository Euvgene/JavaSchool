<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en-GB">
<head>
    <meta charset="UTF-8"/>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="/css/file.css">
    <title>Access Denied</title>
</head>
<body>

<div class="forbiddenPage">
    <p class="page-information"><img src="/images/di-UUKI.jpg" + width="500" height="500" alt=""></p>
    <h2>Not this time, access forbidden!</h2>
    <input class="btn btn-info" type="button" onclick="history.back();" value="Back"/>
</div>

</body>
</html>