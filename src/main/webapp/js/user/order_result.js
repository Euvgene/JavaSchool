showOrder = function () {
    $.ajax({
        type: "GET",
        url: "http://localhost:8189/api/v1/orders/" + localStorage.orderUuid,
        success: function (response) {
            delete localStorage.orderUuid;
            let order = response.items;
            let count = 0;
            while (count < order.length) {
                $('#cartHead').append(
                    "<tr>" +
                    "<td>Title</td>" +
                    "<td >Quantity</td>" +
                    "<td >Price</td>" +
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
                        "font-weight: bold \">Total price</td>" +
                        "<td></td>" +
                        "<td>" + response.totalPrice + ' $' + "</td>" +
                        "</tr>");
                    $('#cartHeader').append("Order " + order[0].orderId.substring(0, 8));
                }
            }
            const delay = 10000;
            setTimeout(function () {
                location.assign("http://localhost:8189/user-products")
            }, delay);
        }
    });
};