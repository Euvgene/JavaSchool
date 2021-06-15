let currentPage = null;
const ALL_PRODUCT = 0
const AVAILABLE_PRODUCT = 1

deleteProduct = function (id) {
    $.ajax({
        type: "GET",
        url: 'http://localhost:8189/api/v1/products/delete',
        data: {
            product_id: id,
        },
        success: function (result) {
            $("#buttonDelete" + id).hide(100);
            $("#quantity" + id).text("Quantity: 0");
            $("#quantity" + id).css("color", "red");
        }
    })
}

toCheck = function () {
    if ($('#notAvailable').is(':checked')) {
        $('#available').prop("checked", false)
    }
}

function clearTable() {
    $('#example').empty();
    $("#pagination").empty()
}


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
                    "                    <button class=\"page-link\" tabindex=\"-1\" id='prePage' onclick=' getProducts(currentPage - 1)' >Previous</button>\n" +
                    "                </li>")
                for (let k = 0; k < PaginationArray.length; k++) {
                    let classOfLi = (currentPage === PaginationArray[k]) ? "page-item active" : "page-item"
                    $("#pagination").append("<li class=\"" + classOfLi + "\"><a class=\"page-link\" onclick=\"getProducts(" + PaginationArray[k] + ")\" id=''>" + PaginationArray[k] + "</a></li>")
                }
                $("#pagination").append("  <li class=\"page-item\">" +
                    "                    <button class=\"page-link\" id='nextPage' onclick=' getProducts(currentPage + 1)'>Next</button  >" +
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





changeProduct = function (id) {
    $.ajax({
        type: "GET",
        url: 'http://localhost:8189/add-products',
        headers: {
            "Authorization": "Bearer " + localStorage.token
        }, success: function (response) {
            localStorage.productId = id;
            location.assign("http://localhost:8189/add-products")
        }
    })
}

$(document).ready(function () {
    delete localStorage.productId;
    getProducts()

    $("#notAvailableDiv").append("<div class=\"form-check\">\n" +
        "            <input id=\"notAvailable\" type=\"checkbox\" class=\"form-check-input\" onclick='toCheck()'>\n" +
        "            <label class=\"form-check-label\" for=\"notAvailable\" style=\"margin-left: 6px\">Not available</label>\n" +
        "        </div>")

    $('#available').click(function () {
        if ($(this).is(':checked')) {
            $('#notAvailable').prop("checked", false)
        }
    });

    $("#filterButton").click(function (event) {
        localStorage.setItem("pageIndx", 1);
        event.preventDefault();
        getProducts()
    });

    $("#addButton").click(function (event) {
        addToCart(1)
    });

});

