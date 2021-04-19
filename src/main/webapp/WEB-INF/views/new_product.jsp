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
    <script type="text/javascript" src="/js/add_product.js"></script>
</header>
<head>
    <%@ include file="header.jsp" %>
    <title>Add New Product</title>
</head>
<body class="d-flex flex-column h-100" onload="loadCategory()">
<main class="flex-shrink-0" style="margin-top: 80px;">
    <div class="container mt-auto">
        <div class="col-md-5" style="margin: auto">
            <h4 class="mb-4">Create product</h4>
            <form class="needs-validation" novalidate="">
                    <div class="row g-3">
                        <div class="col-md-5" style="white-space: nowrap">
                            <label for="productName" class="form-label">Product name</label>
                            <input type="text" class="form-control" id="productName" placeholder="" value=""
                                   required="">
                            <div class="invalid-feedback">
                                Valid first name is required.
                            </div>
                        </div>

                        <div class="col-md-3" >
                            <label for="category" class="form-label">Category</label>
                            <select class="form-select" id="category" required="">
                                <option value="">Choose...</option>
                            </select>

                            <div class="invalid-feedback">
                                Please provide a valid state.
                            </div>

                        </div>

                        <div class="col-md-3" style="margin-top: 47px; white-space: nowrap">
                            <button id="showNewCategoryForm" class="btn btn-primary" type="submit">+new category</button>
                        </div>


                        <div class="col-md-4">
                            <input type="text" class="form-control" id="newCategory" placeholder="New category name"
                                   value=""
                                   required="">
                            <div class="invalid-feedback">
                                Please provide a valid state.
                            </div>
                        </div>

                        <div class="col-md-3" style="white-space: nowrap">
                            <button id="addCategoryButton" class="btn btn-primary" onclick="createNewCategory()">Add
                            </button>
                        </div>
                    </div>

                    <div class="row g-3" >
                        <div class="col-md-4">
                            <label for="gender" class="form-label">Gender</label>
                            <select class="form-select" id="gender" required="">
                                <option value="" >Choose...</option>
                                <option value="" id="femail">Femail</option>
                                <option value="" id="mail">Mail</option>
                            </select>

                            <div class="invalid-feedback">
                                Please provide a valid state.
                            </div>
                        </div>

                        <div class="col-md-4" >
                            <label for="age" class="form-label">Age</label>
                            <input type="number" class="form-control" id="age" style="min-width: 150px"
                                   value=""
                                   required="">

                            <div class="invalid-feedback">
                                Please provide a valid state.
                            </div>
                        </div>

                        <div class="col-md-3" style="white-space: nowrap">
                            <label for="weight" class="form-label">Weight, kg</label>
                            <input type="number" class="form-control" id="weight" placeholder="" value=""
                                   required="">
                            <div class="invalid-feedback">
                                Please select a valid weight.
                            </div>
                        </div>
                    </div>

                    <div class="row g-3" style="margin-top: auto">
                        <div class="col-md-3">
                            <label for="price" class="form-label">Price</label>
                            <input type="number" class="form-control" id="price" placeholder="" value=""
                                   required="">
                            <div class="invalid-feedback">
                                Please select a valid price.
                            </div>
                        </div>

                        <div class="col-md-2">
                            <label for="count" class="form-label">Count</label>
                            <input type="number" class="form-control" id="count" placeholder="" value=""
                                   required="">
                            <div class="invalid-feedback">
                                Please provide a valid state.
                            </div>
                        </div>

                        <div class="col-md-3">
                            <label for="fotoId" class="form-label">Foto id</label>
                            <input type="number" class="form-control" id="fotoId" placeholder="" required="">
                            <div class="invalid-feedback">
                                Foto id required.
                            </div>
                        </div>

                        <div class="col-md-3">
                            <label for="lifespan" class="form-label">Lifespan</label>
                            <input type="text" class="form-control" id="lifespan" placeholder="" required="">
                            <div class="invalid-feedback">
                                Lifespan required.
                            </div>
                        </div>
                    </div>

                    <button id="createProduct" class="btn btn-primary btn-lg" type="submit" style="margin-top: 50px">Create product</button>

            </form>
        </div>
    </div>
</main>
</body>
</html>
