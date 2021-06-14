<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@ page contentType="text/html;charset=utf-8" %>
<!doctype html>
<html>

<header>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <script type="text/javascript" src="/js/common/small_cart.js"></script>
    <link rel="stylesheet" type="text/css" href="/css/file.css">
</header>
<head>
    <title></title>
</head>
<body>
<div class="smallCartDiv"  >
    <h6  id="smallCartHeader" align="center"></h6>
    <div id="smallCart">
    </div>
</div>
<script type="text/javascript">
    let smallCartList = null

    function getSmallCartProducts() {

        $.ajax({
            type: "GET",
            url: 'http://localhost:8189/api/v1/cart/' + localStorage.marketCartUuid,
            success: function (result) {
                smallCartList = result.items;
                $('#smallCartHeader').empty()
                $('#smallCartHeader').append("<svg width=\"3em\" height=\"3em\" viewBox=\"0 0 16 16\" class=\"bi bi-cart3\" fill=\"currentColor\" xmlns=\"http://www.w3.org/2000/svg\">\n" +
                    "  <path fill-rule=\"evenodd\" d=\"M0 1.5A.5.5 0 0 1 .5 1H2a.5.5 0 0 1 .485.379L2.89 3H14.5a.5.5 0 0 1 .49.598l-1 5a.5.5 0 0 1-.465.401l-9.397.472L4.415 11H13a.5.5 0 0 1 0 1H4a.5.5 0 0 1-.491-.408L2.01 3.607 1.61 2H.5a.5.5 0 0 1-.5-.5zM3.102 4l.84 4.479 9.144-.459L13.89 4H3.102zM5 12a2 2 0 1 0 0 4 2 2 0 0 0 0-4zm7 0a2 2 0 1 0 0 4 2 2 0 0 0 0-4zm-7 1a1 1 0 1 0 0 2 1 1 0 0 0 0-2zm7 0a1 1 0 1 0 0 2 1 1 0 0 0 0-2z\"/>\n" +
                    "</svg>");
                console.log(smallCartList)
                $('#smallCart').empty()

                if (smallCartList.length > 0) {
                    for (let k = 0; k < smallCartList.length; k++) {
                        let rd = $('<div></div>');

                        rd.append("<p class=\"page-information\" align='center'><img src=\"/images/" + smallCartList[k].fotoId + "\" + style=\"max-width: 50%;height: auto;  \"></p>" +
                            "<p  align='center'>" + smallCartList[k].quantity + " <fmt:message key="label.pc"/>: " + smallCartList[k].price + " $</p>");

                        $('#smallCart').append(rd);
                    }
                    $('#smallCartHeader').append(
                        "<p><fmt:message key="label.Total"/>: " + result.totalPrice + ' $' + "</p>");
                } else {
                    $('#smallCart').append("<p align='center'><fmt:message key="label.EmptyCart"/></p>");
                }
            }
        });

    }

</script>

</body>
</html>
