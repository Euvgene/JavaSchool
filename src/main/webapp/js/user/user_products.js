const ELEMENTS_NUMBER_PER_LINE = 4
const DECREMENT_PRODUCT = 1
let productList = null;
let products = null
let index = null;

function showButton(role, count, k) {

    if (role === "[ROLE_USER]" && count > 0) {
        return "<input id='cartButton" + productList[k].productId + "' class=\"btn btn-primary\" type='submit' onclick= \"addToCart(" + productList[k].productId + "," + count + ")\"  value='Add to cart'/> " +
            "</div>"
    } else if (count > 0) {
        return "<input id='cartButton" + productList[k].productId + "' class=\"btn btn-primary\" type='submit' onclick= \"addToCart(" + productList[k].productId + "," + count + ")\"  value='Add to cart'/>" +
            "</div>"
    } else {
        return "<input class=\"btn btn-danger\" type='submit'  value='Not available' disabled style='color: black'/>" +
            "</div>"
    }
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
        }, headers: {
            "Authorization": "Bearer " + localStorage.token
        },
        success: function (result) {
            productList = result;
            console.log(result)
            let elementsNumber = ELEMENTS_NUMBER_PER_LINE;
            let count = 0;
            while (count < productList.length) {

                $('#example').empty();
                $('#currentPage').empty();
                let rd = $('<div ></div>');
                if (productList.length > 0) {
                    $('#pagination').show(200);
                    for (let k = 0; k < productList.length; k++) {
                        if (smallCartList != null) {
                            console.log(productList[k].productId)
                            index = smallCartList.findIndex(i => i.productId === productList[k].productId)
                            if (index >= 0)
                                productList[k].productQuantity = productList[k].productQuantity - smallCartList[index].quantity
                        }


                        count++;
                        let button = showButton(localStorage.role, productList[k].productQuantity, k);
                        if (count > elementsNumber) {
                            elementsNumber = elementsNumber + 4;
                            rd = $('<div ></div>');
                        }
                        rd.append('<div class = "block">' +
                            "<p class=\"page-information\"><img id=\"photoId" + productList[k].productId + "\" src=\"/images/" + productList[k].fotoId + "\" + width=\"150\" height=\"150\"></p>" +
                            "<p class=\"page-information\"> Name: " + productList[k].productTitle + "</p>" +
                            "<p class=\"page-information\"> Gender: " + productList[k].parameters.productGender + "</p>" +
                            "<p class=\"page-information\"> Age:  " + productList[k].parameters.productAge + "</p>" +
                            "<p class=\"page-information\"> Lifespan:  " + productList[k].parameters.productLifespan + "</p>" +
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
            } else  if (productList.length === 0){
                $('#example').empty();
                $('#pagination').hide(200);
                $('#example').append("  <h3 id=\"cartHeader\" style=\"margin-top: 20px;\">Products not found</h3>")
            }
        }
    });
}

function decrementCount(id) {

    products.set(id, products.get(id) - DECREMENT_PRODUCT)
    console.log(products)

    if (products.get(id) === 0) {
        let element = document.getElementById("cartButton" + id);
        element.style.color = "black";
        element.setAttribute("value", "Not available")
        element.setAttribute("class", "btn btn-danger")
        element.setAttribute("disabled", "true")
    }
}

addToCart = function (id, count) {
    $.ajax({
        type: "POST",
        url: "http://localhost:8189/api/v1/cart/add",
        data: {
            uuid: localStorage.marketCartUuid,
            prod_id: id
        },
        success: function () {
            if (products.has(id)) {
                decrementCount(id)
                getSmallCartProducts();
            } else {
                products.set(id, count)
                decrementCount(id)
                getSmallCartProducts();
            }

        }
    });
}

$(document).ready(function () {
    getSmallCartProducts()
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

