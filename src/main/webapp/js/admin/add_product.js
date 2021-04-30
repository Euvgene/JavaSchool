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
    $("#newCategory").show(100);
    $("#addCategoryButton").show(100);
    $("#showNewCategoryForm").hide(100);
}

function hideCategoryForm() {
    $("#newCategory").hide(100);
    $("#addCategoryButton").hide(100);
    $("#showNewCategoryForm").show(100);
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
        success: function () {
            hideCategoryForm();
        }
    });
}

function createProduct() {
    let categories = JSON.parse(localStorage.getItem("categories"));

    const name = document.getElementById('fl_inp');
    const prodData = {
        productId: localStorage.productId,
        productTitle: $("#productName").val(),
        productPrice: $("#price").val(),
        category: categories.find(item => item.categoryName === $("#category").val()),
        productParams: {
            parametersId: localStorage.parametersId?localStorage.parametersId: null,
            productGender: $("#female").val() ? "Female" : "Mail",
            productAge: $("#age").val(),
            productWeight: $("#weight").val(),
            productLifespan: $("#lifespan").val(),
        },
        fotoId: name.files.item(0)!= null ? name.files.item(0).name : $('#fileName').val(),
        productQuantity: $("#count").val(),
    }
    console.log(prodData)
    $.ajax({
        type: $('#createProduct').val("Update product") ? "PUT" : "POST",
        contentType: "application/json",
        url: "http://localhost:8189/api/v1/products",
        data: JSON.stringify(prodData),
        dataType: 'json',
        success: function (result) {
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
            console.log(result.category.categoryName)
            console.log(result.productParams.productGender)
            localStorage.parametersId = result.productParams.parametersId
            $('#productName').val(result.productTitle)
            $('#category').val(result.category.categoryName)
            $('#gender').val(result.productParams.productGender.valueOf())
            $('#age').val(result.productParams.productAge)
            $('#weight').val(result.productParams.productWeight)
            $('#price').val(result.productPrice)
            $('#count').val(result.productQuantity)
            $('#lifespan').val(result.productParams.productLifespan)
            $('#fileName').val(result.fotoId)
            $('#divFoto').append("<p class=\"page-information\"><img src=\"/images/" + $('#fileName').val() + "\" + width=\"150\" height=\"150\"></p>");
            $('#createProduct').val("Update product")
        }
    })
}

$(document).ready(function () {
    hideCategoryForm();
    if (localStorage.productId) {
        getProduct(localStorage.productId)
    }
    $("#fl_inp").change(function () {
        const filename = $(this).val().replace(/.*\\/, "");
        $('#divFoto').empty();
        $('#divFoto').append("<p class=\"page-information\"><img src=\"/images/" + filename + "\" + width=\"150\" height=\"150\"></p>");
        $("#fl_nm").html(filename);
    });
    $("#showNewCategoryForm").click(function (event) {
        event.preventDefault();
        showNewCategoryForm()
    });

    $("#createProduct").click(function (event) {
        event.preventDefault();
        createProduct()
    });

});

