function getOrderProducts() {
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

                console.log(cartList);
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

createOrder = function (isValid) {
    console.log(localStorage.marketCartUuid)
    const order = {
        cartId: localStorage.marketCartUuid,
        address: $('#deliveryToHome').is(':checked') ? $('#orderAddress').val() : "from store",
        paymentMethod: $('#creditCart').is(':checked') ? "credit card" : "cash"
    }
    if ($("#addressForm").valid() && $("#paymentForm").valid()) {
        if (isValid === 1) {
            $.ajax({
                type: "POST",
                url: "http://localhost:8189/api/v1/orders",
                headers: {
                    "Authorization": "Bearer " + localStorage.token
                },
                contentType: "application/json",
                data: JSON.stringify(order),
                success: function (result) {
                    console.log(result)
                    localStorage.orderUuid = result.orderId;
                    console.log(localStorage.orderUuid)
                    location.assign("http://localhost:8189/orders-result")

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
}

function checkValidCount() {
    let isValidCount = 0;
    return function () {
        $.ajax({
            type: "GET",
            url: "http://localhost:8189/api/v1/cart/clear",
            data: {
                uuid: localStorage.marketCartUuid
            },
            success: function () {
                isValidCount = 1;
            }, error: function () {
                $("#errorMassage").append("<input type=\"text\" " +
                    "disabled    class=\"errorText\" style=\"text-align: center; width: 100%; border: none;outline: none;\"" +
                    " value=' Sorry you order become invalid we will refresh it'>");
                isValidCount = 0;
                setTimeout(function () {
                    getOrderProducts();
                }, delay);
            }
        });
        console.log(isValidCount)
        return isValidCount;
    }
}

let valid = checkValidCount();

$(document).ready(function () {
    getOrderProducts()
    valid()
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

        } else {
            $('#cash').prop("checked", true)
            $('#creditCartPayment').hide(100);

        }
    });

    $('#cash').click(function () {
        if ($(this).is(':checked')) {
            $('#creditCart').prop("checked", false)
            $('#creditCartPayment').hide(100);
        } else {
            $('#creditCart').prop("checked", true)
            $('#creditCartPayment').show(100);
        }
    });

    $('#confirmOrder').click(function () {
        createOrder(valid());
    });

});



