const delay = 3500;
let cartList = null

let clearTable = function () {
    $('#example').empty();
    $('#cartHead').empty();
    $('#cartHeader').empty();
    $("#errorMassage").empty()
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
        }, error: function () {
            $("#errorMassage").append("<input type=\"text\" " +
                "disabled    class=\"errorText\" style=\"text-align: center; width: 100%; border: none;outline: none;\"" +
                " value=' Sorry you order become invalid we will refresh it'>");
            setTimeout(function () {
                getOrderProducts();
            }, delay);
        }
    });
}


getOrderProducts = function () {
    $.ajax({
        type: "GET",
        url: 'http://localhost:8189/api/v1/cart/' + localStorage.marketCartUuid,

        success: function (result) {
            cartList = result.items;
            let count = 0;
            clearTable()
            while (count < cartList.length) {
                $('#cartHead').append(
                    "                    <tr>" +
                    "                        <td>Title</td>" +
                    "                        <td >Quantity</td>" +
                    "                        <td >Price</td>" +
                    "                    </tr>");

                if (cartList.length > 0) {
                    for (let k = 0; k < cartList.length; k++) {

                        let rd = $('<tr class="" style="height: 55px; vertical-align: 30;"></tr>');
                        count++;
                        rd.append(
                            "<td style=\"justify-content:center; margin: auto;font-family:'Lucida Sans', " +
                            "'Lucida Sans Regular', 'Lucida Grande', 'Lucida Sans Unicode', Geneva, Verdana, sans-serif;" +
                            "font-weight: bold \">"
                            + cartList[k].productTitle + "</td>" +
                            "<td class=\"justify-content-md-center\" >" +
                            "<div class=\"col-6 col-md-4\" style=\"text-align: center\">" + cartList[k].quantity + "</div>" +
                            " </td>" +
                            "<td '> " + cartList[k].price + ' $' + "</td>");

                        $('#example').append(rd);
                    }
                    $('#example').append("<tr>" +
                        "<td style=\"justify-content:center; margin: auto;font-family:'Lucida Sans', " +
                        "'Lucida Sans Regular', 'Lucida Grande', 'Lucida Sans Unicode', Geneva, Verdana, sans-serif;" +
                        "font-weight: bold \">Total price</td>" +
                        "<td></td>" +
                        "<td>" + result.totalPrice + ' $' + "</td>" +
                        "</tr>");
                    $('#cartHeader').append("Order");
                }
            }
            if (cartList.length === 0) {
                clearTable()
                $('#orderConfirmDiv').empty();
                $('#cartHeader').append("Order is empty");
                setTimeout(function () {
                    location.assign("http://localhost:8189/user-products")
                }, delay);
            }

        }
    });
}

appendMessage = function (response) {
    $("#errorMassage").append("<input type=\"text\" " +
        "disabled    class=\"errorText\" style=\"text-align: center; width: 100%; border: none;outline: none;\"" +
        " value='" + response + "'>")
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

    $('#creditCart').click(function () {
        if ($(this).is(':checked')) {
            $('#cash').prop("checked", false)
            $('#creditCartPayment').show(100);
            $("#confirmOrder").prop('value', "Pay for the order")

        } else {
            $('#cash').prop("checked", true)
            $('#creditCartPayment').hide(100);
            $("#confirmOrder").prop('value', "Create order")
        }
    });

    $('#cash').click(function () {
        if ($(this).is(':checked')) {
            $('#creditCart').prop("checked", false)
            $('#creditCartPayment').hide(100);
            $("#confirmOrder").prop('value', "Create order")
        } else {
            $('#creditCart').prop("checked", true)
            $('#creditCartPayment').show(100);
            $("#confirmOrder").prop('value', "Pay for the order")
        }
    });

    $('#confirmOrder').click(function () {
        checkValidOrder()
    });

});



