<!doctype html>
<html lang="en-GB">
<header>
    <meta charset="utf-8" >
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="/css/file.css">
    <script type="text/javascript" src="/js/user/user_products.js"></script>

</header>
<head id="header">
    <title>Products</title>
    <jsp:include page="header.jsp" />
    <jsp:include page="filter_product.jsp" />
    <jsp:include page="small_cart.jsp" />

</head>

<body class="d-flex flex-column h-100">

<main class="flex-shrink-0">

    <div class="container mt-5">
           <nav aria-label="Page navigation" style="margin-left: 10px; margin-top: 100px">
            <ul class="pagination" id="pagination">
            </ul>
        </nav>
        <div>
            <div id="example" class="container"   >

            </div>
        </div>

    </div>
</main>
</body>
</html>
