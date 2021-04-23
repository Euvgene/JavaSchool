const ELEMENTS_NUMBER_PER_LINE = 4
let productList = null;
function getProducts() {
    $.ajax({
        type: "GET",
        url: 'http://localhost:8189/api/v1/products',
        data: {
            p: localStorage.pageIndx,
            min_price: $("#filterMinCost").val() ? $("#filterMinCost").val() : null,
            max_price: $("#filterMaxCost").val() ? $("#filterMaxCost").val() : null,
            product_title: $("#filterTitle").val() ? $("#filterTitle").val() : null,
            gender: $("#filterGender").val() ? $("#filterGender").val() : null,
        },
        success: function (result) {
             productList = result;
            let elementsNumber = ELEMENTS_NUMBER_PER_LINE;
            let count = 0;
            while (count < productList.length) {
                $('#example>tbody').empty();
                $('#currentPage').empty(); // TODO rename
                let rd = $('<tr class=""></tr>');
                if (productList.length > 0) {
                    for (let k = 0; k < productList.length; k++) {
                        console.log(productList[k].productId);
                        count++;
                        if (count > elementsNumber) {
                            elementsNumber = elementsNumber + 4;
                            rd = $('<tr class=""></tr>');
                        }
                        rd.append('<td>' +
                            "<p ><img src=\"/images/" + productList[k].fotoId + "\" + width=\"150\" height=\"150\"></p>" +
                            "<p> Name: " + productList[k].name + "</p>" +
                            "<p> Gender: " + productList[k].gender + "</p>" +
                            "<p> Age:  " + productList[k].age + "</p>" +
                            "<p> Lifespan:  " + productList[k].lifeSpan + "</p>" +
                            "<p> Price:  " + productList[k].productPrice + "</p>" +
                            "<input type='submit' onclick= \"addToCart(" + productList[k].productId + ")\"  value='Add to cart' />" +
                            '</td>');

                        $('#example').append(rd);
                    }
                    $("#nextPage").attr('disabled', false);
                }
                count++;
            }
            if (productList.length > 0) {
                $('#currentPage').append("<span class=\"page-link\">" + localStorage.pageIndx + "</span>");
            }

            if (productList.length === 0 && localStorage.pageIndx > 1) {
                $("#nextPage").attr('disabled', true);

                let pageIndx = Number(localStorage.getItem("pageIndx"));
                localStorage.setItem("pageIndx", --pageIndx);
            }
            console.log(result);

        }
    });
}

addToCart = function (id) {
    console.log(localStorage.marketCartUuid)
    $.ajax({
        type: "POST",
        url: "http://localhost:8189/api/v1/cart/add",
        data: {
            uuid: localStorage.marketCartUuid,
            prod_id: id
        },
        success: function () {
        }
    });
}


$(document).ready(function () {
    if (!localStorage.pageIndx) {
        localStorage.setItem("pageIndx", 1);
    }
    // GET REQUEST
    $("#prePage").click(function (event) {
        if (localStorage.pageIndx < 2) {
            $(this).attr('disabled', true);
        } else {
            $(this).attr('disabled', false);
            const pageIndx = Number(localStorage.getItem("pageIndx"));
            localStorage.setItem("pageIndx", String(pageIndx - 1));
            event.preventDefault();
            getProducts();
        }
    });

    $("#filterButton").click(function (event) {
        localStorage.setItem("pageIndx", 1);
        event.preventDefault();
        getProducts()
    });

    $("#nextPage").click(function (event) {
        let pageIndx = Number(localStorage.getItem("pageIndx"));
        localStorage.setItem("pageIndx", ++pageIndx);
        event.preventDefault();
        getProducts();
    });

    $("#addButton").click(function (event) {
        addToCart(1)
    });
});
