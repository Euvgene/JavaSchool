<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!doctype html>
<html lang="en-GB">
<head>
    <meta charset="utf-8">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="/css/file.css">
    <script type="text/javascript" src="/js/common/user_validation.js"></script>
    <script type="text/javascript" src="/js/user/change_password.js"></script>
    <title>Change password</title>
    <jsp:include page="userHeader.jsp"/>
</head>

<body class="text-center">

<main class="form-signin" style="width:100%; max-width: 330px; padding: 15px; margin: auto">
    <form id="form">
        <h1 class="h3 mb-3 fw-normal"><fmt:message key="label.changePassword"/></h1>

        <div  style="margin-top: 15px">
            <label for="oldPassword"><fmt:message key="label.oldPassword"/></label>
            <input type="password" class="form-control" id="oldPassword" name="password" >

        </div>
        <div  style="margin-top: 15px">
            <label for="firstPassword"><fmt:message key="label.newPassword"/></label>
            <input type="password" class="form-control" id="firstPassword" name="password1">

        </div>

        <div  style="margin-top: 15px">
            <label for="secondPassword"><fmt:message key="label.repeatNewPassword"/></label>
            <input type="password" class="form-control"  id="secondPassword" name="password2">
        </div>


        <button class="w-100 btn btn-lg btn-primary" type="submit" id="submitChanges" style="margin-top: 10px"><fmt:message key="label.confirmChanges"/></button>
    </form>

    <div id="errorMassage" >
    </div>
</main>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.1/jquery.validate.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.1/additional-methods.min.js"></script>

</body>
</html>