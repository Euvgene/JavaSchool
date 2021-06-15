let currentPage = null;

function checkFirstDate() {
    if ($("#firstDate").val() !== "" && $("#secondDate").val() !== "") {
        if ($("#firstDate").val() > $("#secondDate").val()) $("#secondDate").val($("#firstDate").val())
    }
}

function checkSecondDate() {
    if ($("#firstDate").val() !== "" && $("#secondDate").val() !== "") {
        if ($("#secondDate").val() < $("#firstDate").val()) $("#firstDate").val($("#secondDate").val())
    }
}


clearTable = function () {
    $('#cartHeader').empty();
    $('#example').empty();
    $('#pagination').empty();
    $('#cartHead').empty();
}

function generatePagesIndexes(startPage, endPage) {
    let arr = [];
    for (let i = startPage; i < endPage + 1; i++) {
        arr.push(i);
    }
    return arr;
}

function getProductCount() {
    $.ajax({
        type: "GET",
        url: 'http://localhost:8189/api/v1/orders/get-user-order-page-count',
        data: {
            first_date: $("#firstDate").val() ? $("#firstDate").val() : null,
            second_date: $("#secondDate").val() ? $("#secondDate").val() : null,
        },
        success: function (response) {
            productCount = response;
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
                $("#pagination").empty()
                $("#pagination").append("<li class=\"page-item\" >\n" +
                    "                    <button class=\"page-link\" tabindex=\"-1\" id='prePage' onclick=' showOrders(currentPage - 1)' ><<</button>\n" +
                    "                </li>")
                for (let k = 0; k < PaginationArray.length; k++) {
                    let classOfLi = (currentPage === PaginationArray[k]) ? "page-item active" : "page-item"
                    $("#pagination").append("<li class=\"" + classOfLi + "\"><a class=\"page-link\" onclick=\"showOrders(" + PaginationArray[k] + ")\" id=''>" + PaginationArray[k] + "</a></li>")
                }
                $("#pagination").append("  <li class=\"page-item\">" +
                    "                    <button class=\"page-link\" id='nextPage' onclick=' showOrders(currentPage + 1)'>>></button  >" +
                    "                </li>")
                if (currentPage === 1) {
                    $("#prePage").prop('disabled', true)
                } else $("#prePage").prop('disabled', false)
                if (currentPage >= totalPages) {
                    $("#nextPage").prop('disabled', true)
                } else $("#nextPage").prop('disabled', false)
            } else {
                $("#pagination").empty()
            }
        }
    })
}



goToOrder = function (orderId) {
    localStorage.orderUuid = orderId;
    location.assign("http://localhost:8189/orders-result")
}

$(document).ready(function () {

    $("#firstDate").on('change', function (event) {
        event.preventDefault();
        checkFirstDate()
        showOrders();
    })
    $("#secondDate").on('change', function (event) {
        event.preventDefault();
        checkSecondDate()
        showOrders();
    })

});