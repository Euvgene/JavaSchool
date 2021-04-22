let cartList = null;

function getCartProducts() {
    $.ajax({
        type: "GET",
        url: 'http://localhost:8189/api/v1/cart/' + localStorage.marketCartUuid,

        success: function (result) {
            cartList = result.items;
            let count = 0;
            while (count < cartList.length) {
                $('#example>tbody').empty();
                $('#currentPage').empty(); // TODO rename
                console.log(cartList);
                if (cartList.length > 0) {
                    for (let k = 0; k < cartList.length; k++) {

                        let rd = $('<tr class=""></tr>');
                        count++;
                        rd.append(
                            "<td style=\'justify-content:center; size:  \'> Title:</td>" +
                            "<td class=\"row justify-content-md-center\" > <div class=\"col-6 col-md-4\" style=\'text-align: right\'><button type=\"button\" class=\"btn btn-outline-danger\">" +
                            "<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"16\" height=\"16\" fill=\"currentColor\" class=\"bi bi-bag-dash-fill\" viewBox=\"0 0 16 16\">" +
                            "<path fill-rule=\"evenodd\" d=\"M10.5 3.5a2.5 2.5 0 0 0-5 0V4h5v-.5zm1 0V4H15v10a2 2 0 0 1-2 2H3a2 2 0 0 1-2-2V4h3.5v-.5a3.5 3.5 0 1 1 7 0zM6 9.5a.5.5 0 0 0 0 1h4a.5.5 0 0 0 0-1H6z\"></path>" +
                            "</svg>" +
                            "</button> </div>" +
                            "<div class=\"col-6 col-md-4\" style=\'text-align: center\'>" +cartList[k].productTitle+ "</div>"+
                            " <div class=\"col-6 col-md-4\" style=\'text-align: left\'><button type=\"button\" class=\"btn btn-primary\">" +
                            "<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"16\" height=\"16\" fill=\"currentColor\"" +
                            "class=\"bi bi-bag-plus-fill\" viewBox=\"0 0 16 16\" >" +
                            " <path fill-rule=\"evenodd\"" +
                            " d=\"M10.5 3.5a2.5 2.5 0 0 0-5 0V4h5v-.5zm1 0V4H15v10a2 2 0 0 1-2 2H3a2 2 0 0 1-2-2V4h3.5v-.5a3.5 3.5 0 1 1 7 0zM8.5 8a.5.5 0 0 0-1 0v1.5H6a.5.5 0 0 0 0 1h1.5V12a.5.5 0 0 0 1 0v-1.5H10a.5.5 0 0 0 0-1H8.5V8z\"></path>" +
                            "     </svg>" +
                            "</button></div> </td>" +
                            "<td  style=\'justify-content: center\'> Age:  </td>" +
                            "<td  style=\'justify-content: center\'><input type='submit'  value='Add to cart' /></td>");

                        $('#example').append(rd);
                    }
                    $('#example').append(
                        "<td>All</td>" +
                        "<td></td>" +
                        "<td></td>");
                }
            }
            console.log(result);

        }
    });
}


$(document).ready(function () {

});
