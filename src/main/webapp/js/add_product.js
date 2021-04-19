function loadCategory() {
    $("#newCategory").hide();
    $("#addCategoryButton").hide();
    $.ajax({
        type: "GET",
        url: 'http://localhost:8189/api/v1/category',
        success: function (result) {
            localStorage.setItem("categories", JSON.stringify(result));
            for (let i = 0; i < result.length; i++) {
                $('#category').append('<option>' + result[i].categoryName + '</option>');
            }
        }
    });
}

function showNewCategoryForm() {
    $("#newCategory").show();
    $("#addCategoryButton").show();
    $("#showNewCategoryForm").hide();
}

function hideCategoryForm() {
    $("#newCategory").hide();
    $("#addCategoryButton").hide();
    $("#showNewCategoryForm").show();
}

function createNewCategory() {
    const formData = {
        categoryName: $("#newCategory").val()
    }
    // DO POST
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "http://localhost:8189/api/v1/category",
        data: JSON.stringify(formData),
        dataType: 'json',
        success: function (result) {

        }
    });


}

function createProduct() {
    const paramsData = {
        productGender: $("#femail").val() ? "femail" : "mail",
        productAge: $("#age").val(),
        productWeight: $("#weight").val(),
        productLifespan: $("#lifespan").val(),
    }
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "http://localhost:8189/api/v1/params",
        data: JSON.stringify(paramsData),
        dataType: 'json',
        success: function (result) {
            localStorage.setItem("params", JSON.stringify(result));
        }
    });
    let categories = JSON.parse(localStorage.getItem("categories"));

    let params = JSON.parse(localStorage.getItem("params") || "[]");

    const prodData = {
        productTitle: $("#productName").val(),
        productPrice: $("#price").val(),
        category:categories.find(item => item.categoryName/*.some(deepItem => deepItem.label*/ === $("#category").val()),
        productParams: params,
        fotoId: $("#fotoId").val(),
        productQuantity: $("#count").val(),
    }

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "http://localhost:8189/api/v1/products",
        data: JSON.stringify(prodData),
        dataType: 'json',
        success: function (result) {
        }
    });
}

$(document).ready(function () {
    hideCategoryForm();
    $("#showNewCategoryForm").click(function (event) {
        event.preventDefault();
        showNewCategoryForm()
    });

    hideCategoryForm();
    $("#createProduct").click(function (event) {
        event.preventDefault();
        createProduct()
    });
});

