let orderState = null;
const ELEMENTS_NUMBER_PER_LINE = 10
let currentPage = null;

clearTable = function () {
    $('#cartHeader').empty();
    $('#pagination').empty();
    $('#example').empty();
    $('#cartHead').empty();
}

showConfirmButton = function (id) {
    document.getElementById('confirm' + id).style.display = 'inline';
}

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

function generatePagesIndexes(startPage, endPage) {
    let arr = [];
    for (let i = startPage; i < endPage + 1; i++) {
        arr.push(i);
    }
    return arr;
}

function getOrderCount() {
    const e = document.getElementById("state");
    $.ajax({
        type: "GET",
        url: 'http://localhost:8189/api/v1/orders/get-all-order-page-count',
        data: {
            first_date: $("#firstDate").val() ? $("#firstDate").val() : null,
            second_date: $("#secondDate").val() ? $("#secondDate").val() : null,
            state: e.options[e.selectedIndex].id
        },
        success: function (response) {
            console.log(response)
            productCount = response;
            $("#pagination").empty()
            if (productCount > 5) {
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
                    "                    <button class=\"page-link\" tabindex=\"-1\" id='prePage' onclick=' getAllOrders(currentPage - 1)' ><<</button>\n" +
                    "                </li>")
                for (let k = 0; k < PaginationArray.length; k++) {
                    let classOfLi = (currentPage === PaginationArray[k]) ? "page-item active" : "page-item"
                    $("#pagination").append("<li class=\"" + classOfLi + "\"><a class=\"page-link\" onclick=\"getAllOrders(" + PaginationArray[k] + ")\" id=''>" + PaginationArray[k] + "</a></li>")
                }
                $("#pagination").append("  <li class=\"page-item\">" +
                    "                    <button class=\"page-link\" id='nextPage' onclick=' getAllOrders(currentPage + 1)'>>></button  >" +
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

changeOrder = function (orderName, id) {
    if ($("#changeOrderForm").valid()) {
        const s = document.getElementById('state' + orderName);
        const address = document.getElementById('address' + orderName);

        console.log(address.value)
        $.ajax({
            type: "GET",
            url: "http://localhost:8189/api/v1/orders/update",
            headers: {
                "Authorization": "Bearer " + localStorage.token
            },
            data: {
                order_id: id,
                delivery_address: address.value,
                state: s.value
            },
            complete: function () {
                $("#confirm" + id.substring(0, 8)).hide(100)
                if (s.value === "RETURN") {
                    document.getElementById('state' + orderName).disabled = true;
                }

            }
        })
    }
}



$(document).ready(function () {
    getAllOrders()

    $("#firstDate").on('change', function (event) {
        event.preventDefault();
        checkFirstDate()
        getAllOrders();
    })
    $("#secondDate").on('change', function (event) {
        event.preventDefault();
        checkSecondDate()
        getAllOrders();
    })

    $("#state").on('change', function (event) {
        event.preventDefault();
        getAllOrders();
    })

});
