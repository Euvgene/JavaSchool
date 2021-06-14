<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!doctype html>
<html lang="en-GB">
<header>
    <meta charset="utf-8">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="/css/file.css">
    <script type="text/javascript" src="/js/cart.js"></script>
</header>
<head id="header">
    <title>Cart</title>
    <jsp:include page="header.jsp"/>
</head>

<body class="d-flex flex-column h-100" onload="getCartProducts()">
<main class="flex-shrink-0">

    <div class="col-md-6" style="margin: auto">
        <h3 id="cartHeader" style="margin-top: 100px;"></h3>

        <div>
            <table class="table table-success table-striped" cellpadding="0" cellspacing="0"
                   style="border-collapse: separate;" aria-describedby="">
                <tr style="display: none">
                    <th id=""></th>
                </tr>
                <thead id="cartHead" style="font-size: 1.3rem;"></thead>
                <tbody id="example"></tbody>
            </table>
        </div>
        <div id="cartButtons">

        </div>
        <div id="errorMassage">
        </div>
    </div>
    <script type="text/javascript">
        function getCartProducts() {
            $.ajax({
                type: "GET",
                url: 'http://localhost:8189/api/v1/cart/' + localStorage.marketCartUuid,

                success: function (result) {
                    cartList = result.items;
                    let count = 0;

                    while (count < cartList.length) {
                        clearTable()
                        $('#cartHead').append(
                            " <tr>" +
                            "<td><fmt:message key="label.name"/></td>" +
                            "<td style='text-align: center'><fmt:message key="label.Quantity"/></td>" +
                            "<td ><fmt:message key="label.price"/></td>" +
                            "<td></td>" +
                            " </tr>");

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
                                    "<fmt:message key="label.Delete"/>" +
                                    "</button></td>");

                                $('#example').append(rd);
                            }


                            $('#example').append("<tr>" +
                                "<td style=\"justify-content:center; margin: auto;font-family:'Lucida Sans', " +
                                "'Lucida Sans Regular', 'Lucida Grande', 'Lucida Sans Unicode', Geneva, Verdana, sans-serif;" +
                                "font-weight: bold \"><fmt:message key="label.Total"/></td>" +
                                "<td></td>" +
                                "<td>" + result.totalPrice + ' $' + "</td>" +
                                "<td></td></tr>");
                            $('#cartHeader').append("<fmt:message key="label.cart"/>");
                            $('#cartButtons').append(
                                "<button class=\"btn btn-danger\" type='button' id=\"clearCart\" " +
                                "onclick=\"clearCart()\"" +
                                "style='margin-right: 10px'>" +
                                "<fmt:message key="label.clearCart"/>" +
                                "</button>" +
                                "<button class=\"btn btn-primary\"  onclick=\'goToOrderSubmit()\'>" +
                                "<fmt:message key="label.submitOrder"/>" +
                                "</button>" +
                                "<p id='logInMessage' style='color: red; display: none; margin-top: 10px'><fmt:message key="label.pleaseLogIn"/>!</p>");
                            document.getElementById('cartButtons').hidden = false;


                        }
                    }
                    if (cartList.length === 0) {
                        clearTable()
                        $('#cartHeader').append("<fmt:message key="label.EmptyCart"/>");
                    }
                    checkValidCount(cartList);
                }
            });
        }
    </script>
</main>

</body>
</html>