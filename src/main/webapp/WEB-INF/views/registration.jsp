<%--
  Created by IntelliJ IDEA.
  User: julia
  Date: 20.04.2021
  Time: 13:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html >
<header>
    <meta charset="utf-8">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="/css/file.css">
    <script type="text/javascript" src="/js/common/user_validation.js"></script>
    <script type="text/javascript" src="/js/registration.js"></script>
</header>
<head>
    <title>Registration</title>
    <jsp:include page="header.jsp" />

</head>

<body class="bg-light">

<div class="container">
    <main style="">

        <div class="row g-5" style="justify-content: center">
            <div class="col-md-8 col-lg-8" style="justify-content: center">
                <h4 class="mb-3" style="margin-top: 100px;">Registration new user</h4>
                <form  id="form" >
                    <div class="row g-3">
                        <div class="col-sm-3">
                            <label for="firstName" class="form-label">First Name/Username</label>
                            <input type="text"  class="form-control" id="firstName" placeholder="First Name/Username"  name="firstname">
                        </div>

                        <div class="col-sm-3">
                            <label for="lastName" class="form-label">Last name</label>
                            <input type="text" class="form-control" id="lastName" placeholder="Last Name"  name="lastname" >

                        </div>


                        <div class="col-sm-3">
                            <label for="userPassword" class="form-label">Password</label>
                            <input type="password" class="form-control" id="userPassword" placeholder="Password" name="password"  >
                        </div>

                        <div class="col-sm-3">
                            <label for="birthday" class="form-label">Birthday</label>
                            <input type="date" class="form-control" id="birthday" name="birthday"  >

                        </div>

                        <div class="col-12">
                            <label for="email" class="form-label">Email </label>
                            <input type="email" class="form-control" id="email" name="email" placeholder="you@example.com" >

                        </div>

                        <div class="row g-3" style="margin-top: 0">
                            <div class="col-md-7" style="white-space: nowrap">
                                <label for="street" class="form-label">Street name</label>
                                <input  type="text" class="form-control" id="street" name= "street" placeholder="Street name"  >
                            </div>

                            <div class="col-md-3">
                                <label for="house_number" class="form-label">House number</label>
                                <input type="number" class="form-control" id="house_number" name="house" placeholder="House number" >
                            </div>

                            <div class="col-md-2">
                                <label for="flat" class="form-label">Flat</label>
                                <input type="number" class="form-control" id="flat" placeholder="flat number" name="flat" style="min-width: 140px">
                            </div>
                        </div>

                        <div class="col-md-5">
                            <label for="country" class="form-label">Country</label>
                            <input type="text" class="form-control" id="country" name="country" placeholder="Country" >
                        </div>

                        <div class="col-md-4">
                            <label for="city" class="form-label">City</label>
                            <input type="text" class="form-control" id="city" name="city" placeholder="City" >
                        </div>

                        <div class="col-md-3">
                            <label for="zip" class="form-label">Zip</label>
                            <input type="text" class="form-control" id="zip" name="zip" placeholder="Zip" >
                        </div>
                    </div>

                    <hr class="my-4">

                    <button class="w-100 btn btn-primary btn-lg" type="button" id="checkout">Continue to checkout</button>
                </form>
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