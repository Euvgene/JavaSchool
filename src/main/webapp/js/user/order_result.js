showOrder = function () {
    $.ajax({
        type: "GET",
        url: "http://localhost:8189/api/v1/orders/" + localStorage.orderUuid,
        success: function (response) {
            delete  localStorage.orderUuid;
            console.log(response)
            let order = response;
            let orderState;
            switch (order.orderState) {
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

            $('#cartHead').append(
                "                    <tr>" +
                "                        <td>Order number</td>" +
                "                        <td align='center' >Order price</td>" +
                "                        <td align='center'>Delivery address</td>" +
                "                        <td align='center' >Payment method</td>" +
                "                        <td align='center' >Payment state</td>" +
                "                    </tr>");


            let rd = $('<tr class="" style="height: 55px; vertical-align: 30; align-items: center"></tr>');

            rd.append(
                "<td style=\"justify-content:center; margin: auto;font-family:'Lucida Sans', " +
                "'Lucida Sans Regular', 'Lucida Grande', 'Lucida Sans Unicode', Geneva, Verdana, sans-serif;" +
                "font-weight: bold \" >"
                + order.orderId.substring(0,8) + "</td>" +
                "<td align='center' class=\"justify-content-md-center\" >"
                + order.totalPrice + ' $' +" </td>" +
                "<td align='center' '> " + order.address + "</td>" +
                "<td align='center' '> " + order.paymentMethod + "</td>" +
                "<td align='center' '> " + orderState + "</td>");

            $('#example').append(rd);
            $('#cartHeader').append("Order created");

            const delay = 10000; /* 1 миллисекунда*/
            setTimeout(function(){ location.assign( "http://localhost:8189/user-products")} , delay);
        }
    });
};