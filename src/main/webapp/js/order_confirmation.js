function getOrderProducts() {
    $.ajax({
        type: "GET",
        url: 'http://localhost:8189/api/v1/cart/' + localStorage.marketCartUuid,

        success: function (result) {
            cartList = result.items;
            let count = 0;

            while (count < cartList.length) {
                $('#cartHead').append(
                    "                    <tr>" +
                    "                        <td>Title</td>" +
                    "                        <td >Quantity</td>" +
                    "                        <td >Price</td>" +
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
                            "<td class=\"justify-content-md-center\" >" +
                            "<div class=\"col-6 col-md-4\" style=\"text-align: center\">" + cartList[k].quantity + "</div>" +
                            " </td>" +
                            "<td '> " + cartList[k].price + ' $' + "</td>");

                        $('#example').append(rd);
                    }


                    $('#example').append("<tr>" +
                        "<td style=\"justify-content:center; margin: auto;font-family:'Lucida Sans', " +
                        "'Lucida Sans Regular', 'Lucida Grande', 'Lucida Sans Unicode', Geneva, Verdana, sans-serif;" +
                        "font-weight: bold \">Total price</td>" +
                        "<td></td>" +
                        "<td>" + result.totalPrice + ' $' + "</td>" +
                        "</tr>");
                    $('#cartHeader').append("Order");
                }
            }
        }
    });
}