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
            state: e.options[e.selectedIndex].text
        },
        success: function (response) {
            console.log(response)
            productCount = response;
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
                $("#pagination").empty()
                $("#pagination").append("<li class=\"page-item\" >\n" +
                    "                    <button class=\"page-link\" tabindex=\"-1\" id='prePage' onclick=' getAllOrders(currentPage - 1)' >Previous</button>\n" +
                    "                </li>")
                for (let k = 0; k < PaginationArray.length; k++) {
                    let classOfLi = (currentPage === PaginationArray[k]) ? "page-item active" : "page-item"
                    $("#pagination").append("<li class=\"" + classOfLi + "\"><a class=\"page-link\" onclick=\"getAllOrders(" + PaginationArray[k] + ")\" id=''>" + PaginationArray[k] + "</a></li>")
                }
                $("#pagination").append("  <li class=\"page-item\">" +
                    "                    <button class=\"page-link\" id='nextPage' onclick=' getAllOrders(currentPage + 1)'>Next</button  >" +
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


function getAllOrders(pageIndex = 1) {
    const e = document.getElementById("state");
    $.ajax({
        type: "GET",
        url: "http://localhost:8189/api/v1/orders/all",
        headers: {
            "Authorization": "Bearer " + localStorage.token
        },
        data: {
            page: pageIndex,
            first_date: $("#firstDate").val() ? $("#firstDate").val() : null,
            second_date: $("#secondDate").val() ? $("#secondDate").val() : null,
            state: e.options[e.selectedIndex].text
        },
        success: function (response) {
            currentPage = pageIndex;
            let elementsNumber = ELEMENTS_NUMBER_PER_LINE;
            let order = response;
            console.log(response)
            if (order.length > 0) {
                clearTable();
                $('#cartHead').append(
                    "                    <tr>" +
                    "                        <td align='center' style='white-space: nowrap'>Order number</td>" +
                    "                        <td align='center' style='white-space: nowrap'>Order price</td>" +
                    "                        <td align='center' style='white-space: nowrap'>Delivery address</td>" +
                    "                        <td align='center' style='white-space: nowrap'>Payment method</td>" +
                    "                        <td align='center' style='white-space: nowrap'>State</td>" +
                    "                        <td align='center' style='white-space: nowrap'>Order date</td>" +
                    "                    </tr>");

                $('#cartHeader').append("Orders");
                $('#currentPage').empty();

                $('#example').empty();

                for (let k = 0; k < order.length; k++) {

                    switch (order[k].orderState) {
                        case "AWAITING_SHIPMENT":
                            orderState = "<option value=\"AWAITING_SHIPMENT\">awaiting shipment</option>" +
                                "         <option value=\"SHIPPED\">shipped</option>" +
                                "         <option value=\"DELIVERED\">delivered</option>" +
                                "         <option value=\"RETURN\">return</option>";
                            break;
                        case "SHIPPED":
                            orderState = "<option value=\"SHIPPED\">shipped</option>" +
                                "         <option value=\"DELIVERED\">delivered</option>" +
                                "         <option value=\"RETURN\">return</option>";
                            break;
                        case "DELIVERED":
                            orderState = "<option value=\"DELIVERED\">delivered</option>" +
                                "         <option value=\"RETURN\">return</option>";
                            break;
                        case "RETURN":
                            orderState = " <option value=\"RETURN\">return</option>";
                            break;
                    }
                    let rd = $('<tr class=""></tr>');
                    rd.append(
                        "<td style=\"justify-content:center; margin: auto;font-family:'Lucida Sans', " +
                        "'Lucida Sans Regular', 'Lucida Grande', 'Lucida Sans Unicode', Geneva, Verdana, sans-serif;" +
                        "font-weight: bold \"  align='center' > " + order[k].orderId.substring(0, 8) + "" +
                        "<button type='button' class='btn btn-info' style='display: none' id='confirm" + order[k].orderId.substring(0, 8) + "'" +
                        " onclick= \"changeOrder('" + order[k].orderId.substring(0, 8) + "','" + order[k].orderId + "')\"><svg width=\"1em\" height=\"1em\" viewBox=\"0 0 16 16\" class=\"bi bi-check2\" fill=\"currentColor\" xmlns=\"http://www.w3.org/2000/svg\">\n" +
                        "  <path fill-rule=\"evenodd\" d=\"M13.854 3.646a.5.5 0 0 1 0 .708l-7 7a.5.5 0 0 1-.708 0l-3.5-3.5a.5.5 0 1 1 .708-.708L6.5 10.293l6.646-6.647a.5.5 0 0 1 .708 0z\"/>" +
                        "</svg></button></td>" +
                        "<td align='center' class=\"justify-content-md-center\" >"
                        + order[k].totalPrice + ' $' + " </td>" +
                        "<td>" +
                        " <input class=\"form-control\" type=\"text\"  value='" + order[k].address + "'  id='address" + order[k].orderId.substring(0, 8) + "'" +
                        "onchange=\"showConfirmButton('" + order[k].orderId.substring(0, 8) + "')\" name='address'></td>" +
                        "<td align='center' > " + order[k].paymentMethod + "</td>" +
                        "<td> " +
                        "                <select class=\"form-select\" id='state" + order[k].orderId.substring(0, 8) + "' required=\"\" onchange=\"showConfirmButton('" + order[k].orderId.substring(0, 8) + "')\">" +
                        orderState +
                        "                </select>" +
                        "                <div class=\"invalid-feedback\">\n" +
                        "                    Please provide a valid state.\n" +
                        "                </div>" +

                        "</td>" +
                        "<td align='center' > " + order[k].creationDateTime.substring(0, 10) + '</td>');
                    $('#example').append(rd);
                }
                getOrderCount();
            } else {
                clearTable()
                $('#cartHeader').append("Order list is empty");
            }
        }
    })
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
