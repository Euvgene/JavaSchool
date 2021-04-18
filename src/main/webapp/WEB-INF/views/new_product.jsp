<%--
  Created by IntelliJ IDEA.
  User: julia
  Date: 17.04.2021
  Time: 17:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="" %>
<html>
<header>
    <meta charset="utf-8" >
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
    <script type="text/javascript" src="/js/add_product.js"></script>
</header>
<head>
    <%@ include file="header.jsp" %>
    <title>Add New Product</title>
</head>
<body class="d-flex flex-column h-100" onload = "loadCategory()">
<main class="flex-shrink-0" style="margin-top: 80px;">
    <div class="container mt-5">
        <div class="col-md-4">
            <h4 class="mb-4">Create product</h4>
            <form class="needs-validation" novalidate="">
             <div>
                <div class="row g-3">
                    <div class="col-md-5">
                        <label for="productName" class="form-label">Product name</label>
                        <input type="text" class="form-control" id="productName" placeholder="" value=""
                               required="">
                           <div class="invalid-feedback">
                               Valid first name is required.
                           </div>
                    </div>

                    <div class="col-md-4">
                        <label for="state" class="form-label">Category</label>
                        <select class="form-select" id="category" required="">
                            <option value="">Choose...</option>
                        </select>

                        <div class="invalid-feedback">
                            Please provide a valid state.
                        </div>

                    </div>

                    <div class="col-md-2" style="margin-top: 47px; white-space: nowrap">
                        <button id="showNewCategoryForm" class="btn btn-primary"  type="submit">+category</button>
                    </div>


                    <div class="col-md-4">
                        <input type="text" class="form-control" id="newCategory" placeholder="New category name" value=""
                               required="">
                        <div class="invalid-feedback">
                            Please provide a valid state.
                        </div>
                    </div>

                    <div class="col-md-3" style="white-space: nowrap">
                        <button  id="addCategoryButton"  class="btn btn-primary" onclick="createNewCategory()">Add</button>
                    </div>
                </div>
             </div>
                <div class="row g-3">
                    <div class="col-md-5">
                        <label for="country" class="form-label">Country</label>
                        <select class="form-select" id="country" required="">
                            <option value="">Choose...</option>
                            <option>United States</option>
                        </select>
                        <div class="invalid-feedback">
                            Please select a valid country.
                        </div>
                    </div>

                    <div class="col-md-4">
                        <label for="state" class="form-label">State</label>
                        <select class="form-select" id="state" required="">
                            <option value="">Choose...</option>
                            <option>California</option>
                        </select>
                        <div class="invalid-feedback">
                            Please provide a valid state.
                        </div>
                    </div>

                    <div class="col-md-3">
                        <label for="zip" class="form-label">Zip</label>
                        <input type="text" class="form-control" id="zip" placeholder="" required="">
                        <div class="invalid-feedback">
                            Zip code required.
                        </div>
                    </div>
               </div>


                <hr class="my-4">

                <div class="form-check">
                    <input type="checkbox" class="form-check-input" id="same-address">
                    <label class="form-check-label" for="same-address">Shipping address is the same as my billing
                        address</label>
                </div>

                <div class="form-check">
                    <input type="checkbox" class="form-check-input" id="save-info">
                    <label class="form-check-label" for="save-info">Save this information for next time</label>
                </div>

                <hr class="my-4">

                <h4 class="mb-3">Payment</h4>

                <div class="my-3">
                    <div class="form-check">
                        <input id="credit" name="paymentMethod" type="radio" class="form-check-input" checked=""
                               required="">
                        <label class="form-check-label" for="credit">Credit card</label>
                    </div>
                    <div class="form-check">
                        <input id="debit" name="paymentMethod" type="radio" class="form-check-input" required="">
                        <label class="form-check-label" for="debit">Debit card</label>
                    </div>
                    <div class="form-check">
                        <input id="paypal" name="paymentMethod" type="radio" class="form-check-input" required="">
                        <label class="form-check-label" for="paypal">PayPal</label>
                    </div>
                </div>

                <div class="row gy-3">
                    <div class="col-md-6">
                        <label for="cc-name" class="form-label">Name on card</label>
                        <input type="text" class="form-control" id="cc-name" placeholder="" required="">
                        <small class="text-muted">Full name as displayed on card</small>
                        <div class="invalid-feedback">
                            Name on card is required
                        </div>
                    </div>

                    <div class="col-md-6">
                        <label for="cc-number" class="form-label">Credit card number</label>
                        <input type="text" class="form-control" id="cc-number" placeholder="" required="">
                        <div class="invalid-feedback">
                            Credit card number is required
                        </div>
                    </div>

                    <div class="col-md-3">
                        <label for="cc-expiration" class="form-label">Expiration</label>
                        <input type="text" class="form-control" id="cc-expiration" placeholder="" required="">
                        <div class="invalid-feedback">
                            Expiration date required
                        </div>
                    </div>

                    <div class="col-md-3">
                        <label for="cc-cvv" class="form-label">CVV</label>
                        <input type="text" class="form-control" id="cc-cvv" placeholder="" required="">
                        <div class="invalid-feedback">
                            Security code required
                        </div>
                    </div>
                </div>

                <hr class="my-4">

                <button class="w-100 btn btn-primary btn-lg" type="submit">Continue to checkout</button>
            </form>
        </div>
    </div>
</main>
</body>
</html>
