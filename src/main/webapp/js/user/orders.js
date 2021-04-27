const ELEMENTS_NUMBER_PER_LINE = 10

showOrders = function () {
    $.ajax({
        type: "GET",
        url: "http://localhost:8189/api/v1/orders",
        headers: {
            "Authorization": "Bearer " + localStorage.token
        },
        data: {
            p: localStorage.pageIndx,
            first_date: $("#firstDate").val() ? $("#firstDate").val() : null,
            second_date: $("#secondDate").val() ? $("#secondDate").val() : null,
        },
        success: function (response) {
            console.log(response)
            let elementsNumber = ELEMENTS_NUMBER_PER_LINE;
            let order = response;
            let orderState;
            $('#cartHead').empty();
            $('#cartHead').append(
                "                    <tr>" +
                "                        <td>Order number</td>" +
                "                        <td align='center' >Order price</td>" +
                "                        <td align='center'>Delivery address</td>" +
                "                        <td align='center' >Payment method</td>" +
                "                        <td align='center' >Payment state</td>" +
                "                        <td align='center' >Order date</td>" +
                "                    </tr>");
            let count = 0;
            $('#cartHeader').empty();
            $('#cartHeader').append("Orders list");



            while (count < order.length) {
                $('#example').empty();
                $('#currentPage').empty();

                if (order.length > 0) {
                    for (let k = 0; k < order.length; k++) {
                        count++;
                        switch (order[k].orderState) {
                            case "AWAITING_PAYMENT":
                                orderState = "not paid";
                                break;
                            case "AWAITING_SHIPMENT":
                                orderState = "awaiting shipment"
                                break;
                            case "SHIPPED":
                                orderState = "shipped"
                                break;
                            case "DELIVERED":
                                orderState = "delivered"
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
                            "font-weight: bold \" >" +
                            "<input type='submit' class='btn btn-info'" +
                            " onclick= \"goToOrder(" + "'" + order[k].orderId + "'" + ")\" " +
                            " value='" + order[k].orderId.substring(0, 8) + "' />" +
                            "<td align='center' class=\"justify-content-md-center\" >"
                            + order[k].totalPrice + ' $' + " </td>" +
                            "<td align='center' '> " + order[k].address + "</td>" +
                            "<td align='center' '> " + order[k].paymentMethod + "</td>" +
                            "<td align='center' '> " + orderState + "</td>" +
                            "<td align='center' '> " + order[k].creationDateTime.substring(0, 10) + '</td>');
                        $('#example').append(rd);
                    }
                    $("#nextPage").attr('disabled', false);
                }
                count++;
            }
                if (order.length > 0) {
                    $('#currentPage').append("<span class=\"page-link\">" + localStorage.pageIndx + "</span>");
                }
                if (order.length === 0 && localStorage.pageIndx > 1) {
                    $("#nextPage").attr('disabled', true);

                    let pageIndx = Number(localStorage.getItem("pageIndx"));
                    localStorage.setItem("pageIndx", --pageIndx);
                }
                else  if (order.length === 0){
                    $('#cartHeader').empty();
                    $('#example').empty();
                    $('#cartHead').empty();
                    $('#cartHeader').append("Cart is empty");
                }


        }
    });
}


goToOrder = function (orderId) {
    localStorage.orderUuid = orderId;
    console.log(localStorage.orderUuid)
    location.assign("http://localhost:8189/orders-result")
}

$(document).ready(function () {
    if (!localStorage.pageIndx) {
        localStorage.setItem("pageIndx", 1);
    }
    $("#prePage").click(function (event) {
        if (localStorage.pageIndx < 2) {
            $(this).attr('disabled', true);
        } else {
            $(this).attr('disabled', false);
            const pageIndx = Number(localStorage.getItem("pageIndx"));
            localStorage.setItem("pageIndx", String(pageIndx - 1));
            event.preventDefault();
            showOrders();
        }
    });

    $("#filterButton").click(function (event) {
        localStorage.setItem("pageIndx", 1);
        event.preventDefault();
        showOrders()
    });

    $("#nextPage").click(function (event) {
        let pageIndx = Number(localStorage.getItem("pageIndx"));
        localStorage.setItem("pageIndx", ++pageIndx);
        event.preventDefault();
        showOrders();
    });
});