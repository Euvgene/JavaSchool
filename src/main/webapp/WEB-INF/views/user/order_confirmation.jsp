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
    <script type="text/javascript" src="/js/common/order_validation.js"></script>
    <script type="text/javascript" src="/js/order_confirmation.js"></script>
</header>
<head id="header">
    <title>Order confirmation</title>
    <jsp:include page="userHeader.jsp"/>

</head>

<body class="d-flex flex-column h-100">
<main class="flex-shrink-0">

    <div class="col-md-5" style="margin: auto">
        <h3 id="cartHeader" style="margin-top: 90px;"></h3>

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

        <div id="orderConfirmDiv">
            <h4 ><fmt:message key="label.DeliveryTerms"/></h4>
            <div>
                <div>
                    <input id="deliveryToHome" type="checkbox" class="form-check-input" checked>
                    <label class="form-check-label" for="deliveryToHome" style="margin-left: 10px"><fmt:message key="label.NeedDelivery"/></label>
                </div>
                <div class="form-check">
                    <input id="fromStore" type="checkbox" class="form-check-input">
                    <label class="form-check-label" for="fromStore" style="margin-left: 6px"><fmt:message key="label.Delivery"/></label>
                </div>
            </div>
            <form id="addressForm">
                <div class="form-group" style="margin-bottom: 8px;" id="address">
                    <label for="orderAddress" style="margin-bottom: 7px"><fmt:message key="label.DeliveryAddress"/></label>
                    <input class="form-control" type="text" id="orderAddress" name="address">
                </div>
            </form>
            <h4><fmt:message key="label.PaymentMethod"/></h4>
            <div>
                <div class="form-check">
                    <input id="creditCart" type="checkbox" value="card" class="form-check-input" checked>
                    <label class="form-check-label" for="creditCart" style="margin-left: 10px"><fmt:message key="label.Card"/></label>
                </div>
                <div class="form-check">
                    <input id="cash" type="checkbox" value="cash" class="form-check-input">
                    <label class="form-check-label" for="cash" style="margin-left: 10px"><fmt:message key="label.Cash"/></label>
                </div>
            </div>
            <form id="paymentForm">
                <div class="row gy-3" id="creditCartPayment">
                    <div class="col-md-6">
                        <label for="cc-name" class="form-label"><fmt:message key="label.NameOnCard"/></label>
                        <input type="text" class="form-control" id="cc-name" placeholder="JOHN BLACK" name="cardname" style="text-transform: uppercase">
                    </div>

                    <div class="col-md-6">
                        <label for="cc-number" class="form-label"><fmt:message key="label.CreditCardNumber"/></label>
                        <input type="text" class="form-control" id="cc-number" placeholder="0000-0000-0000-0000"
                               name="number">

                    </div>

                    <div class="col-md-3">
                        <label for="cc-expiration" class="form-label"><fmt:message key="label.Expiration"/></label>
                        <input type="text" class="form-control" id="cc-expiration" placeholder="12/20"
                               name="expiration">

                    </div>

                    <div class="col-md-3">
                        <label for="cc-cvv" class="form-label">CVV</label>
                        <input type="text" class="form-control" id="cc-cvv" placeholder="888" name="cvv">
                    </div>

                </div>
            </form>
            <div style="margin-top: 10px">
                <input class="w-100 btn btn-primary" type="button" id="confirmOrder" value="<fmt:message key="label.Pay"/>" >
            </div>
        </div>
    </div>
    <div id="errorMassage">
    </div>

    <script type="text/javascript">
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
                            "<tr>" +
                            "<td><fmt:message key="label.name"/></td>" +
                            "<td ><fmt:message key="label.Quantity"/></td>" +
                            "<td ><fmt:message key="label.price"/></td>" +
                            "</tr>");

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
                                "font-weight: bold \"><fmt:message key="label.Total"/></td>" +
                                "<td></td>" +
                                "<td>" + result.totalPrice + ' $' + "</td>" +
                                "</tr>");
                            $('#cartHeader').append("<fmt:message key="label.Order"/>");
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

        $('#creditCart').click(function () {
            if ($(this).is(':checked')) {
                $('#cash').prop("checked", false)
                $('#creditCartPayment').show(100);
                $("#confirmOrder").prop('value', "<fmt:message key="label.Pay"/>")

            } else {
                $('#cash').prop("checked", true)
                $('#creditCartPayment').hide(100);
                $("#confirmOrder").prop('value', "<fmt:message key="label.CreateOrder"/>")
            }
        });

        $('#cash').click(function () {
            if ($(this).is(':checked')) {
                $('#creditCart').prop("checked", false)
                $('#creditCartPayment').hide(100);
                $("#confirmOrder").prop('value', "<fmt:message key="label.CreateOrder"/>")
            } else {
                $('#creditCart').prop("checked", true)
                $('#creditCartPayment').show(100);
                $("#confirmOrder").prop('value', "<fmt:message key="label.Pay"/>")
            }
        });
    </script>
</main>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.1/jquery.validate.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.1/additional-methods.min.js"></script>
</body>
</html>