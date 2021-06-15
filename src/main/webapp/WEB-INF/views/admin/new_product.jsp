<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!doctype html>
<html lang="en-GB">
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
    <title>Add New Product</title>
    <%@ include file="adminHeader.jsp" %>

</head>
<body class="d-flex flex-column h-100">
<main class="flex-shrink-0" style="margin-top: 80px;">
    <div class="container mt-auto">
        <div class="col-md-5" style="margin: auto" id="mainDiv">
            <h4 class="mb-4"><fmt:message key="label.createProduct"/></h4>
            <div class="row g-3">
                <form id="productNameForm" class="col-md-5 validate" style="white-space: nowrap">
                    <label for="productName" class="form-label"><fmt:message key="label.name"/></label>
                    <input type="text" class="form-control" id="productName"
                           placeholder="<fmt:message key="label.name"/>"
                           name="productName">
                </form>

                <form class="col-md-3 validate" id="categoryNameForm">
                    <label for="category" class="form-label"><fmt:message key="label.category"/></label>
                    <select class="form-select" id="category" name="category">
                        <option><fmt:message key="label.choose"/></option>
                    </select>
                </form>

                <div class="col-md-3" style="margin-top: 47px; white-space: nowrap">
                    <button id="showNewCategoryForm" class="btn btn-primary" type="submit"><fmt:message
                            key="label.addCategory"/></button>
                    <button id="showChangeCategoryForm" class="btn btn-warning" type="submit" style="width: 117px">
                        <svg width="1em" height="1em" viewBox="0 0 16 16" class="bi bi-arrow-counterclockwise"
                             fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                            <path fill-rule="evenodd"
                                  d="M12.83 6.706a5 5 0 0 0-7.103-3.16.5.5 0 1 1-.454-.892A6 6 0 1 1 2.545 5.5a.5.5 0 1 1 .91.417 5 5 0 1 0 9.375.789z"/>
                            <path fill-rule="evenodd"
                                  d="M7.854.146a.5.5 0 0 0-.708 0l-2.5 2.5a.5.5 0 0 0 0 .708l2.5 2.5a.5.5 0 1 0 .708-.708L5.707 3 7.854.854a.5.5 0 0 0 0-.708z"/>
                        </svg>
                        <fmt:message key="label.category"/></button>
                </div>
            </div>

            <div class="row g-3" id="newCategoryDiv" style="margin-top: 5px">
                <form class="col-md-4" id="newCategoryForm">
                    <input type="text" class="form-control" id="newCategory"
                           placeholder="<fmt:message key="label.newCategoryName" />"
                           name="newCategoryName">
                </form>

                <div class="col-md-3" style="white-space: nowrap">
                    <button id="addCategoryButton" class="btn btn-primary" onclick="createNewCategory()"><fmt:message
                            key="label.add"/>
                    </button>
                </div>
            </div>

            <div class="row g-3" id="changeCategoryDiv" style="margin-top: 5px">
                <form class="col-md-4" id="changeCategoryForm">
                    <input type="text" class="form-control" id="changeCategory"
                           placeholder="<fmt:message key="label.newCategoryName" />"
                           name="newCategoryName">
                </form>

                <div class="col-md-3" style="white-space: nowrap">
                    <button id="changeCategoryButton" class="btn btn-info" onclick="changeCategory()"><fmt:message
                            key="label.change"/>
                    </button>
                </div>
            </div>

            <form id="middleForm">


                <div class="row g-3">
                    <div class="col-md-4">
                        <label for="gender" class="form-label"><fmt:message key="label.gender"/></label>
                        <select class="form-select" id="gender" name="gender">
                            <option value="0"><fmt:message key="label.choose"/></option>
                            <option id="female" value="1"><fmt:message key="label.female"/></option>
                            <option id="mail" value="2"><fmt:message key="label.male"/></option>
                        </select>
                    </div>

                    <div class="col-md-4">
                        <label for="age" class="form-label"><fmt:message key="label.age"/></label>
                        <input type="number" class="form-control" id="age" style="min-width: 150px"
                               placeholder="<fmt:message key="label.age" />"
                               name="age">

                    </div>

                    <div class="col-md-3" style="white-space: nowrap">
                        <label for="weight" class="form-label"><fmt:message key="label.weight"/></label>
                        <input type="number" class="form-control" id="weight"
                               placeholder="<fmt:message key="label.weight" />" name="weight">

                    </div>
                </div>

                <div class="row g-3" style="margin-top: auto">
                    <div class="col-md-4">
                        <label for="price" class="form-label"><fmt:message key="label.price"/></label>
                        <input type="number" class="form-control" id="price" placeholder="$" name="price">

                    </div>

                    <div class="col-md-4">
                        <label for="count" class="form-label"><fmt:message key="label.Quantity"/></label>
                        <input type="number" class="form-control" id="count"
                               placeholder="<fmt:message key="label.Quantity" />" name="count">
                    </div>

                    <div class="col-md-3">
                        <label for="lifespan" class="form-label"><fmt:message key="label.Lifespan"/></label>
                        <input type="text" class="form-control" id="lifespan"
                               placeholder="<fmt:message key="label.Lifespan" />" name="lifespan">

                    </div>
                </div>
                <div class="row g-3" style="margin-top: auto">
                    <div id="divFoto"></div>
                    <div class="fl_upld col-md-4" style="white-space: nowrap;">
                        <label><input id="fl_inp" type="file"><fmt:message key="label.chooseFile"/></label>
                    </div>
                    <div class="col-md-7" id="fl_nm" style="margin-left: 0px">
                        <input id="fileName" style=" border: white; width: 100%"
                               placeholder="<fmt:message key="label.noFileSelected" />" disabled/>
                    </div>
                </div>
            </form>
            <input type='button' id="createProduct" class="btn btn-primary btn-lg" style="margin-top: 30px"
                   value="<fmt:message key="label.createProduct" />"/>
        </div>
        <div id="errorMassage">
        </div>
    </div>
    <script type="text/javascript">
        function loadCategory() {
            hideInputForm()
            $('#category').empty();
            $('#errorMassage').empty();
            $.ajax({
                type: "GET",
                url: 'http://localhost:8189/api/v1/category',
                success: function (result) {
                    $('#category').append('   <option value=""><fmt:message key="label.choose" /></option>');
                    localStorage.setItem("categories", JSON.stringify(result));
                    for (let i = 0; i < result.length; i++) {
                        $('#category').append('<option>' + result[i].categoryName + '</option>');
                    }
                    if (newNameCategory !== null) {
                        $('#category').val(newNameCategory)
                    } else if (newCategoryName !== null) {
                        $('#category').val(newCategoryName)
                    }
                    checkCategory()
                    return result
                }, error: function (response) {

                }
            });
        }

        function getProduct(productId) {
            $.ajax({
                type: "GET",
                url: 'http://localhost:8189/api/v1/products/' + productId,
                headers: {
                    "Authorization": "Bearer " + localStorage.token
                }, success: function (result) {
                    console.log(result)
                    localStorage.parametersId = result.productParams.parametersId
                    $('#productName').val(result.productTitle)
                    $('#category').val(result.category.categoryName)
                    if (result.productParams.productGender === "Female") {
                        $('#gender option[value=1]').prop('selected', true)
                    } else {
                        $('#gender option[value=2]').prop('selected', true)
                    }
                    $('#age').val(result.productParams.productAge)
                    $('#weight').val(result.productParams.productWeight)
                    $('#price').val(result.productPrice)
                    $('#count').val(result.productQuantity)
                    $('#lifespan').val(result.productParams.productLifespan)
                    $("#gender option:contains(" + result.productParams.productGender.valueOf() + ")").prop('selected', true)
                    $('#fileName').val(result.fotoId)
                    $('#divFoto').append("<p class=\"page-information\"><img src=\"/images/" + $('#fileName').val() + "\" + width=\"150\" height=\"150\"></p>");
                    $('#createProduct').val("<fmt:message key="label.updateProduct" />")
                    methodeName = "PUT";
                    checkCategory();
                }
            })
        }
    </script>
</main>

<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.1/jquery.validate.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.1/additional-methods.min.js"></script>
</body>
</html>
