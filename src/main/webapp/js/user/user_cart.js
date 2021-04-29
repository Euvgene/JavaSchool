let cartList = null;

function getUserCartProducts() {
    $.ajax({
        type: "GET",
        url: 'http://localhost:8189/api/v1/cart/' + localStorage.marketCartUuid,
        headers: {
            "Authorization": "Bearer " + localStorage.token
        },
        success: function (result) {
            cartList = result.items;
            let count = 0;

            while (count < cartList.length) {
                clearTable()
                $('#cartHead').append(
                    "                    <tr>" +
                    "                        <td>Title</td>" +
                    "                        <td style='text-align: center'>Quantity</td>" +
                    "                        <td >Price</td>" +
                    "                        <td></td>" +
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
                            "<td class=\"row justify-content-md-center\" > <div class=\"col-6 col-md-4\" style=\"text-align: right\">" +
                            "<button type=\"button\" class=\"btn btn-outline-danger\" onclick= \"deleteCartProductById(" + cartList[k].productId + ")\">" +
                            "<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"16\" height=\"16\" fill=\"currentColor\" class=\"bi bi-bag-dash-fill\" viewBox=\"0 0 16 16\">" +
                            "<path fill-rule=\"evenodd\" d=\"M10.5 3.5a2.5 2.5 0 0 0-5 0V4h5v-.5zm1 0V4H15v10a2 2 0 0 1-2 2H3a2 2 0 0 1-2-2V4h3.5v-.5a3.5 3.5 0 1 1 7 0zM6 9.5a.5.5 0 0 0 0 1h4a.5.5 0 0 0 0-1H6z\"></path>" +
                            "</svg>" +
                            "</button> </div>" +
                            "<div class=\"col-6 col-md-2\" style=\"text-align: center\">" + cartList[k].quantity + "</div>" +
                            " <div class=\"col-6 col-md-4\" style=\"text-align: left\"><button type=\"button\" class=\"btn btn-primary\" onclick= \"incrementProductInCart(" + cartList[k].productId + ")\">" +
                            "<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"16\" height=\"16\" fill=\"currentColor\"" +
                            "class=\"bi bi-bag-plus-fill\" viewBox=\"0 0 16 16\" >" +
                            " <path fill-rule=\"evenodd\"" +
                            " d=\"M10.5 3.5a2.5 2.5 0 0 0-5 0V4h5v-.5zm1 0V4H15v10a2 2 0 0 1-2 2H3a2 2 0 0 1-2-2V4h3.5v-.5a3.5 3.5 0 1 1 7 0zM8.5 8a.5.5 0 0 0-1 0v1.5H6a.5.5 0 0 0 0 1h1.5V12a.5.5 0 0 0 1 0v-1.5H10a.5.5 0 0 0 0-1H8.5V8z\"></path>" +
                            "     </svg>" +
                            "</button></div> </td>" +
                            "<td '> " + cartList[k].price + ' $' + "</td>" +
                            "<td  '>" +
                            "<button type=\"button\" class=\"btn btn-danger\" onclick= \"deleteCartProductById(" + cartList[k].productId + ",1)\" >" +
                            "Delete" +
                            "</button></td>");

                        $('#example').append(rd);
                    }


                    $('#example').append("<tr>" +
                        "<td style=\"justify-content:center; margin: auto;font-family:'Lucida Sans', " +
                        "'Lucida Sans Regular', 'Lucida Grande', 'Lucida Sans Unicode', Geneva, Verdana, sans-serif;" +
                        "font-weight: bold \">Total price</td>" +
                        "<td></td>" +
                        "<td>" + result.totalPrice + ' $' + "</td>" +
                        "<td></td></tr>");
                    $('#cartHeader').append("Cart");
                    $('#cartButtons').append(
                    "<button class=\"btn btn-danger\" type='button' id=\"clearCart\" " +
                        "onclick=\"clearCart()\"" +
                        "style='margin-right: 10px'>" +
                        "Clear cart" +
                        "</button>" +
                        "<button class=\"btn btn-primary\"  onclick=\'goToOrderSubmit()\'>" +
                        "Submit order" +
                        "</button>" +
                        "<p id='logInMessage' style='color: red; display: none; margin-top: 10px'>Please Log in!</p>");
                    document.getElementById('cartButtons').hidden = false;


                }
            }
            if (cartList.length === 0) {
                clearTable()
                $('#cartHeader').append("Cart is empty");
            }
        }
    });
}

goToOrderSubmit = function (){
    if (!localStorage.currentUserName){
        document.getElementById("logInMessage").style.display= "block";
    }else {
        location.assign("http://localhost:8189/order-confirmation")
    }
}

clearTable = function () {
    $('#example').empty();
    $('#cartHead').empty();
    $('#cartHeader').empty();
    $('#cartButtons').empty();
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
            getUserCartProducts();
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
            getUserCartProducts();
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
            getUserCartProducts();
        }
    });
}
$(document).ready(function () {
});
