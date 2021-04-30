const ELEMENTS_NUMBER_PER_LINE = 4
let productList = null;

function showButton(role,k) {
    if (role === "[ROLE_USER]") {
       return  "<input class=\"button\" type='submit' onclick= \"addToCart(" + productList[k].productId + ")\"  value='Add to cart'/> " +
            "</div>"
    } else if (role === "[ROLE_ADMIN]") {
        return  "<input class=\"button\" type='submit' onclick= \"changeProduct(" + productList[k].productId + ")\"  value='Change product'/>" +
            "</div>"
    } else {
       return "<input class=\"button\" type='submit' onclick= \"addToCart(" + productList[k].productId + ")\"  value='Add to cart'/>" +
            "</div>"
    }
}

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
                $('#example').empty();
                $('#currentPage').empty(); // TODO rename
                let rd = $('<div ></div>');
                if (productList.length > 0) {
                    for (let k = 0; k < productList.length; k++) {
                        count++;
                        let button = showButton(localStorage.role,k);
                        if (count > elementsNumber) {
                            elementsNumber = elementsNumber + 4;
                            rd = $('<div ></div>');
                        }
                        rd.append('<div class = "block">' +
                            "<p class=\"page-information\"><img src=\"/images/" + productList[k].fotoId + "\" + width=\"150\" height=\"150\"></p>" +
                            "<p class=\"page-information\"> Name: " + productList[k].name + "</p>" +
                            "<p class=\"page-information\"> Gender: " + productList[k].gender + "</p>" +
                            "<p class=\"page-information\"> Age:  " + productList[k].age + "</p>" +
                            "<p class=\"page-information\"> Lifespan:  " + productList[k].lifeSpan + "</p>" +
                            "<p class=\"page-information\"> Price:  " + productList[k].productPrice + "</p>" +
                            button);


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

changeProduct = function (id) {
    $.ajax({
        type: "GET",
        url: 'http://localhost:8189/addproducts',
        headers: {
            "Authorization": "Bearer " + localStorage.token
        },success: function (response) {
            localStorage.productId = id;
            location.assign("http://localhost:8189/addproducts")
        }})

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
