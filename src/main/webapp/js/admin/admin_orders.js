let orderState = null;
const ELEMENTS_NUMBER_PER_LINE = 10

showConfirmButton = function (id) {
    document.getElementById('confirm' + id).style.display = 'inline';
}

changeOrder = function (smallId, id) {
    const s = document.getElementById('state' + smallId);
    const address = document.getElementById('address' + smallId);

    console.log(address.value)
    $.ajax({
        type: "GET",
        url: "http://localhost:8189/api/v1/orders/update",
        headers: {
            "Authorization": "Bearer " + localStorage.token
        },
        data: {
            order_id: id,
            delivery_address: address.value,
            state: s.value
        },
        success: function () {
            getAllOrders()
        }
    })
}


function getAllOrders() {
    const e = document.getElementById("state");
    const strState = e.options[e.selectedIndex].text;
    $.ajax({
        type: "GET",
        url: "http://localhost:8189/api/v1/orders/all",
        headers: {
            "Authorization": "Bearer " + localStorage.token
        },
        data: {
            page: localStorage.orderPageIndx,
            first_date: $("#firstDate").val() ? $("#firstDate").val() : null,
            second_date: $("#secondDate").val() ? $("#secondDate").val() : null,
            state: strState === "Choose..." ? null : e.options[e.selectedIndex].text
        },
        success: function (response) {
            let elementsNumber = ELEMENTS_NUMBER_PER_LINE;
            let order = response;
            console.log(response)
            clearTable();
            $('#cartHead').append(
                "                    <tr>" +
                "                        <td align='center' style='white-space: nowrap'>Order number</td>" +
                "                        <td align='center' style='white-space: nowrap'>Order price</td>" +
                "                        <td align='center' style='white-space: nowrap'>Delivery address</td>" +
                "                        <td align='center' style='white-space: nowrap'>Payment method</td>" +
                "                        <td align='center' style='white-space: nowrap'>State</td>" +
                "                        <td align='center' style='white-space: nowrap'>Order date</td>" +
                "                    </tr>");
            let count = 0;
            $('#cartHeader').append("Orders");
            $('#pagination').append("  <ul class=\"pagination\">" +
                "                <li class=\"page-item\" id='prePage'>" +
                "                    <button class=\"page-link\" tabIndex=\"-1\" onclick=\"prePage()\">Previous</button>" +
                "                </li>\n" +
                "                <li class=\"page-item active\" aria-current=\"page\" id=\"currentPage\">" +
                "                </li>\n" +
                "                <li class=\"page-item\" id='nextPage'>" +
                "                    <button class=\"page-link\" onclick=\"nextPage()\">Next</button>" +
                "                </li>\n" +
                "            </ul>");
            $('#currentPage').empty();
            while (count < order.length) {
                $('#example').empty();
                if (order.length > 0) {
                    for (let k = 0; k < order.length; k++) {
                        count++;
                        switch (order[k].orderState) {
                            case "AWAITING_SHIPMENT":
                                orderState = "<option value=\"AWAITING_SHIPMENT\">awaiting shipment</option>" +
                                    "         <option value=\"SHIPPED\">shipped</option>" +
                                    "         <option value=\"DELIVERED\">delivered</option>" +
                                    "         <option value=\"RETURN\">return</option>";
                                break;
                            case "SHIPPED":
                                orderState = "<option value=\"SHIPPED\">shipped</option>" +
                                    "         <option value=\"DELIVERED\">delivered</option>" +
                                    "         <option value=\"RETURN\">return</option>";
                                break;
                            case "DELIVERED":
                                orderState = "<option value=\"DELIVERED\">delivered</option>" +
                                    "         <option value=\"RETURN\">return</option>";
                                break;
                            case "RETURN":
                                orderState = " <option value=\"RETURN\">return</option>";
                                break;
                        }
                        let rd = $('<tr class=""></tr>');

                        if (count > elementsNumber) {
                            elementsNumber = elementsNumber + 10;
                            rd = $('<tr class=""></tr>');
                        }

                        rd.append(
                            "<td style=\"justify-content:center; margin: auto;font-family:'Lucida Sans', " +
                            "'Lucida Sans Regular', 'Lucida Grande', 'Lucida Sans Unicode', Geneva, Verdana, sans-serif;" +
                            "font-weight: bold \"  align='center' '> " + order[k].orderId.substring(0, 8) + "" +
                            "<button class='btn btn-info' style='display: none' id='confirm" + order[k].orderId.substring(0, 8) + "'" +
                            " onclick= \"changeOrder('" + order[k].orderId.substring(0, 8) + "','" + order[k].orderId + "')\"><svg width=\"1em\" height=\"1em\" viewBox=\"0 0 16 16\" class=\"bi bi-check2\" fill=\"currentColor\" xmlns=\"http://www.w3.org/2000/svg\">\n" +
                            "  <path fill-rule=\"evenodd\" d=\"M13.854 3.646a.5.5 0 0 1 0 .708l-7 7a.5.5 0 0 1-.708 0l-3.5-3.5a.5.5 0 1 1 .708-.708L6.5 10.293l6.646-6.647a.5.5 0 0 1 .708 0z\"/>" +
                            "</svg></button></td>" +
                            "<td align='center' class=\"justify-content-md-center\" >"
                            + order[k].totalPrice + ' $' + " </td>" +
                            "<td>" +
                            " <input class=\"form-control\" type=\"text\"  value='" + order[k].address + "'  id='address" + order[k].orderId.substring(0, 8) + "'" +
                            "onchange=\"showConfirmButton('" + order[k].orderId.substring(0, 8) + "')\"></td>" +
                            "<td align='center' '> " + order[k].paymentMethod + "</td>" +
                            "<td> " +
                            "                <select class=\"form-select\" id='state" + order[k].orderId.substring(0, 8) + "' required=\"\" onchange=\"showConfirmButton('" + order[k].orderId.substring(0, 8) + "')\">" +
                            orderState +
                            "                </select>" +
                            "                <div class=\"invalid-feedback\">\n" +
                            "                    Please provide a valid state.\n" +
                            "                </div>" +

                            "</td>" +
                            "<td align='center' '> " + order[k].creationDateTime.substring(0, 10) + '</td>');
                        $('#example').append(rd);
                    }
                    $("#nextPage").attr('disabled', false);
                }
                count++;
            }
            if (order.length > 0) {
                $('#currentPage').append("<span class=\"page-link\">" + localStorage.orderPageIndx + "</span>");
            }
            if (order.length === 0 && localStorage.orderPageIndx > 1) {
                $("#nextPage").attr('disabled', true);
                let orderPageIndx = Number(localStorage.getItem("orderPageIndx"));
                localStorage.setItem("orderPageIndx", --orderPageIndx);
                $('#currentPage').append("<span class=\"page-link\">" + localStorage.orderPageIndx + "</span>");
            } else if (order.length === 0) {
                clearTable();
                $('#example').empty();
                $('#cartHeader').append("Orders is empty");
            }
        }
    })
}

prePage = function () {
    if (localStorage.orderPageIndx < 2) {
        $(this).attr('disabled', true);
    } else {
        $(this).attr('disabled', false);
        const orderPageIndx = Number(localStorage.getItem("orderPageIndx"));
        localStorage.setItem("orderPageIndx", String(orderPageIndx - 1));

        getAllOrders();
    }
};

nextPage = function () {
    let pageIndx = Number(localStorage.getItem("orderPageIndx"));
    localStorage.setItem("orderPageIndx", ++pageIndx);
    getAllOrders();
};

clearTable = function () {
    $('#cartHeader').empty();
    $('#pagination').empty();
    $('#cartHead').empty();
}
$(document).ready(function () {

    localStorage.setItem("orderPageIndx", 1);
    // GET REQUEST
    $("#filterButton").click(function (event) {
        getAllOrders()
    });


});
