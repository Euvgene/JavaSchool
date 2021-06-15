<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!doctype html>
<html lang="en-GB">
<header>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <script type="text/javascript" src="/js/user/orders.js"></script>
</header>
<head>
    <title>Orders</title>
    <jsp:include page="userHeader.jsp"/>

</head>
<body class="d-flex flex-column h-100" onload="showOrders()">
<main class="flex-shrink-0">

    <div class="col-md-6" style="margin: auto">
        <div class="form-group row" style="margin-top: 100px;">
            <div class="form-group col-md-3">
                <label for="firstDate"><fmt:message key="label.FromDate" /></label>
                <input class="form-control" type="date" id="firstDate">
            </div>
            <div class="form-group col-md-3">
                <label for="secondDate"><fmt:message key="label.ToDate" /></label>
                <input class="form-control" type="date" id="secondDate" >
            </div>
        </div>

        <h3 id="cartHeader" style="margin-top: 50px;"></h3>
        <nav aria-label="Page navigation">
            <ul class="pagination" id="pagination">
            </ul>
        </nav>

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
        <script type="text/javascript">

            showOrders = function (pageIndex = 1) {
                $.ajax({
                    type: "GET",
                    url: "http://localhost:8189/api/v1/orders",
                    headers: {
                        "Authorization": "Bearer " + localStorage.token
                    },
                    data: {
                        page: pageIndex,
                        first_date: $("#firstDate").val() ? $("#firstDate").val() : null,
                        second_date: $("#secondDate").val() ? $("#secondDate").val() : null,
                    },
                    success: function (response) {
                        let paymentMethod;
                        let delivery;
                        currentPage = pageIndex
                        let order = response;
                        let orderState;
                        if (order.length > 0) {
                            clearTable();
                            $('#cartHead').append(
                                "<tr>" +
                                "<td align='center' style='white-space: nowrap'><fmt:message key="label.OrderNumber" /></td>" +
                                "<td align='center' style='white-space: nowrap'><fmt:message key="label.OrderPrice" /></td>" +
                                "<td align='center' style='white-space: nowrap'><fmt:message key="label.DeliveryAddress" /></td>" +
                                "<td align='center' style='white-space: nowrap'><fmt:message key="label.PaymentMethod" /></td>" +
                                "<td align='center' style='white-space: nowrap'><fmt:message key="label.State" /></td>" +
                                "<td align='center' style='white-space: nowrap'><fmt:message key="label.OrderDate" /></td>" +
                                "</tr>");

                            $('#cartHeader').append("<fmt:message key="label.OrderList" />");
                            $('#currentPage').empty();

                            for (let k = 0; k < order.length; k++) {
                                switch (order[k].orderState) {
                                    case "AWAITING_PAYMENT":
                                        orderState = "<fmt:message key="label.AwaitingShipment" />";
                                        break;
                                    case "AWAITING_SHIPMENT":
                                        orderState = "<fmt:message key="label.AwaitingShipment" />"
                                        break;
                                    case "SHIPPED":
                                        orderState = "<fmt:message key="label.Shipped" />"
                                        break;
                                    case "DELIVERED":
                                        orderState = "<fmt:message key="label.Delivered" />"
                                        break;
                                    case "RETURN":
                                        orderState = "<fmt:message key="label.Return" />"
                                        break;
                                }
                                let rd = $('<tr class=""></tr>');
                                paymentMethod =( order[k].paymentMethod === "cash")?"<fmt:message key="label.Cash" />" : "<fmt:message key="label.Card" />"
                                delivery = (order[k].address === "from store" )? "<fmt:message key="label.Delivery" />" : order[k].address;
                                rd.append(
                                    "<td style=\"justify-content:center; margin: auto;font-family:'Lucida Sans', " +
                                    "'Lucida Sans Regular', 'Lucida Grande', 'Lucida Sans Unicode', Geneva, Verdana, sans-serif;" +
                                    "font-weight: bold \" >" +
                                    "<input type='submit' class='btn btn-info'" +
                                    " onclick= \"goToOrder(" + "'" + order[k].orderId + "'" + ")\" " +
                                    " value='" + order[k].orderId.substring(0, 8) + "' />" +
                                    "<td align='center' class=\"justify-content-md-center\" >"
                                    + order[k].totalPrice + ' $' + " </td>" +
                                    "<td align='center' > " + delivery + "</td>" +
                                    "<td align='center' > " + paymentMethod + "</td>" +
                                    "<td align='center' > " + orderState + "</td>" +
                                    "<td align='center' > " + order[k].creationDateTime.substring(0, 10) + '</td>');
                                $('#example').append(rd);
                            }
                            getProductCount()
                        } else {
                            clearTable();
                            $('#cartHeader').append("<fmt:message key="label.OrderIsEmpty" />");
                        }
                    }
                });
            }

        </script>

    </div>
</main>

</body>
</html>
