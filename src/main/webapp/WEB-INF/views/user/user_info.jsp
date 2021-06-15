
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
    <script type="text/javascript" src="/js/user/user_info.js"></script>
    <title>Update user info</title>
    <jsp:include page="userHeader.jsp"/>
</head>

<body class="bg-light" onload="getUser()">

<div class="container">
    <main style="">

        <div class="row g-5" style="justify-content: center">
            <div class="col-md-8 col-lg-8" style="justify-content: center">
                <h4 class="mb-3" style="margin-top: 100px;"><fmt:message key="label.changeInfo" /></h4>
                <form  id="form" >
                    <div class="row g-3">
                        <div class="col-sm-3">
                            <label for="name" class="form-label"><fmt:message key="label.firstName" /></label>
                            <input type="text"  class="form-control" id="name" placeholder="<fmt:message key="label.firstName" />"  name="firstname">
                        </div>

                        <div class="col-sm-3">
                            <label for="lastName" class="form-label"><fmt:message key="label.lastName" /></label>
                            <input type="text" class="form-control" id="lastName" placeholder="<fmt:message key="label.lastName" />"  name="lastname" >

                        </div>

                        <div class="col-sm-3">
                            <label for="userPassword" class="form-label"><fmt:message key="label.password" /></label>
                            <input type="password" class="form-control" id="userPassword" placeholder="<fmt:message key="label.password" />" name="password"  >
                        </div>


                        <div class="col-sm-3">
                            <label for="userPasswordButton" class="form-label"><fmt:message key="label.changePassword" /></label>
                            <button class="btn btn-primary" type="submit" id="userPasswordButton" style="width: 100%;" >
                                <fmt:message key="label.changePassword" />
                            </button>
                        </div>

                        <div class="col-sm-3">
                            <label for="birthday" class="form-label"><fmt:message key="label.birthday" /></label>
                            <input type="date" class="form-control" id="birthday" name="birthday"  >

                        </div>

                        <div class="col-9">
                            <label for="email" class="form-label"><fmt:message key="label.email" /> </label>
                            <input type="email" class="form-control" id="email" name="email" placeholder="you@example.com" >
                        </div>

                        <div class="row g-3" style="margin-top: 0">
                            <div class="col-md-7" style="white-space: nowrap">
                                <label for="street" class="form-label"><fmt:message key="label.streetName" /></label>
                                <input  type="text" class="form-control" id="street" name= "street" placeholder="<fmt:message key="label.streetName" />"  >
                            </div>

                            <div class="col-md-3">
                                <label for="house_number" class="form-label"><fmt:message key="label.houseNumber" /></label>
                                <input type="number" class="form-control" id="house_number" name="house" placeholder="<fmt:message key="label.houseNumber" />" >
                            </div>

                            <div class="col-md-2">
                                <label for="flat" class="form-label"><fmt:message key="label.flat" /></label>
                                <input type="number" class="form-control" id="flat" placeholder="<fmt:message key="label.flat" />" name="flat" style="min-width: 140px">
                            </div>
                        </div>

                        <div class="col-md-5">
                            <label for="country" class="form-label"><fmt:message key="label.country" /></label>
                            <input type="text" class="form-control" id="country" name="country" placeholder="<fmt:message key="label.country" />" >
                        </div>

                        <div class="col-md-4">
                            <label for="city" class="form-label"><fmt:message key="label.city" /></label>
                            <input type="text" class="form-control" id="city" name="city" placeholder="<fmt:message key="label.city" />" >
                        </div>

                        <div class="col-md-3">
                            <label for="zip" class="form-label"><fmt:message key="label.zip" /></label>
                            <input type="text" class="form-control" id="zip" name="zip" placeholder="<fmt:message key="label.zip" />" >
                        </div>
                    </div>

                    <hr class="my-4">

                    <button class="w-100 btn btn-primary btn-lg" type="button" id="changeUser"><fmt:message key="label.confirmChanges" /></button>
                </form>
                <div id="validName" >
                </div>
                <div id="validEmail" >
                </div>
                <div id="errorMassage" >
                </div>
            </div>
        </div>
    </main>
</div>

<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.1/jquery.validate.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.1/additional-methods.min.js"></script>

</body>
</html>
