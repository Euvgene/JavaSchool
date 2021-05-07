
<html>
<header>
    <meta charset="utf-8" >
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="/css/file.css">

    <script type="text/javascript" src="/js/common/small_cart.js"></script>
    <script type="text/javascript" src="/js/user/user_products.js"></script>

</header>
<head id="header">
    <jsp:include page="header.jsp" />

    <title>Products</title>
</head>

<body class="d-flex flex-column h-100">
<h6 class="smallCartHeader" id="smallCartHeader" ></h6>
<div class="smallCartDiv" id = "smallCart">


</div>
<main class="flex-shrink-0">

    <div class="container mt-5">
            <h3  style="margin-top: 100px;">Filter</h3>
            <div class="form-group row">
                <div class="form-group col-md-3">
                    <label for="filterTitle">Product name</label>
                    <input class="form-control" type="text" id="filterTitle" pattern="[abc]">
                </div>
                <div class="form-group col-md-3">
                    <label for="filterGender">Gender</label>
                    <input class="form-control" type="text" id="filterGender" >
                </div>
                <div class="form-group col-md-3">
                    <label for="filterMinCost">Min price</label>
                    <input class="form-control" type="number"  id="filterMinCost" >
                </div>
                <div class="form-group col-md-3">
                    <label for="filterMaxCost">Max price</label>
                    <input class="form-control" type="number"  id="filterMaxCost">
                </div>
                <div class="invalid-feedback">
                    Please provide a valid state.
                </div>
            </div>
            <div>
                <button class="btn btn-success" type="submit" id="filterButton">Submit</button>
            </div>
        <nav style="margin-left: 15px">
            <ul class="pagination" id="pagination">
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
        <div>
            <div id="example" class="container"   >

            </div>
        </div>

    </div>


</main>

</body>
</html>
