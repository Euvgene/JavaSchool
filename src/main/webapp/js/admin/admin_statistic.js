
clearTable = function () {
    $('#cartHeader').empty();
    $('#example').empty();
    $('#cartHead').empty();
}

function getStatistic(name) {
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
            console.log(response)
            let order = response;
            let money = ""
            let total = null;
            clearTable();
            $('#cartHead').append(
                "                    <tr>" +
                "                        <td align='center' style='white-space: nowrap'>Name</td>" +
                "                        <td align='center' style='white-space: nowrap'>Count</td>" +
                "                    </tr>");

            $('#cartHeader').append("Statistic");
            $('#example').empty();
            if (order.length > 0) {
                if(name === "proceeds"){
                     money = "$"
                }
                for (let k = 0; k < order.length; k++) {
                    total = total + order[k].number;
                    let rd = $('<tr class=""></tr>');
                    rd.append(
                        "<td align='center' '> " + order[k].name + " </td>" +
                        "<td align='center' '> " + order[k].number + ''+money+'</td>');
                    $('#example').append(rd);
                }
                $('#example').append("<tr>" +
                    "<td style=\"justify-content:center; margin: auto;font-family:'Lucida Sans', " +
                    "'Lucida Sans Regular', 'Lucida Grande', 'Lucida Sans Unicode', Geneva, Verdana, sans-serif;" +
                    "font-weight: bold \" align='center' >Total</td>" +
                    "<td align='center' >" + total + ""+money+"</td>" +
                    "</tr>");
            } else {
                $('#cartHeader').append("Statistic is empty");
            }
        }
    })
}

$(document).ready(function () {
    $("#productButton").click(function (event) {
        event.preventDefault();
        getStatistic("product");
    });

    $("#userButton").click(function (event) {
        event.preventDefault();
        getStatistic("user")
    });

    $("#proceeds").click(function (event) {
        event.preventDefault();
        getStatistic("proceeds");
    });


});