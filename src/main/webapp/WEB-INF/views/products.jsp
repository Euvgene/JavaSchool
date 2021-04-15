<html>
<header>
    <meta charset="utf-8">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" <%--integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous"--%>>
    <script type="text/javascript" src="/js/products.js"></script>
</header>
<head>
    <title>Products</title>
</head>
<body onload="ajaxGet1()">
<div>
    <table align="center" id="example" class="table table-hover" cellpadding="20"  >
        <thead>
        </thead>
        <tbody>    </tbody>
    </table>
</div>
<nav >
    <ul class="pagination">
        <li class="page-item" id="prePage">
            <button class="page-link" tabindex="-1" >Previous</button>
        </li>
        <li class="page-item active" aria-current="page" id="current">
        </li>
        <li class="page-item" id="nextPage">
            <button class="page-link" >Next</button>
        </li>
    </ul>
</nav>
</body>
</html>
