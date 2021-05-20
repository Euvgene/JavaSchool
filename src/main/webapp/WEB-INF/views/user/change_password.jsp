<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
        <h1 class="h3 mb-3 fw-normal">Change you password</h1>

        <div  style="margin-top: 15px">
            <label for="oldPassword">Old Password</label>
            <input type="password" class="form-control" id="oldPassword" name="password" >

        </div>
        <div  style="margin-top: 15px">
            <label for="firstPassword">New Password</label>
            <input type="password" class="form-control" id="firstPassword" name="password1">

        </div>

        <div  style="margin-top: 15px">
            <label for="secondPassword">New Password</label>
            <input type="password" class="form-control"  id="secondPassword" name="password2">
        </div>


        <button class="w-100 btn btn-lg btn-primary" type="submit" id="submitChanges" style="margin-top: 10px">Submit changes</button>
    </form>

    <div id="errorMassage" >
    </div>
</main>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.1/jquery.validate.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.1/additional-methods.min.js"></script>

</body>
</html>