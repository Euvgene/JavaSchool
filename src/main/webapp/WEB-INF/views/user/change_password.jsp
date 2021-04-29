<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <meta charset="utf-8">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
    <script type="text/javascript" src="/js/user/change_password.js"></script>
    <title>Change password</title>
    <jsp:include page="userHeader.jsp"/>
</head>

<body class="text-center">

<main class="form-signin" style="width:100%; max-width: 330px; padding: 15px; margin: auto">
    <form>
        <h1 class="h3 mb-3 fw-normal">Change you password</h1>

        <div class="form-floating" style="margin-top: 15px">
            <input type="password" class="form-control" id="oldPassword" placeholder="name@example.com">
            <label for="oldPassword">Old Password</label>
        </div>
        <div class="form-floating" style="margin-top: 15px">
            <input type="password" class="form-control" id="firstPassword" placeholder="Password">
            <label for="firstPassword">New Password</label>
        </div>

        <div class="form-floating" style="margin-top: 15px">
            <input type="password" class="form-control" id="secondPassword" placeholder="Password">
            <label for="secondPassword">New Password</label>
        </div>


        <button class="w-100 btn btn-lg btn-primary" type="submit" id="submitChanges" style="margin-top: 10px">Submit changes</button>
        <p class="mt-5 mb-3 text-muted">© 2017–2021</p>
    </form>
</main>


</body>
</html>