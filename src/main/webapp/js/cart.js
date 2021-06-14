let cartList = null;

checkValidCount = function () {
    $.ajax({
        type: "GET",
        url: "http://localhost:8189/api/v1/cart/clear",
        data: {
            uuid: localStorage.marketCartUuid
        },
        error: function (response) {
            $("#errorMassage").append("<input type=\"text\" " +
                "disabled    class=\"errorText\" style=\"text-align: center; width: 100%; border: none;outline: none;\"" +
                " value='" + response.responseJSON.message + "'>")
            const delay = 2500;
            setTimeout(function () {
                getCartProducts();
            }, delay);
        }
    });
}



goToOrderSubmit = function () {
    if (!localStorage.currentUserName) {
        document.getElementById("logInMessage").style.display = "block";
    } else {
        location.assign("http://localhost:8189/order-confirmation")
    }
}

clearTable = function () {
    $('#example').empty();
    $('#cartHead').empty();
    $('#cartHeader').empty();
    $('#cartButtons').empty();
    $("#errorMassage").empty()
    document.getElementById('cartButtons').hidden = true;
}

clearCart = function () {
    $.ajax({
        type: "POST",
        url: "http://localhost:8189/api/v1/cart/clear",
        data: {
            uuid: localStorage.marketCartUuid
        },
        success: function () {
            getCartProducts();
        }
    });
}

deleteCartProductById = function (productId, i) {
    $.ajax({
        type: "POST",
        url: "http://localhost:8189/api/v1/cart/delete",
        data: {
            uuid: localStorage.marketCartUuid,
            product_id: productId,
            updateNumber: i

        },
        success: function () {
            getCartProducts();
        }
    });
}

incrementProductInCart = function (id) {
    $.ajax({
        type: "POST",
        url: "http://localhost:8189/api/v1/cart/add",
        data: {
            uuid: localStorage.marketCartUuid,
            prod_id: id
        },
        success: function () {
            getCartProducts();
        }
    });
}
$(document).ready(function () {
});
