
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



