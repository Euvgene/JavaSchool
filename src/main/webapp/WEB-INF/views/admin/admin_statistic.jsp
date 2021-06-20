<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!doctype html>
<html lang="en-GB">
<header>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <link rel="stylesheet" type="text/css" href="/css/file.css">
    <script type="text/javascript" src="/js/admin/admin_statistic.js"></script>
</header>
<head>
    <title>Statistic</title>
    <jsp:include page="adminHeader.jsp"/>

</head>
<body class="d-flex flex-column h-100">
<main class="flex-shrink-0" style="margin-top: 80px;">

    <div class="container mt-auto" style="margin-top: 100px;">


        <div class="form-group row">
            <div class="form-group col-md-2" style="margin-top: 23px; white-space: nowrap;">
                <button class="btn btn-success" type="submit" id="productButton" style="width: 100%"><fmt:message key="label.productStatistic"/></button>
            </div>
            <div class="form-group col-md-2" style="margin-top: 23px; white-space: nowrap;">
                <button class="btn btn-success" type="submit" id="userButton" style="width: 100%"><fmt:message key="label.userStatistic"/></button>
            </div>
            <div class="form-group col-md-2" style="margin-top: 23px">
                <button class="btn btn-success" type="submit" id="proceeds" style="width: 100%"><fmt:message key="label.proceeds"/></button>
            </div>
            <div class="form-group col-md-2">
                <label for="firstDate"><fmt:message key="label.FromDate"/></label>
                <input class="form-control" type="date" id="firstDate" >
            </div>
            <div class="form-group col-md-2">
                <label for="secondDate"><fmt:message key="label.ToDate"/></label>
                <input class="form-control" type="date" id="secondDate" >
            </div>


        </div>

        <div class="tableDiv">
            <h3 id="cartHeader" style="margin-top: 100px;"></h3>
            <table class="table table-success table-striped" cellpadding="0" cellspacing="0"
                   style="border-collapse: separate;" aria-describedby="">
                <tr style="display: none">
                    <th id=""></th>
                </tr>
                <thead id="cartHead" style=" font-size: 1.3rem;"></thead>
                <tbody id="example"></tbody>
            </table>
        </div>
        <nav id="pagination">

        </nav>

    </div>
</main>
<script type="text/javascript">
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
                    "<tr>" +
                    "<td align='center' style='white-space: nowrap'><fmt:message key="label.name"/></td>" +
                    "<td align='center' style='white-space: nowrap'><fmt:message key="label.Total"/></td>" +
                    "</tr>");
                $('#example').empty();
                if (order.length > 0) {

                    for (let k = 0; k < order.length; k++) {
                        total = total + order[k].number;
                        let rd = $('<tr class=""></tr>');
                        rd.append(
                            "<td align='center' > " + order[k].name + " </td>" +
                            "<td align='center' > " + order[k].number + '' + " $" + '</td>');
                        $('#example').append(rd);
                    }
                    if (name === "Proceeds") {
                        $('#cartHeader').append("<fmt:message key="label.proceeds"/>");
                        $('#example').append("<tr>" +
                            "<td style=\"justify-content:center; margin: auto;font-family:'Lucida Sans', " +
                            "'Lucida Sans Regular', 'Lucida Grande', 'Lucida Sans Unicode', Geneva, Verdana, sans-serif;" +
                            "font-weight: bold \" align='center' ><fmt:message key="label.Total"/></td>" +
                            "<td align='center' >" + total + "" + " $" + "</td>" +
                            "</tr>");
                    } else {
                        $('#cartHeader').append("<fmt:message key="label.userStatistic"/>");
                    }
                } else {
                    clearTable()
                    $('#cartHeader').append("<fmt:message key="label.statisticIsEmpty"/>");
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
                console.log(response)
                $('#cartHead').append(
                    "<tr>" +
                    "<td align='center' style='white-space: nowrap'><fmt:message key="label.name"/></td>" +
                    "<td align='center' style='white-space: nowrap'><fmt:message key="label.Quantity"/></td>" +
                    "<td align='center' style='white-space: nowrap'><fmt:message key="label.Total"/></td>" +
                    "</tr>");
                let count = 0;
                $('#example').empty();
                if (order.length > 0) {
                    $('#cartHeader').append("<fmt:message key="label.productStatistic"/>");
                    while (count < order.length) {
                        for (let k = 0; k < order.length; k++) {
                            count++;
                            let rd = $('<tr class=""></tr>');
                            rd.append(
                                "<td align='center' > " + order[k].name + " </td>" +
                                "<td align='center' > " + order[k].number + '' + '</td>' +
                                "<td align='center' >" + order[k].pricePerProduct  + "" + " $" + "</td>");
                            $('#example').append(rd);
                        }
                    }
                } else {
                    clearTable()
                    $('#cartHeader').append("<fmt:message key="label.statisticIsEmpty"/>");
                }
            }

        })
    }
</script>

</body>
</html>
