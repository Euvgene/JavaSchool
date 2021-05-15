<%--
  Created by IntelliJ IDEA.
  User: julia
  Date: 17.04.2021
  Time: 17:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<header>
    <meta charset="utf-8">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="/css/file.css">
    <script type="text/javascript" src="/js/common/new_product_validation.js"></script>
    <script type="text/javascript" src="/js/admin/add_product.js"></script>

</header>
<head>
    <%@ include file="adminHeader.jsp" %>
    <title>Add New Product</title>
</head>
<body class="d-flex flex-column h-100">
<main class="flex-shrink-0" style="margin-top: 80px;">
    <div class="container mt-auto">
        <div class="col-md-5" style="margin: auto" id="mainDiv">
            <h4 class="mb-4">Create product</h4>
            <div class="row g-3">
                <form id="productNameForm" class="col-md-5 validate" style="white-space: nowrap">
                    <label for="productName" class="form-label">Product name</label>
                    <input type="text" class="form-control" id="productName" placeholder="Product name"
                           name="productName">
                </form>

                <form class="col-md-3 validate" id="categoryNameForm">
                    <label for="category" class="form-label">Category</label>
                    <select class="form-select" id="category" name="category">
                        <option >Choose...</option>
                    </select>
                </form>

                <div class="col-md-3" style="margin-top: 47px; white-space: nowrap">
                    <button id="showNewCategoryForm" class="btn btn-primary" type="submit">Add category</button>
                    <button id="showChangeCategoryForm" class="btn btn-warning" type="submit" style="width: 117px"><svg width="1em" height="1em" viewBox="0 0 16 16" class="bi bi-arrow-counterclockwise" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                        <path fill-rule="evenodd" d="M12.83 6.706a5 5 0 0 0-7.103-3.16.5.5 0 1 1-.454-.892A6 6 0 1 1 2.545 5.5a.5.5 0 1 1 .91.417 5 5 0 1 0 9.375.789z"/>
                        <path fill-rule="evenodd" d="M7.854.146a.5.5 0 0 0-.708 0l-2.5 2.5a.5.5 0 0 0 0 .708l2.5 2.5a.5.5 0 1 0 .708-.708L5.707 3 7.854.854a.5.5 0 0 0 0-.708z"/>
                    </svg> category</button>
                </div>
            </div>

            <div class="row g-3" id="newCategoryDiv">
                <form class="col-md-4" id="newCategoryForm">
                    <input type="text" class="form-control" id="newCategory" placeholder="New category name"
                           name="newCategoryName">
                </form>

                <div class="col-md-3" style="white-space: nowrap">
                    <button id="addCategoryButton" class="btn btn-primary" onclick="createNewCategory()">Add
                    </button>
                </div>
            </div>

            <div class="row g-3" id="changeCategoryDiv">
                <form class="col-md-4" id="changeCategoryForm">
                    <input type="text" class="form-control" id="changeCategory" placeholder="New category name"
                           name="newCategoryName">
                </form>

                <div class="col-md-3" style="white-space: nowrap">
                    <button id="changeCategoryButton" class="btn btn-info" onclick="changeCategory()">Change
                    </button>
                </div>
            </div>

            <form id="middleForm">


                <div class="row g-3">
                    <div class="col-md-4">
                        <label for="gender" class="form-label">Gender</label>
                        <select class="form-select" id="gender" name="gender">
                            <option value="0">Choose...</option>
                            <option id="female" value="1">Female</option>
                            <option id="mail" value="2">Mail</option>
                        </select>
                    </div>

                    <div class="col-md-4">
                        <label for="age" class="form-label">Age</label>
                        <input type="number" class="form-control" id="age" style="min-width: 150px" placeholder="Age"
                               name="age">

                    </div>

                    <div class="col-md-3" style="white-space: nowrap">
                        <label for="weight" class="form-label">Weight, kg</label>
                        <input type="number" class="form-control" id="weight" placeholder="Weight" name="weight">

                    </div>
                </div>

                <div class="row g-3" style="margin-top: auto">
                    <div class="col-md-4">
                        <label for="price" class="form-label">Price</label>
                        <input type="number" class="form-control" id="price" placeholder="$" name="price">

                    </div>

                    <div class="col-md-4">
                        <label for="count" class="form-label">Count</label>
                        <input type="number" class="form-control" id="count" placeholder="Count" name="count">
                    </div>

                    <div class="col-md-3">
                        <label for="lifespan" class="form-label">Lifespan</label>
                        <input type="text" class="form-control" id="lifespan" placeholder="Lifespan" name="lifespan">

                    </div>
                </div>
                <div class="row g-3" style="margin-top: auto">
                    <div id="divFoto"></div>
                    <div class="fl_upld col-md-4">
                        <label><input id="fl_inp" type="file">Choose file</label>
                    </div>
                    <div class="col-md-7" id="fl_nm" style="margin-left: 0px">
                        <input id="fileName" style=" border: white; width: 100%" value="No file selected" disabled/>
                    </div>
                </div>
            </form>
            <input type='button' id="createProduct" class="btn btn-primary btn-lg" style="margin-top: 30px"
                   value="Create product"/>
        </div>
        <div id="errorMassage" >
        </div>
    </div>
</main>

<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.1/jquery.validate.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.1/additional-methods.min.js"></script>
</body>
</html>
