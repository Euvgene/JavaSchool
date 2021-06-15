<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!doctype html>
<html lang="en-GB">
<header>
    <script type="text/javascript" src="/js/user/order_result.js"></script>
</header>
<head>
    <title>New order</title>
    <jsp:include page="userHeader.jsp"/>

</head>
<script type="text/javascript">
    showOrder = function () {
        $.ajax({
            type: "GET",
            url: "http://localhost:8189/api/v1/orders/result",
            data: {
                uuid: localStorage.orderUuid,
            },
            success: function (response) {

                let order = response.items;
                let count = 0;
                while (count < order.length) {
                    $('#cartHead').append(
                        "<tr>" +
                        "<td><fmt:message key="label.name"/></td>" +
                        "<td ><fmt:message key="label.Quantity"/></td>" +
                        "<td ><fmt:message key="label.price"/></td>" +
                        "</tr>");

                    console.log(order);
                    if (order.length > 0) {
                        for (let k = 0; k < order.length; k++) {

                            let rd = $('<tr class="" style="height: 55px; vertical-align: 30;"></tr>');
                            count++;
                            rd.append(
                                "<td style=\"justify-content:center; margin: auto;font-family:'Lucida Sans', " +
                                "'Lucida Sans Regular', 'Lucida Grande', 'Lucida Sans Unicode', Geneva, Verdana, sans-serif;" +
                                "font-weight: bold \">"
                                + order[k].productTitle + "</td>" +
                                "<td class=\"justify-content-md-center\" >" +
                                "<div class=\"col-6 col-md-4\" style=\"text-align: center\">" + order[k].quantity + "</div>" +
                                " </td>" +
                                "<td '> " + order[k].price + ' $' + "</td>");

                            $('#example').append(rd);
                        }

                        $('#example').append("<tr>" +
                            "<td style=\"justify-content:center; margin: auto;font-family:'Lucida Sans', " +
                            "'Lucida Sans Regular', 'Lucida Grande', 'Lucida Sans Unicode', Geneva, Verdana, sans-serif;" +
                            "font-weight: bold \"><fmt:message key="label.Total"/></td>" +
                            "<td></td>" +
                            "<td>" + response.totalPrice + ' $' + "</td>" +
                            "</tr>");
                        $('#cartHeader').append("<fmt:message key="label.Order"/> " + order[0].orderId.substring(0, 8));
                    }
                }
                const delay = 10000;
                setTimeout(function () {
                    location.assign("http://localhost:8189/user-products")
                    delete localStorage.orderUuid;
                }, delay);
            }
        });
    };
</script>
<body class="d-flex flex-column h-100" onload="showOrder()">
<main class="flex-shrink-0">

    <div class="col-md-6" style="margin: auto">
        <h3 id="cartHeader" style="margin-top: 100px;"></h3>

        <div>
            <table  class="table table-success table-striped" cellpadding="0" cellspacing="0"
                    style="border-collapse: separate;" aria-describedby="">
                <tr style="display: none">
                    <th id=""></th>
                </tr>
                <thead id="cartHead" style="font-size: 1.3rem;"></thead>
                <tbody id="example"></tbody>
            </table>
        </div>
        <div id="cartButtons"   >

        </div>
    </div>

</main>

</body>
</html>
