<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
    <jsp:include page="userHeader.jsp" />
    <jsp:include page="../filter_product.jsp" />
    <jsp:include page="../small_cart.jsp" />
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
    <script type="text/javascript">
        function showButton(role, count, k) {

            if (role === "[ROLE_USER]" && count > 0) {
                return "<input id='cartButton" + productList[k].productId + "' class=\"btn btn-primary\" type='submit' onclick= \"addToCart(" + productList[k].productId + "," + count + ")\"  value='<fmt:message key="label.addToCart"/>'/> " +
                    "</div>"
            } else if (count > 0) {
                return "<input id='cartButton" + productList[k].productId + "' class=\"btn btn-primary\" type='submit' onclick= \"addToCart(" + productList[k].productId + "," + count + ")\"  value='<fmt:message key="label.addToCart"/>'/>" +
                    "</div>"
            } else {
                return "<input class=\"btn btn-danger\" type='submit'  value='<fmt:message key="label.NotAvailable"/>' disabled style='color: black'/>" +
                    "</div>"
            }
        }

        function getProducts(pageIndex = 1) {
            products = new Map();
            const genderName = document.getElementById("gender");
            const categoryName = document.getElementById("category");
            let gender;
            let formData = {
                page: pageIndex,
                minPrice: $("#filterMinCost").val() ? $("#filterMinCost").val() : "0",
                maxPrice: $("#filterMaxCost").val() ? $("#filterMaxCost").val() : Number.MAX_VALUE + "",
                name: $("#filterTitle").val() ? $("#filterTitle").val() : "",
                category: (categoryName.options[categoryName.selectedIndex].text === "Choose..." || categoryName.options[categoryName.selectedIndex].text === "Выберите...") ? "" : categoryName.options[categoryName.selectedIndex].text,
                gender: (genderName.options[genderName.selectedIndex].text === "Choose..." || genderName.options[genderName.selectedIndex].text === "Выберите...") ? "" : genderName.options[genderName.selectedIndex].id,
                quantity: $('#available').is(':checked') ? AVAILABLE_PRODUCT : ALL_PRODUCT,
            }
            console.log(formData)
            $.ajax({
                type: "POST",
                contentType: "application/json",
                url: 'http://localhost:8189/api/v1/products/get',
                data: JSON.stringify(formData),
                dataType: 'json',
                success: function (result) {
                    productList = result;
                    currentPage = pageIndex
                    console.log(result)

                    $('#example').empty();
                    $('#currentPage').empty();
                    let rd = $('<div ></div>');
                    if (productList.length > 0) {
                        $('#pagination').show(200);
                        for (let k = 0; k < productList.length; k++) {
                            if (smallCartList != null) {
                                console.log(productList[k].productId)
                                index = smallCartList.findIndex(i => i.productId === productList[k].productId)
                                if (index >= 0)
                                    productList[k].productQuantity = productList[k].productQuantity - smallCartList[index].quantity
                            }

                            let button = showButton(localStorage.role, productList[k].productQuantity, k);
                            gender =  (productList[k].parameters.productGender=== "Female") ? '<fmt:message key="label.female"/>': '<fmt:message key="label.male"/>'

                            rd.append('<div class = "block">' +
                                "<p class=\"page-information\"><img id=\"photoId" + productList[k].productId + "\" src=\"/images/" + productList[k].fotoId + "\" + width=\"150\" height=\"150\"></p>" +
                                "<p class=\"page-information\"> <fmt:message key="label.name"/>: " + productList[k].productTitle + "</p>" +
                                "<p class=\"page-information\"> <fmt:message key="label.gender"/>: " + gender + "</p>" +
                                "<p class=\"page-information\"> <fmt:message key="label.age"/>: < " + productList[k].parameters.productAge + "</p>" +
                                "<p class=\"page-information\"> <fmt:message key="label.Lifespan"/>:  " + productList[k].parameters.productLifespan + "</p>" +
                                "<p class=\"page-information\"> <fmt:message key="label.price"/>:  " + productList[k].productPrice + " $</p>" +
                                button);
                            $('#example').append(rd);
                        }
                        getProductCount(formData)
                    } else {
                        clearTable()
                        $('#example').append("<h3><fmt:message key="label.productListIsEmpty"/></h3>");
                    }
                }
            });
        }
    </script>
</main>

</body>
</html>
