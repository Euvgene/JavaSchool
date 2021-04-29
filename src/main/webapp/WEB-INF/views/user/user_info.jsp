<%--
  Created by IntelliJ IDEA.
  User: julia
  Date: 28.04.2021
  Time: 12:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <meta charset="utf-8">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
    <script type="text/javascript" src="/js/user/user_info.js"></script>
    <title>Update user info</title>
    <jsp:include page="userHeader.jsp"/>
</head>

<body class="bg-light" onload="getUser()">

<div class="container">
    <main style="">

        <div class="row g-5" style="justify-content: center">
            <div class="col-md-8 col-lg-8" style="justify-content: center">
                <h4 class="mb-3" style="margin-top: 100px;">Update user info</h4>
                <form class="needs-validation" novalidate="">
                    <div class="row g-3">
                        <div class="col-sm-3">
                            <label for="firstName" class="form-label">First Name/Username</label>
                            <div class="input-group has-validation">

                                <input type="text" class="form-control" id="firstName" placeholder="First Name/Username" required="">
                                <div class="invalid-feedback">
                                    Your username is required.
                                </div>
                            </div>
                        </div>

                        <div class="col-sm-3">
                            <label for="lastName" class="form-label">Last name</label>
                            <input type="text" class="form-control" id="lastName" placeholder="Last Name" value="" required="">
                            <div class="invalid-feedback">
                                Valid last name is required.
                            </div>
                        </div>


                        <div class="col-sm-3">
                            <label for="userPassword" class="form-label">Password</label>
                            <button class="btn btn-primary" type="submit" id="userPassword" style="width: 100%;" >
                                Change Password
                            </button>
                        </div>

                        <div class="col-sm-3">
                            <label for="birthday" class="form-label">Birthday</label>
                            <input type="date" class="form-control" id="birthday" placeholder="" value="" required="">
                            <div class="invalid-feedback">
                                Valid last name is required.
                            </div>
                        </div>

                        <div class="col-12">
                            <label for="email" class="form-label">Email </label>
                            <input type="email" class="form-control" id="email" placeholder="you@example.com">
                            <div class="invalid-feedback">
                                Please enter a valid email address for shipping updates.
                            </div>
                        </div>

                        <div class="row g-3" style="margin-top: 0">
                            <div class="col-md-7" style="white-space: nowrap">
                                <label for="street" class="form-label">Street name</label>
                                <input  type="text" class="form-control" id="street" placeholder="" value="" required="">
                                <div class="invalid-feedback">
                                    Valid first name is required.
                                </div>
                            </div>

                            <div class="col-md-3">
                                <label for="house_number" class="form-label">House number</label>
                                <input type="number" class="form-control" id="house_number" placeholder="" value="" required="">
                                <div class="invalid-feedback">
                                    Please provide a valid state.
                                </div>

                            </div>

                            <div class="col-md-2">
                                <label for="flat" class="form-label">Flat</label>
                                <input type="number" class="form-control" id="flat" value="" required="" style="min-width: 140px">

                                <div class="invalid-feedback">
                                    Please provide a valid state.
                                </div>
                            </div>
                        </div>

                        <div class="col-md-5">
                            <label for="country" class="form-label">Country</label>
                            <input type="text" class="form-control" id="country" placeholder="" value="" required="">
                            <div class="invalid-feedback">
                                Please select a valid country.
                            </div>
                        </div>

                        <div class="col-md-4">
                            <label for="city" class="form-label">City</label>
                            <input type="text" class="form-control" id="city" placeholder="" value="" required="">
                            <div class="invalid-feedback">
                                Please provide a valid state.
                            </div>
                        </div>

                        <div class="col-md-3">
                            <label for="zip" class="form-label">Zip</label>
                            <input type="text" class="form-control" id="zip" placeholder="" required="">
                            <div class="invalid-feedback">
                                Zip code required.
                            </div>
                        </div>
                    </div>

                    <hr class="my-4">

                    <button class="w-100 btn btn-primary btn-lg" type="submit" id="changeUser">Confirm changes</button>
                </form>
            </div>
        </div>
    </main>
</div>


</body>
</html>