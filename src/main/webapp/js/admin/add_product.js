let methodeName = "POST";


appendMessage = function (response) {
    $("#errorMassage").append("<input type=\"text\" " +
        "disabled    class=\"errorText\" style=\"text-align: center; width: 100%; border: none;outline: none;\"" +
        " value='" + response + "'>")
}

function loadCategory() {
    $("#newCategory").hide();
    $("#addCategoryButton").hide();
    $("#showNewCategoryForm").show(100);
    $('#category').empty();
    $.ajax({
        type: "GET",
        url: 'http://localhost:8189/api/v1/category',
        success: function (result) {

            $('#category').append('   <option value="">Choose...</option>');
            localStorage.setItem("categories", JSON.stringify(result));
            for (let i = 0; i < result.length; i++) {
                $('#category').append('<option>' + result[i].categoryName + '</option>');
            }
            return result
        }, error: function (response) {
            console.log(response)
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
    loadCategory()
}

function createNewCategory() {
    if ($("#newCategoryForm").valid()) {
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
                loadCategory()
            }, error: function (response) {
                console.log(response)
            }
        });

    }
}

function createProduct(methode) {
    if (methode === "POST") {
        localStorage.productId = null;
        localStorage.parametersId = null;
    }
        let isValid = $("#productNameForm").valid() && $("#categoryNameForm").valid() && $("#middleForm").valid();
        if (isValid) {
    let categories = JSON.parse(localStorage.getItem("categories"));
    const e = document.getElementById("gender");
    const name = document.getElementById('fl_inp');
    const prodData = {
        productId: localStorage.productId,
        productTitle: $("#productName").val(),
        productPrice: $("#price").val(),
        category: categories.find(item => item.categoryName === $("#category").val()),
        parameters: {
            parametersId: localStorage.parametersId,
            productGender: e.options[e.selectedIndex].text,
            productAge: $("#age").val(),
            productWeight: $("#weight").val(),
            productLifespan: $("#lifespan").val(),
        },
        fotoId: name.files.item(0) != null ? name.files.item(0).name : $('#fileName').val(),
        productQuantity: $("#count").val(),
    }
    console.log(prodData)
    $.ajax({
        type: methode,
        contentType: "application/json",
        url: "http://localhost:8189/api/v1/products",
        data: JSON.stringify(prodData),
        dataType: 'json',
        success: function (result) {
            document.getElementById("mainDiv").style.background = '#a8e3a4';
            const delay = 1500;
            setTimeout(function () {
                location.assign("http://localhost:8189/admin-products")
            }, delay);
        }, error: function (response) {
            console.log(response)
            if (response.responseJSON.message.length > 1) {
                for (let k = 0; k < response.responseJSON.message.length; k++) {
                    appendMessage(response.responseJSON.message[k])
                }
            } else {
                appendMessage(response.responseJSON.message)
            }
        }
    });
        }
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
            $('#gender').val(result.productParams.productGender.valueOf())
            $('#age').val(result.productParams.productAge)
            $('#weight').val(result.productParams.productWeight)
            $('#price').val(result.productPrice)
            $('#count').val(result.productQuantity)
            $('#lifespan').val(result.productParams.productLifespan)
            $("#gender option:contains("+ result.productParams.productGender.valueOf()+")").prop('selected', true)
            $('#fileName').val(result.fotoId)
            $('#divFoto').append("<p class=\"page-information\"><img src=\"/images/" + $('#fileName').val() + "\" + width=\"150\" height=\"150\"></p>");
            $('#createProduct').val("Update product")
            methodeName = "PUT";
        }
    })
}

$(document).ready(function () {
    loadCategory()
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
        $("#errorMassage").empty()
        createProduct(methodeName)
    });

});

