<html>
<header>
    <meta charset="utf-8" >
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
    <script type="text/javascript" src="/js/products.js"></script>
</header>
<head>
    <title>Products</title>
</head>

<body class="d-flex flex-column h-100" onload="ajaxGet1() ">

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
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href="http://localhost:8189/products">Product</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active" id="myOrders" aria-current="page" href="#">Orders</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href="#">Cart</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href="#">Add product</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href="#">Change Orders</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href="#">Orders Statistic</a>
                    </li>
                </ul>
                <form class="d-flex "  id="navLoginSubmit">
                    <input class="form-control mt-2" type="text"
                           aria-describedby="inputGroup-sizing-sm" style="margin-left: 10px;" placeholder="Login">

                    <input  class="form-control mt-2" type="password"
                            aria-describedby="inputGroup-sizing-sm" style="margin-left: 10px;" placeholder="Password">
                    <button id="logIn" class="btn btn-outline-success mt-2" type="submit" style="margin-left: 10px;white-space: nowrap">
                        Log in
                    </button>
                    <button id="regButton" class="btn btn-outline-success mt-2" style="margin-left: 10px; white-space: nowrap"
                          type="submit">Sign up
                    </button>
                </form>
            </div>
        </div>
    </nav>
</header>

<main class="flex-shrink-0">

    <div class="container mt-5">


            <h3  style="margin-top: 100px;">Filter</h3>
            <div class="form-group row">
                <div class="form-group col-md-3">
                    <label for="filterTitle">Product name</label>
                    <input class="form-control" type="text" id="filterTitle">
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
            </div>
            <div>
                <button class="btn btn-success" type="submit" id="filterButton">Submit</button>
            </div>

        <div>
            <table id="example" class="table table-hover"   >
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
    </div>
</main>

</body>
</html>
