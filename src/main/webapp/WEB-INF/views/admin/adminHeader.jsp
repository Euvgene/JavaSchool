<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!doctype html>
<html>
<header>
    <meta charset="utf-8" >
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
    <script type="text/javascript" src="/js/admin/admin_header.js"></script>
    <script type="text/javascript" src="/js/common/common.js"></script>
</header>
<head>
    <title></title>
</head>
<body class="d-flex flex-column h-100" >
<header>
    <nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark">
        <div class="container-fluid">
            <div class="collapse navbar-collapse" id="navbarCollapse">
                <ul class="navbar-nav me-auto mb-2 mb-md-0">
                    <li class="nav-item">
                        <a class="py-2" href="http://localhost:8189/" aria-label="Product">
                            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="none" stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" class="d-block mx-auto" role="img" viewBox="0 0 24 24"><title>Product</title><circle cx="12" cy="12" r="10"></circle><path d="M14.31 8l5.74 9.94M9.69 8h11.48M7.38 12l5.74-9.94M9.69 16L3.95 6.06M14.31 16H2.83m13.79-4l-5.74 9.94"></path></svg>
                        </a>
                    </li>
                    <li id="adminProducts" class="nav-item">
                        <a class="nav-link active" aria-current="page" href="http://localhost:8189/admin-products"><fmt:message key="label.products"/></a>
                    </li>
                    <li id="addProduct" class="nav-item" >
                        <a class="nav-link active" aria-current="page" href="http://localhost:8189/add-products"><fmt:message key="label.addProduct"/></a>
                    </li>
                    <li id="changeOrder" class="nav-item">
                        <a class="nav-link active" aria-current="page" href="http://localhost:8189/change-orders"><fmt:message key="label.changeOrders"/></a>
                    </li>
                    <li id="statistic" class="nav-item">
                        <a class="nav-link active" aria-current="page" href="http://localhost:8189/statistic"><fmt:message key="label.ordersStatistic"/></a>
                    </li>
                </ul>
                <div>
                    <a href="?lang=en">
                        <img id="EnLang" src="/images/united-states.png" alt="" width="25" height="25">
                    </a>
                    <a href="?lang=ru">
                        <img id="RuLang" src="/images/russia.png" alt="" width="25" height="25">
                    </a>
                </div>
                <form class="d-flex "  id="navLoginSubmit">
                    <button id="exitButton" class="btn btn-outline-success mt-2" style="margin-left: 10px; white-space: nowrap"
                            type="submit"><fmt:message key="label.exit"/>
                    </button>
                </form>
            </div>
        </div>
    </nav>
</header>
<div id="footer">
    &copy; Java School 2021
</div>
</body>
</html>
