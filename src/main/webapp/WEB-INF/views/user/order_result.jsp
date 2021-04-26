<%--
  Created by IntelliJ IDEA.
  User: julia
  Date: 26.04.2021
  Time: 14:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<header>
    <script type="text/javascript" src="/js/user/order_result.js"></script>
</header>
<head>
    <jsp:include page="userHeader.jsp"/>
    <title>New order</title>
</head>
<body class="d-flex flex-column h-100" onload="showOrder()">
<main class="flex-shrink-0">

    <div class="col-md-6" style="margin: auto">
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
        <div id="cartButtons"   >

        </div>
    </div>
</main>

</body>
</html>