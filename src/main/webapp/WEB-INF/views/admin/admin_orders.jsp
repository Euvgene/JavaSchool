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

    <script type="text/javascript" src="/js/common/order_validation.js"></script>
    <script type="text/javascript" src="/js/admin/admin_orders.js"></script>
    <link rel="stylesheet" type="text/css" href="/css/file.css">
</header>
<head>
    <jsp:include page="adminHeader.jsp"/>
    <title>Orders</title>
</head>
<body class="d-flex flex-column h-100">
<main class="flex-shrink-0" style="margin-top: 80px;">

    <div class="container mt-auto" style="margin-top: 100px;">
        <div class="form-group row" >
            <div class="form-group col-md-3" style=" margin: auto" align="center">
                <label for="firstDate">From date</label>
                <input class="form-control" type="date" id="firstDate">
            </div>
            <div class="form-group col-md-3" style=" margin: auto" align="center">
                <label for="secondDate">To date</label>
                <input class="form-control" type="date" id="secondDate">
            </div>
            <div class="form-group col-md-3" style=" margin: auto" align="center">
                <label for="state">State</label>
                <select class="form-select" id="state" required="">
                    <option value="0">ALL</option>
                    <option value="1">Active</option>
                    <option value="2">Delivered</option>
                    <option value="3">Return</option>
                </select>
            </div>
        </div>


        <h3 id="cartHeader" style="margin-top: 100px;"></h3>

        <div>
            <form id='changeOrderForm'>
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
            </form>
        </div>
        <nav aria-label="Page navigation">
            <ul class="pagination" id="pagination">
            </ul>
        </nav>
    </div>
</main>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.1/jquery.validate.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.1/additional-methods.min.js"></script>
</body>
</html>
