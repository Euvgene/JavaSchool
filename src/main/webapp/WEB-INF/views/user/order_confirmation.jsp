<!doctype html>
<html lang="en-GB">
<header>
    <meta charset="utf-8">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="/css/file.css">
    <script type="text/javascript" src="/js/common/order_validation.js"></script>
    <script type="text/javascript" src="/js/order_confirmation.js"></script>
</header>
<head id="header">
    <title>Order confirmation</title>
    <jsp:include page="userHeader.jsp"/>

</head>

<body class="d-flex flex-column h-100">
<main class="flex-shrink-0">

    <div class="col-md-5" style="margin: auto">
        <h3 id="cartHeader" style="margin-top: 90px;"></h3>

        <div>
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
        </div>

        <div id="orderConfirmDiv">
            <h4 >Delivery terms</h4>
            <div>
                <div>
                    <input id="deliveryToHome" type="checkbox" class="form-check-input" checked>
                    <label class="form-check-label" for="deliveryToHome" style="margin-left: 10px">Need delivery</label>
                </div>
                <div class="form-check">
                    <input id="fromStore" type="checkbox" class="form-check-input">
                    <label class="form-check-label" for="fromStore" style="margin-left: 6px">Pick up from the
                        store</label>
                </div>
            </div>
            <form id="addressForm">
                <div class="form-group" style="margin-bottom: 8px;" id="address">
                    <label for="orderAddress" style="margin-bottom: 7px">Delivery address</label>
                    <input class="form-control" type="text" id="orderAddress" name="address">
                </div>
            </form>
            <h4>Payment</h4>
            <div>
                <div class="form-check">
                    <input id="creditCart" type="checkbox" value="card" class="form-check-input" checked>
                    <label class="form-check-label" for="creditCart" style="margin-left: 10px">Credit card</label>
                </div>
                <div class="form-check">
                    <input id="cash" type="checkbox" value="cash" class="form-check-input">
                    <label class="form-check-label" for="cash" style="margin-left: 10px">Cash</label>
                </div>
            </div>
            <form id="paymentForm">
                <div class="row gy-3" id="creditCartPayment">
                    <div class="col-md-6">
                        <label for="cc-name" class="form-label">Name on card</label>
                        <input type="text" class="form-control" id="cc-name" placeholder="JOHN BLACK" name="cardname" style="text-transform: uppercase">
                        <small class="text-muted">Full name as displayed on card</small>
                    </div>

                    <div class="col-md-6">
                        <label for="cc-number" class="form-label">Credit card number</label>
                        <input type="text" class="form-control" id="cc-number" placeholder="0000-0000-0000-0000"
                               name="number">

                    </div>

                    <div class="col-md-3">
                        <label for="cc-expiration" class="form-label">Expiration</label>
                        <input type="text" class="form-control" id="cc-expiration" placeholder="12/20"
                               name="expiration">

                    </div>

                    <div class="col-md-3">
                        <label for="cc-cvv" class="form-label">CVV</label>
                        <input type="text" class="form-control" id="cc-cvv" placeholder="888" name="cvv">
                    </div>

                </div>
            </form>
            <div style="margin-top: 10px">
                <input class="w-100 btn btn-primary" type="button" id="confirmOrder" value="Pay for the order" >
            </div>
        </div>
    </div>
    <div id="errorMassage">
    </div>
    </div>
</main>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.1/jquery.validate.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.1/additional-methods.min.js"></script>
</body>
</html>