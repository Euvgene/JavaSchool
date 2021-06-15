<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!doctype html>
<html lang="en-GB">
<header>
    <meta charset="utf-8" >
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="/css/file.css">
    <script type="text/javascript" src="/js/admin/admin_products.js"></script>
</header>
<head id="header">
    <title>Products</title>
    <jsp:include page="adminHeader.jsp" />
    <jsp:include page="../filter_product.jsp" />
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
        function getProducts(pageIndex = 1) {
            const genderName = document.getElementById("gender");
            const categoryName = document.getElementById("category");
            let formData = {
                page: pageIndex,
                minPrice: $("#filterMinCost").val() ? $("#filterMinCost").val() : "0",
                maxPrice: $("#filterMaxCost").val() ? $("#filterMaxCost").val() : Number.MAX_VALUE + "",
                name: $("#filterTitle").val() ? $("#filterTitle").val() : "",
                category: (categoryName.options[categoryName.selectedIndex].text === "Choose..." || categoryName.options[categoryName.selectedIndex].text === "Выберите...") ? "" : categoryName.options[categoryName.selectedIndex].text,
                gender: (genderName.options[genderName.selectedIndex].text === "Choose..." || genderName.options[genderName.selectedIndex].text === "Выберите...") ? "" : genderName.options[genderName.selectedIndex].id,
                quantity: $('#available').is(':checked') ? AVAILABLE_PRODUCT : ALL_PRODUCT,
                notAvailable: $('#notAvailable').is(':checked') ? AVAILABLE_PRODUCT : ALL_PRODUCT,
            }
            console.log(formData)
            $.ajax({
                type: "POST",
                contentType: "application/json",
                url: 'http://localhost:8189/api/v1/products/get',
                data: JSON.stringify(formData),
                dataType: 'json',
                success: function (result) {
                    let gender;
                    currentPage = pageIndex
                    productList = result;
                    console.log(result)
                    $('#example').empty();
                    $('#currentPage').empty();
                    let rd = $('<div ></div>');
                    if (productList.length > 0) {
                        for (let k = 0; k < productList.length; k++) {
                            gender =  (productList[k].parameters.productGender=== "Female") ? '<fmt:message key="label.female"/>': '<fmt:message key="label.male"/>'
                            rd.append('<div  class = "block" >' +
                                "<p class=\"page-information\"><img id=\"photoId" + productList[k].productId + "\" src=\"/images/" + productList[k].fotoId + "\" + width=\"130\" height=\"130\"></p>" +
                                "<p class=\"page-information\"> <fmt:message key="label.name"/>: " + productList[k].productTitle + "</p>" +
                                "<p class=\"page-information\"> <fmt:message key="label.gender"/>: " + gender + "</p>" +
                                "<p class=\"page-information\"> <fmt:message key="label.age"/>: <  " + productList[k].parameters.productAge + " year</p>" +
                                "<p class=\"page-information\"> <fmt:message key="label.Lifespan"/>:  " + productList[k].parameters.productLifespan + "</p>" +
                                "<p class=\"page-information\"> <fmt:message key="label.price"/>:  " + productList[k].productPrice + " $</p>" +
                                "<p class=\"page-information\" id=\"quantity" + productList[k].productId + "\"> <fmt:message key="label.Quantity"/>:  " + productList[k].productQuantity + "</p>" +
                                "<input class=\"btn btn-primary\" type='button' onclick= \"changeProduct(" + productList[k].productId + ")\"  value='<fmt:message key="label.changeProduct"/>'/>" +
                                "<input class=\"btn btn-danger\" type='button' id=\'buttonDelete" + productList[k].productId + "\' onclick= \"deleteProduct(" + productList[k].productId + ")\"  value='<fmt:message key="label.Delete"/>'/>" +
                                "</div>");
                            $('#example').append(rd);
                            if (productList[k].productQuantity === 0) {
                                $("#buttonDelete" + productList[k].productId).hide();
                                $("#quantity" + productList[k].productId).css("color", "red");
                            }
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
