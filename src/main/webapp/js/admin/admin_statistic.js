localStorage.statisticName = "product";
clearTable = function () {
    $('#cartHeader').empty();
    $('#cartHead').empty();
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

function getStatistic(name) {
    localStorage.statisticName = name;
    $.ajax({
        type: "GET",
        url: "http://localhost:8189/api/v1/orders/statistic",
        headers: {
            "Authorization": "Bearer " + localStorage.token
        },
        data: {
            statistic_name: name,
            first_date: $("#firstDate").val() ? $("#firstDate").val() : null,
            second_date: $("#secondDate").val() ? $("#secondDate").val() : null
        },
        success: function (response) {
            let order = response;
            let total = null;
            clearTable();
            $('#cartHead').append(
                "                    <tr>" +
                "                        <td align='center' style='white-space: nowrap'>Name</td>" +
                "                        <td align='center' style='white-space: nowrap'>Count</td>" +
                "                    </tr>");
            let count = 0;
            $('#example').empty();
            if (order.length > 0) {
                $('#cartHeader').append("Statistic");
                for (let k = 0; k < order.length; k++) {
                    total = total + order[k].number;
                    let rd = $('<tr class=""></tr>');
                    rd.append(
                        "<td align='center' > " + order[k].name + " </td>" +
                        "<td align='center' > " + order[k].number + '' + " $" + '</td>');
                    $('#example').append(rd);
                }
                if (name === "proceeds") {
                    $('#example').append("<tr>" +
                        "<td style=\"justify-content:center; margin: auto;font-family:'Lucida Sans', " +
                        "'Lucida Sans Regular', 'Lucida Grande', 'Lucida Sans Unicode', Geneva, Verdana, sans-serif;" +
                        "font-weight: bold \" align='center' >Total</td>" +
                        "<td align='center' >" + total + "" + " $" + "</td>" +
                        "</tr>");
                }
            } else {
                clearTable()
                $('#cartHeader').append("Statistic is empty");
            }
        }
    })
}


function getProductStatistic() {
    localStorage.statisticName = "product";
    $.ajax({
        type: "GET",
        url: "http://localhost:8189/api/v1/orders/product-statistic",
        headers: {
            "Authorization": "Bearer " + localStorage.token
        },
        data: {
            first_date: $("#firstDate").val() ? $("#firstDate").val() : null,
            second_date: $("#secondDate").val() ? $("#secondDate").val() : null
        },
        success: function (response) {
            let order = response;
            clearTable();
            $('#cartHead').append(
                "                    <tr>" +
                "                        <td align='center' style='white-space: nowrap'>Name</td>" +
                "                        <td align='center' style='white-space: nowrap'>Count</td>" +
                "                        <td align='center' style='white-space: nowrap'>Price</td>" +
                "                        <td align='center' style='white-space: nowrap'>Total price</td>" +
                "                    </tr>");
            let count = 0;
            $('#example').empty();
            if (order.length > 0) {
                $('#cartHeader').append("Product statistic");
                while (count < order.length) {
                    for (let k = 0; k < order.length; k++) {
                        count++;
                        let rd = $('<tr class=""></tr>');
                        rd.append(
                            "<td align='center' > " + order[k].name + " </td>" +
                            "<td align='center' > " + order[k].number + '' + '</td>' +
                            "<td align='center' >" + order[k].pricePerProduct + "" + " $" + "</td>" +
                            "<td align='center' >" + order[k].pricePerProduct * order[k].number + "" + " $" + "</td>");
                        $('#example').append(rd);
                    }
                }
            } else {
                clearTable()
                $('#cartHeader').append("Statistic is empty");
            }
        }

    })
}

function chooseStatistic() {
    if (localStorage.statisticName === "product") {
        getProductStatistic()
    } else {
        getStatistic(localStorage.statisticName)
    }

}

$(document).ready(function () {
    getProductStatistic();
    $("#productButton").click(function (event) {
        event.preventDefault();
        getProductStatistic();
    });

    $("#userButton").click(function (event) {
        event.preventDefault();
        getStatistic("user")
    });

    $("#proceeds").click(function (event) {
        event.preventDefault();
        getStatistic("proceeds");
    });

    $("#firstDate").on('change', function (event) {
        event.preventDefault();
        checkFirstDate()
        chooseStatistic();
    })
    $("#secondDate").on('change', function (event) {
        event.preventDefault();
        checkSecondDate()
        chooseStatistic();
    })
});