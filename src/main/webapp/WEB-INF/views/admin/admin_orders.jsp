<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!doctype html>
<html lang="en-GB">
<header>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>

    <script type="text/javascript" src="/js/common/order_validation.js"></script>
    <script type="text/javascript" src="/js/admin/admin_orders.js"></script>
    <link rel="stylesheet" type="text/css" href="/css/file.css">
</header>
<head>
    <title>Orders</title>
    <jsp:include page="adminHeader.jsp"/>

</head>
<body class="d-flex flex-column h-100">
<main class="flex-shrink-0" style="margin-top: 80px;">

    <div class="container mt-auto" style="margin-top: 100px;">
        <div class="form-group row">
            <div class="form-group col-md-3" style=" margin: auto" align="center">
                <label for="firstDate"><fmt:message key="label.FromDate"/></label>
                <input class="form-control" type="date" id="firstDate">
            </div>
            <div class="form-group col-md-3" style=" margin: auto" align="center">
                <label for="secondDate"><fmt:message key="label.ToDate"/></label>
                <input class="form-control" type="date" id="secondDate">
            </div>
            <div class="form-group col-md-3" style=" margin: auto" align="center">
                <label for="state"><fmt:message key="label.State"/></label>
                <select class="form-select" id="state" required="">
                    <option id="ALL" value="0"><fmt:message key="label.all"/></option>
                    <option id="Active" value="1"><fmt:message key="label.active"/></option>
                    <option id="Delivered" value="2"><fmt:message key="label.delivered"/></option>
                    <option id="Return" value="3"><fmt:message key="label.return"/></option>
                </select>
            </div>
        </div>


        <h3 id="cartHeader" style="margin-top: 50px;"></h3>
        <nav aria-label="Page navigation">
            <ul class="pagination" id="pagination">
            </ul>
        </nav>

        <div>
            <form id='changeOrderForm'>
                <table class="table table-success table-striped" cellpadding="0" cellspacing="0"
                       style="border-collapse: separate;" aria-describedby="">
                    <tr style="display: none">
                        <th id=""></th>
                    </tr>
                    <thead id="cartHead" style=" font-size: 1.3rem;"></thead>
                    <tbody id="example"></tbody>

                </table>
            </form>
        </div>

    </div>

    <script type="text/javascript">
        function getAllOrders(pageIndex = 1) {
            const e = document.getElementById("state");
            $.ajax({
                type: "GET",
                url: "http://localhost:8189/api/v1/orders/all",
                headers: {
                    "Authorization": "Bearer " + localStorage.token
                },
                data: {
                    page: pageIndex,
                    first_date: $("#firstDate").val() ? $("#firstDate").val() : null,
                    second_date: $("#secondDate").val() ? $("#secondDate").val() : null,
                    state: e.options[e.selectedIndex].id
                },
                success: function (response) {
                    let paymentMethod;
                    currentPage = pageIndex;
                    let order = response;
                    console.log(response)
                    if (order.length > 0) {
                        clearTable();
                        $('#cartHead').append(
                            "<tr>" +
                            "<td align='center' style='white-space: nowrap'><fmt:message key="label.OrderNumber"/></td>" +
                            "<td align='center' style='white-space: nowrap'><fmt:message key="label.OrderPrice"/></td>" +
                            "<td align='center' style='white-space: nowrap'><fmt:message key="label.DeliveryAddress"/></td>" +
                            "<td align='center' style='white-space: nowrap'><fmt:message key="label.PaymentMethod"/></td>" +
                            "<td align='center' style='white-space: nowrap'><fmt:message key="label.State"/></td>" +
                            "<td align='center' style='white-space: nowrap'><fmt:message key="label.OrderDate"/></td>" +
                            "</tr>");

                        $('#cartHeader').append("<fmt:message key="label.orders"/>");
                        $('#currentPage').empty();

                        $('#example').empty();

                        for (let k = 0; k < order.length; k++) {

                            switch (order[k].orderState) {
                                case "AWAITING_SHIPMENT":
                                    orderState = "<option value=\"AWAITING_SHIPMENT\"><fmt:message key="label.AwaitingShipment"/></option>" +
                                        "         <option value=\"SHIPPED\"><fmt:message key="label.Shipped"/></option>" +
                                        "         <option value=\"DELIVERED\"><fmt:message key="label.Delivered"/></option>" +
                                        "         <option value=\"RETURN\"><fmt:message key="label.Return"/></option>";
                                    break;
                                case "SHIPPED":
                                    orderState = "<option value=\"SHIPPED\"><fmt:message key="label.Shipped"/></option>" +
                                        "         <option value=\"DELIVERED\"><fmt:message key="label.Delivered"/></option>" +
                                        "         <option value=\"RETURN\"><fmt:message key="label.Return"/></option>";
                                    break;
                                case "DELIVERED":
                                    orderState = "<option value=\"DELIVERED\"><fmt:message key="label.Delivered"/></option>" +
                                        "         <option value=\"RETURN\"><fmt:message key="label.Return"/></option>";
                                    break;
                                case "RETURN":
                                    orderState = " <option value=\"RETURN\"><fmt:message key="label.Return"/></option>";
                                    break;
                            }
                            let rd = $('<tr class=""></tr>');

                            paymentMethod =( order[k].paymentMethod === "cash")?"<fmt:message key="label.Cash" />" : "<fmt:message key="label.Card" />"
                            rd.append(
                                "<td style=\"justify-content:center; margin: auto;font-family:'Lucida Sans', " +
                                "'Lucida Sans Regular', 'Lucida Grande', 'Lucida Sans Unicode', Geneva, Verdana, sans-serif;" +
                                "font-weight: bold \"  align='center' > " + order[k].orderId.substring(0, 8) + "" +
                                "<button type='button' class='btn btn-info' style='display: none' id='confirm" + order[k].orderId.substring(0, 8) + "'" +
                                " onclick= \"changeOrder('" + order[k].orderId.substring(0, 8) + "','" + order[k].orderId + "')\"><svg width=\"1em\" height=\"1em\" viewBox=\"0 0 16 16\" class=\"bi bi-check2\" fill=\"currentColor\" xmlns=\"http://www.w3.org/2000/svg\">\n" +
                                "  <path fill-rule=\"evenodd\" d=\"M13.854 3.646a.5.5 0 0 1 0 .708l-7 7a.5.5 0 0 1-.708 0l-3.5-3.5a.5.5 0 1 1 .708-.708L6.5 10.293l6.646-6.647a.5.5 0 0 1 .708 0z\"/>" +
                                "</svg></button></td>" +
                                "<td align='center' class=\"justify-content-md-center\" >"
                                + order[k].totalPrice + ' $' + " </td>" +
                                "<td>" +
                                " <input class=\"form-control\" type=\"text\"  value='" + order[k].address + "'  id='address" + order[k].orderId.substring(0, 8) + "'" +
                                "onchange=\"showConfirmButton('" + order[k].orderId.substring(0, 8) + "')\" name='address'></td>" +
                                "<td align='center' > " + paymentMethod + "</td>" +
                                "<td> " +
                                "                <select class=\"form-select\" id='state" + order[k].orderId.substring(0, 8) + "' required=\"\" onchange=\"showConfirmButton('" + order[k].orderId.substring(0, 8) + "')\">" +
                                orderState +
                                "                </select>" +
                                "</td>" +
                                "<td align='center' > " + order[k].creationDateTime.substring(0, 10) + '</td>');
                            $('#example').append(rd);
                        }
                        getOrderCount();
                    } else {
                        clearTable()
                        $('#cartHeader').append("<fmt:message key="label.OrderIsEmpty"/>");
                    }
                }
            })
        }

    </script>
</main>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.1/jquery.validate.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.1/additional-methods.min.js"></script>
</body>
</html>
