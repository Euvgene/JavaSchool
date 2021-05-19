let methodeName = "POST";
let newNameCategory = null;
let newCategoryName = null;
let productId = null;


appendMessage = function (response) {
    $("#errorMassage").append("<input type=\"text\" " +
        "disabled    class=\"errorText\" style=\"text-align: center; width: 100%; border: none;outline: none;\"" +
        " value='" + response + "'>")
}

function loadCategory() {
    hideInputForm()
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

function showNewCategoryForm() {
    $("#newCategoryDiv").show(100);
}

function hideInputForm() {
    $("#newCategoryDiv").hide();
    $("#changeCategoryDiv").hide();

}

changeCategory = function () {
    let categories = JSON.parse(localStorage.getItem("categories"));
    let cat = categories.find(item => item.categoryName === $("#category").val())
    let newName = $("#changeCategory").val();
    if ($("#changeCategoryForm").valid()) {
        const formData = {
            categoryName: newName
        }
        $.ajax({
            type: "PUT",
            contentType: "application/json",
            url: "http://localhost:8189/api/v1/category/" + cat.categoryName,
            data: JSON.stringify(formData),
            dataType: 'json',
            success: function () {
                newNameCategory = newName;
                $("#category option:contains('Choose...')").attr('selected', 'selected')
                loadCategory();

            }, error: function (response) {
                appendMessage(response.responseJSON.message)
            }
        });
    }
}

function createNewCategory() {
    let newName = $("#newCategory").val();
    if ($("#newCategoryForm").valid()) {
        const formData = {
            categoryName: newName
        }
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "http://localhost:8189/api/v1/category",
            data: JSON.stringify(formData),
            dataType: 'json',
            success: function () {
                newCategoryName = newName;
                loadCategory()
            }, error: function (response) {
                appendMessage(response.responseJSON.message)
            }
        });

    }
}

function createProduct(methode) {
    if (methode === "POST") {
        productId = null;
        localStorage.parametersId = null;
    }
    let isValid = $("#productNameForm").valid() && $("#categoryNameForm").valid() && $("#middleForm").valid();
    if (isValid) {
        let categories = JSON.parse(localStorage.getItem("categories"));
        const e = document.getElementById("gender");
        const name = document.getElementById('fl_inp');
        const prodData = {
            productId: productId,
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
            $("#gender option:contains(" + result.productParams.productGender.valueOf() + ")").prop('selected', true)
            $('#fileName').val(result.fotoId)
            $('#divFoto').append("<p class=\"page-information\"><img src=\"/images/" + $('#fileName').val() + "\" + width=\"150\" height=\"150\"></p>");
            $('#createProduct').val("Update product")
            delete localStorage.productId
            methodeName = "PUT";
            checkCategory();
        }
    })
}

function checkCategory() {
    const e = document.getElementById("category");
    if (e.options[e.selectedIndex].text === "Choose...") {
        $("#showNewCategoryForm").css('display', 'block')
        $("#showChangeCategoryForm").css('display', 'none')
    } else {
        $("#showNewCategoryForm").css('display', 'none')
        $("#showChangeCategoryForm").css('display', 'block')
    }
}

function showChangeCategoryDiv() {
    const e = document.getElementById("category");
    $("#changeCategoryDiv").show(100);
    $("#changeCategory").val(e.options[e.selectedIndex].text);
}

$(document).ready(function () {
    loadCategory()
    if (localStorage.productId) {
        productId = localStorage.productId
        getProduct(productId)
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
    $("#category").on('change', function (event) {
        event.preventDefault();
        hideInputForm()
        checkCategory()
    })

    $("#showChangeCategoryForm").click(function (event) {
        event.preventDefault();
        showChangeCategoryDiv()
    });
});

