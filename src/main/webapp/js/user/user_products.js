const DECREMENT_PRODUCT = 1
const ALL_PRODUCT = 0
const AVAILABLE_PRODUCT = 1
let productList = null;
let products = null
let index = null;
let currentPage = null;


function generatePagesIndexes(startPage, endPage) {
    let arr = [];
    for (let i = startPage; i < endPage + 1; i++) {
        arr.push(i);
    }
    return arr;
}

function getProductCount(formData) {
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: 'http://localhost:8189/api/v1/products/get-page-count',
        data: JSON.stringify(formData),
        dataType: 'json',
        success: function (response) {
            productCount = response;
            $("#pagination").empty()
            if (productCount > 8) {
                let totalPages = productCount / 8
                let minPageIndex = (currentPage >= totalPages) ? currentPage - 2 : currentPage - 1;
                if (minPageIndex < 1) {
                    minPageIndex = 1;
                }

                let maxPageIndex = (currentPage === 1) ? currentPage + 2 : currentPage + 1;
                if (maxPageIndex > totalPages) {
                    maxPageIndex = totalPages;
                }
                let PaginationArray = generatePagesIndexes(minPageIndex, maxPageIndex)
                $("#pagination").append("<li class=\"page-item\" >\n" +
                    "                    <button class=\"page-link\" tabindex=\"-1\" id='prePage' onclick=' getProducts(currentPage - 1)' ><<</button>\n" +
                    "                </li>")
                for (let k = 0; k < PaginationArray.length; k++) {
                    let classOfLi = (currentPage === PaginationArray[k]) ? "page-item active" : "page-item"
                    $("#pagination").append("<li class=\"" + classOfLi + "\"><a class=\"page-link\" onclick=\"getProducts(" + PaginationArray[k] + ")\" id=''>" + PaginationArray[k] + "</a></li>")
                }
                $("#pagination").append("  <li class=\"page-item\">" +
                    "                    <button class=\"page-link\" id='nextPage' onclick=' getProducts(currentPage + 1)'>>></button  >" +
                    "                </li>")
                if (currentPage === 1) {
                    $("#prePage").prop('disabled', true)
                } else $("#prePage").prop('disabled', false)
                if (currentPage >= totalPages) {
                    $("#nextPage").prop('disabled', true)
                } else $("#nextPage").prop('disabled', false)
            }
        }
    })
}


function clearTable() {
    $('#example').empty();
    $("#pagination").empty()
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

    $("#filterButton").click(function (event) {
        localStorage.setItem("pageIndx", 1);
        event.preventDefault();
        getProducts()
    });

    $("#addButton").click(function (event) {
        addToCart(1)
    });
});

