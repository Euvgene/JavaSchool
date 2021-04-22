
<html>
<header>
    <meta charset="utf-8">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
    <script type="text/javascript" src="/js/cart.js"></script>
</header>
<head id="header">
    <jsp:include page="userHeader.jsp" />
    <title>Products</title>
</head>

<body class="d-flex flex-column h-100" onload="getCartProducts()">
<main class="flex-shrink-0">

    <div class="container mt-5">
        <h3  style="margin-top: 100px;">Cart</h3>

        <div>
            <table id="example" class="table table-success table-striped" cellpadding="0" cellspacing="0"   >

                <tbody>    </tbody>
            </table>
        </div>
    </div>
</main>

</body>
</html>