const delay = 2500;
let cartList = null

let clearTable = function () {
    $('#example').empty();
    $('#cartHead').empty();
    $('#cartHeader').empty();
    $("#errorMassage").empty()
}

appendMessage = function (response) {
    $("#errorMassage").append("<input type=\"text\" " +
        "disabled    class=\"errorText\" style=\"text-align: center; width: 100%; border: none;outline: none;\"" +
        " value='" + response + "'>")
}

function checkValidCount(isChecked) {
    $.ajax({
        type: "GET",
        url: "http://localhost:8189/api/v1/cart/clear",
        data: {
            uuid: localStorage.marketCartUuid
        },
        success: function () {
            if (isChecked) createOrder()
        }, error: function (response) {
            appendMessage(response.responseJSON.message)
            setTimeout(function () {
                getOrderProducts();
            }, delay);
        }
    });
}






createOrder = function () {

    const order = {
        cartId: localStorage.marketCartUuid,
        address: $('#deliveryToHome').is(':checked') ? $('#orderAddress').val() : "from store",
        paymentMethod: $('#creditCart').is(':checked') ? "credit card" : "cash"
    }
    if ($("#addressForm").valid() && $("#paymentForm").valid()) {

        $.ajax({
            type: "POST",
            url: "http://localhost:8189/api/v1/orders",
            headers: {
                "Authorization": "Bearer " + localStorage.token
            },
            contentType: "application/json",
            data: JSON.stringify(order),
            success: function (result) {
                localStorage.orderUuid = result.orderId;
                location.assign("http://localhost:8189/orders-result")
            }, error: function (response) {
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


function checkValidOrder() {
    checkValidCount(true)
}

$(document).ready(function () {

    $('#cc-number').keyup(function() {
        let foo = $(this).val().split("-").join(""); // remove hyphens
        if (foo.length > 0) {
            foo = foo.match(new RegExp('.{1,4}', 'g')).join("-");
        }
        $(this).val(foo);
    });

    $('#cc-expiration').keyup(function() {
        let foo = $(this).val().split("/").join(""); // remove hyphens
        if (foo.length > 0) {
            foo = foo.match(new RegExp('.{1,2}', 'g')).join("/");
        }
        $(this).val(foo);
    });
    getOrderProducts()
    checkValidCount(false)
    $('#deliveryToHome').click(function () {
        if ($(this).is(':checked')) {
            $('#address').show(100);
            $('#fromStore').prop("checked", false)
        } else {
            $('#address').hide(100);
            $('#fromStore').prop("checked", true)
        }
    });

    $('#fromStore').click(function () {
        if ($(this).is(':checked')) {
            $('#deliveryToHome').prop("checked", false)
            $('#address').hide(100);
        } else {
            $('#deliveryToHome').prop("checked", true)
            $('#address').show(100);
        }
    });

    $('#confirmOrder').click(function () {
        checkValidOrder()
    });

});



