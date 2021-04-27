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
    <jsp:include page="userHeader.jsp"/>
    <title>Orders</title>
</head>
<body class="d-flex flex-column h-100" onload="showOrders()">
<main class="flex-shrink-0">

    <div class="col-md-6" style="margin: auto">
        <h3  style="margin-top: 100px;">Filter</h3>
        <div class="form-group row">
            <div class="form-group col-md-3">
                <label for="firstDate">Product name</label>
                <input class="form-control" type="date" id="firstDate">
            </div>
            <div class="form-group col-md-3">
                <label for="secondDate">Gender</label>
                <input class="form-control" type="date" id="secondDate" >
            </div>
        </div>

        <div>
            <button class="btn btn-success" type="submit" id="filterButton">Submit</button>
        </div>

        <h3 id="cartHeader" style="margin-top: 100px;"></h3>

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
        <nav >
            <ul class="pagination" >
                <li class="page-item" id="prePage">
                    <button class="page-link" tabindex="-1" >Previous</button>
                </li>
                <li class="page-item active" aria-current="page" id="currentPage">
                </li>
                <li class="page-item" id="nextPage">
                    <button class="page-link" >Next</button>
                </li>
            </ul>
        </nav>

    </div>
</main>

</body>
</html>
