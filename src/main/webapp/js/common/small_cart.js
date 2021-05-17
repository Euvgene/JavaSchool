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
                        "<p  align='center'>" + smallCartList[k].quantity + " pc: " + smallCartList[k].price + " $</p>");

                    $('#smallCart').append(rd);
                }
                $('#smallCartHeader').append(
                    "<p>Total: " + result.totalPrice + ' $' + "</p>");
            } else {
                $('#smallCart').append("<p align='center'>Cart is empty</p>");
            }
        }
    });

}


clearCart = function () {
    $.ajax({
        type: "POST",
        url: "http://localhost:8189/api/v1/cart/clear",
        data: {
            uuid: localStorage.marketCartUuid
        },
        success: function () {
            getSmallCartProducts();
        }
    });
}

function incrementCount(id, count, newCount) {
    count = count + newCount
    products.set(id, count)
    localStorage.setItem('productCount', JSON.stringify(Array.from(products.entries())));
    console.log(localStorage.productCount)
}

deleteCartProductById = function (productId, i, count) {
    $.ajax({
        type: "POST",
        url: "http://localhost:8189/api/v1/cart/delete",
        data: {
            uuid: localStorage.marketCartUuid,
            product_id: productId,
            updateNumber: i

        },
        success: function () {
            incrementCount(productId, count, INCREMENT_COUNT)
            console.log(localStorage.productCount)
            getSmallCartProducts();
        }
    });
}

incrementProductInCart = function (id) {
    $.ajax({
        type: "POST",
        url: "http://localhost:8189/api/v1/cart/add",
        data: {
            uuid: localStorage.marketCartUuid,
            prod_id: id
        },
        success: function () {
            getSmallCartProducts();
        }
    });
}



