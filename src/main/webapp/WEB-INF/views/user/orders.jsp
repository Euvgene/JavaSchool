<%--
  Created by IntelliJ IDEA.
  User: julia
  Date: 26.04.2021
  Time: 20:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<header>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <script type="text/javascript" src="/js/user/orders.js"></script>
</header>
<head>
    <title>Orders</title>
    <jsp:include page="userHeader.jsp"/>

</head>
<body class="d-flex flex-column h-100" onload="showOrders()">
<main class="flex-shrink-0">

    <div class="col-md-6" style="margin: auto">
        <div class="form-group row" style="margin-top: 100px;">
            <div class="form-group col-md-3">
                <label for="firstDate">From date</label>
                <input class="form-control" type="date" id="firstDate">
            </div>
            <div class="form-group col-md-3">
                <label for="secondDate">To date</label>
                <input class="form-control" type="date" id="secondDate" >
            </div>
        </div>

        <h3 id="cartHeader" style="margin-top: 50px;"></h3>
        <nav aria-label="Page navigation">
            <ul class="pagination" id="pagination">
            </ul>
        </nav>

        <div>
            <table  class="table table-success table-striped" cellpadding="0" cellspacing="0"
                    style="border-collapse: separate;">
                <style>
                    thead {
                        font-size: 1.3rem;
                    }
                    .justify-content-md-center {
                        height: 55px;
                        align-items: center;
                    }
                </style>
                <thead id="cartHead"></thead>
                <tbody id="example"></tbody>
            </table>
        </div>


    </div>
</main>

</body>
</html>
