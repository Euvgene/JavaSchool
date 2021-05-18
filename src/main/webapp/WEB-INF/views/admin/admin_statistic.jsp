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
            <div class="form-group col-md-2" style="margin-top: 23px">
                <button class="btn btn-success" type="submit" id="productButton" style="width: 100%">Product statistic</button>
            </div>
            <div class="form-group col-md-2" style="margin-top: 23px">
                <button class="btn btn-success" type="submit" id="userButton" style="width: 100%">User statistic</button>
            </div>
            <div class="form-group col-md-2" style="margin-top: 23px">
                <button class="btn btn-success" type="submit" id="proceeds" style="width: 100%">Proceeds</button>
            </div>
            <div class="form-group col-md-2">
                <label for="firstDate">From date</label>
                <input class="form-control" type="date" id="firstDate">
            </div>
            <div class="form-group col-md-2">
                <label for="secondDate">To date</label>
                <input class="form-control" type="date" id="secondDate">
            </div>


        </div>




        <div class="tableDiv">
            <h3 id="cartHeader" style="margin-top: 100px;"></h3>
            <table class="table table-success table-striped" cellpadding="0" cellspacing="0"
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
        <nav id="pagination">

        </nav>

    </div>
</main>

</body>
</html>
