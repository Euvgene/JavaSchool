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
        success: function () {
            hideCategoryForm();
        }
    });
}

function createProduct() {
    let categories = JSON.parse(localStorage.getItem("categories"));
    const name = document.getElementById('fl_inp');
    const prodData = {
        productTitle: $("#productName").val(),
        productPrice: $("#price").val(),
        category: categories.find(item => item.categoryName === $("#category").val()),
        productParams: {
            productGender: $("#femail").val() ? "femail" : "mail",
            productAge: $("#age").val(),
            productWeight: $("#weight").val(),
            productLifespan: $("#lifespan").val(),
        },
        fotoId: name.files.item(0).name,
        productQuantity: $("#count").val(),
    }
console.log(prodData)
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
    $("#fl_inp").change(function(){
        var filename = $(this).val().replace(/.*\\/, "");
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

