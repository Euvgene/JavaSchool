const ELEMENTS_NUMBER_PER_LINE = 4



deleteProduct = function (id){
    $.ajax({
        type: "GET",
        url: 'http://localhost:8189/api/v1/products/delete',
        data: {
            product_id: id,
        },
        success: function (result) {
            console.log("ok")
        }
        })
}
function getProducts() {
    products = new Map();
    $.ajax({
        type: "GET",
        url: 'http://localhost:8189/api/v1/products',
        data: {
            page: localStorage.pageIndx,
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
                $('#currentPage').empty();
                let rd = $('<div ></div>');
                if (productList.length > 0) {
                    for (let k = 0; k < productList.length; k++) {
                        count++;
                        if (count > elementsNumber) {
                            elementsNumber = elementsNumber + 4;
                            rd = $('<div ></div>');
                        }
                        rd.append('<div class = "block">' +
                            "<p class=\"page-information\"><img id=\"photoId" + productList[k].productId + "\" src=\"/images/" + productList[k].fotoId + "\" + width=\"150\" height=\"150\"></p>" +
                            "<p class=\"page-information\"> Name: " + productList[k].name + "</p>" +
                            "<p class=\"page-information\"> Gender: " + productList[k].gender + "</p>" +
                            "<p class=\"page-information\"> Age:  " + productList[k].age + "</p>" +
                            "<p class=\"page-information\"> Lifespan:  " + productList[k].lifeSpan + "</p>" +
                            "<p class=\"page-information\"> Price:  " + productList[k].productPrice + "</p>" +
                            "<input class=\"btn btn-primary\" type='submit' onclick= \"changeProduct(" + productList[k].productId + ")\"  value='Change product'/>" +
                            "<input class=\"btn btn-danger\" type='submit' onclick= \"deleteProduct(" + productList[k].productId + ")\"  value='Delete product'/>" +
                            "</div>");


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


changeProduct = function (id) {
    $.ajax({
        type: "GET",
        url: 'http://localhost:8189/addproducts',
        headers: {
            "Authorization": "Bearer " + localStorage.token
        }, success: function (response) {
            localStorage.productId = id;
            location.assign("http://localhost:8189/addproducts")
        }
    })
}

$(document).ready(function () {
    getProducts()
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

