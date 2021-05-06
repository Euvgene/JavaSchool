let statisticName = null;
clearTable = function () {
    $('#cartHeader').empty();
    $('#pagination').empty();
    $('#cartHead').empty();
}

setPageIndex = function (){
    localStorage.setItem("orderPageIndx", 1);
}

function getStatistic(name) {
    $.ajax({
        type: "GET",
        url: "http://localhost:8189/api/v1/orders/statistic",
        headers: {
            "Authorization": "Bearer " + localStorage.token
        },
        data: {
            page: localStorage.orderPageIndx,
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
            let count = 0;
            $('#cartHeader').append("Statistic");
            if (name !== "proceeds"){
                $('#pagination').append("  <ul class=\"pagination\">" +
                    "                <li class=\"page-item\" id='prePage'>" +
                    "                    <button class=\"page-link\" tabIndex=\"-1\" onclick=\"prePage()\">Previous</button>" +
                    "                </li>\n" +
                    "                <li class=\"page-item active\" aria-current=\"page\" id=\"currentPage\">" +
                    "                </li>\n" +
                    "                <li class=\"page-item\" id='nextPage'>" +
                    "                    <button class=\"page-link\" onclick=\"nextPage()\">Next</button>" +
                    "                </li>\n" +
                    "            </ul>");
            }

            $('#currentPage').empty();
            while (count < order.length) {
                $('#example').empty();
                if (order.length > 0) {

                    for (let k = 0; k < order.length; k++) {
                        count++;
                        total = total + order[k].number;
                        let rd = $('<tr class=""></tr>');
                        rd.append(
                            "<td align='center' '> " + order[k].name + " </td>" +
                            "<td align='center' '> " + order[k].number + '' + money + '</td>');
                        $('#example').append(rd);
                    }
                    if (name === "proceeds") {
                        $('#example').append("<tr>" +
                            "<td style=\"justify-content:center; margin: auto;font-family:'Lucida Sans', " +
                            "'Lucida Sans Regular', 'Lucida Grande', 'Lucida Sans Unicode', Geneva, Verdana, sans-serif;" +
                            "font-weight: bold \" align='center' >Total</td>" +
                            "<td align='center' >" + total + "" + "$" + "</td>" +
                            "</tr>");
                    }

                    $("#nextPage").attr('disabled', false);
                } else {
                    $('#cartHeader').append("Statistic is empty");
                }
            }
            if (order.length > 0) {
                $('#currentPage').append("<span class=\"page-link\">" + localStorage.orderPageIndx + "</span>");
            }
            if (order.length === 0 && localStorage.orderPageIndx > 1) {
               /* $("#nextPage").attr('disabled', true);*/
                let orderPageIndx = Number(localStorage.getItem("orderPageIndx"));
                localStorage.setItem("orderPageIndx", --orderPageIndx);
                $('#currentPage').append("<span class=\"page-link\">" + localStorage.orderPageIndx + "</span>");
            } else if (order.length === 0) {
                clearTable();
                $('#example').empty();
                $('#cartHeader').append("Statistic is empty");
            }
        }
    })
}

prePage = function () {
    if (localStorage.orderPageIndx < 2) {
        $(this).attr('disabled', true);
    } else {
        $(this).attr('disabled', false);
        const orderPageIndx = Number(localStorage.getItem("orderPageIndx"));
        localStorage.setItem("orderPageIndx", String(orderPageIndx - 1));

        getStatistic(statisticName);
    }
};

nextPage = function () {
    let pageIndx = Number(localStorage.getItem("orderPageIndx"));
    localStorage.setItem("orderPageIndx", ++pageIndx);
    getStatistic(statisticName);
};
$(document).ready(function () {
    localStorage.setItem("orderPageIndx", 1);
    $("#productButton").click(function (event) {
        event.preventDefault();
        statisticName = "product"
        setPageIndex()
        getStatistic(statisticName);
    });

    $("#userButton").click(function (event) {
        event.preventDefault();
        statisticName = "user"
        setPageIndex()
        getStatistic(statisticName)
    });

    $("#proceeds").click(function (event) {
        event.preventDefault();
        statisticName = "proceeds"
        setPageIndex()
        getStatistic(statisticName);
    });
});