<!doctype html>
<html lang="en-GB">
<header>
    <meta charset="utf-8">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="/css/file.css">
    <script type="text/javascript" src="/js/cart.js"></script>
</header>
<head id="header">
    <title>Cart</title>
    <jsp:include page="header.jsp"/>
</head>

<body class="d-flex flex-column h-100" onload="getCartProducts()">
<main class="flex-shrink-0">

    <div class="col-md-6" style="margin: auto">
        <h3 id="cartHeader" style="margin-top: 100px;"></h3>

        <div>
            <table  class="table table-success table-striped" cellpadding="0" cellspacing="0"
                   style="border-collapse: separate;"  aria-describedby="">
                <thead id="cartHead" style="font-size: 1.3rem;"></thead>
                <tbody id="example"></tbody>
            </table>
        </div>
        <div id="cartButtons"   >

        </div>
        <div id="errorMassage" >
        </div>
    </div>
</main>

</body>
</html>