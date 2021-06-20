function logOut() {
    delete localStorage.currentUserName;
    delete localStorage.token;
    delete localStorage.pageIndx;
    if (localStorage.role === "[ROLE_USER]") {
        delete localStorage.marketCartUuid;
    }
    localStorage.productId = null;
    localStorage.parametersId = null;
    delete localStorage.role;
    window.location.href = "http://localhost:8189/logout";
}

headerRequest = function () {
    $.ajax({
        type: "GET",
        url: 'http://localhost:8189/',
        headers: {
            "Authorization": "Bearer " + localStorage.token
        }, complete: function () {
        }
    })
}


$(document).ready(function () {
    $("#exitButton").click(function (event) {
        event.preventDefault();
        logOut()
    });
    $("#EnLang").click(function (event) {
        localStorage.lang = "?lang=en"
    });

    $("#RuLang").click(function (event) {
        localStorage.lang = "?lang=ru"
    });

    $("#products").click(function (event) {
        event.preventDefault();
        $.ajax({
            type: "GET",
            url: 'http://localhost:8189/',
            headers: {
                "Authorization": "Bearer " + localStorage.token
            }, complete: function () {
                window.location.href = "http://localhost:8189/user-products" + localStorage.lang;
            }
        })
    });

    $("#orders").click(function (event) {
        event.preventDefault();
        $.ajax({
            type: "GET",
            url: 'http://localhost:8189/',
            headers: {
                "Authorization": "Bearer " + localStorage.token
            }, complete: function () {
                window.location.href = "http://localhost:8189/user-orders" + localStorage.lang;
            }
        })
    });
    $("#cart").click(function (event) {
        event.preventDefault();
        $.ajax({
            type: "GET",
            url: 'http://localhost:8189/',
            headers: {
                "Authorization": "Bearer " + localStorage.token
            }, complete: function () {
                window.location.href = "http://localhost:8189/user-cart" + localStorage.lang;
            }
        })
    });
    $("#changeAccount").click(function (event) {
        event.preventDefault();
        $.ajax({
            type: "GET",
            url: 'http://localhost:8189/',
            headers: {
                "Authorization": "Bearer " + localStorage.token
            }, complete: function () {
                window.location.href = "http://localhost:8189/user-info" + localStorage.lang;
            }
        })
    });

    $("#adminProducts").click(function (event) {
        event.preventDefault();
        $.ajax({
            type: "GET",
            url: 'http://localhost:8189/',
            headers: {
                "Authorization": "Bearer " + localStorage.token
            }, complete: function () {
                window.location.href = "http://localhost:8189/admin-products" + localStorage.lang;
            }
        })
    });
    $("#addProduct").click(function (event) {
        event.preventDefault();
        $.ajax({
            type: "GET",
            url: 'http://localhost:8189/',
            headers: {
                "Authorization": "Bearer " + localStorage.token
            }, complete: function () {
                window.location.href = "http://localhost:8189/add-products" + localStorage.lang;
            }
        })
    });
    $("#changeOrder").click(function (event) {
        event.preventDefault();
        $.ajax({
            type: "GET",
            url: 'http://localhost:8189/',
            headers: {
                "Authorization": "Bearer " + localStorage.token
            }, complete: function () {
                window.location.href = "http://localhost:8189/change-orders" + localStorage.lang;
            }
        })
    });
    $("#statistic").click(function (event) {
        event.preventDefault();
        $.ajax({
            type: "GET",
            url: 'http://localhost:8189/',
            headers: {
                "Authorization": "Bearer " + localStorage.token
            }, complete: function () {
                window.location.href = "http://localhost:8189/statistic" + localStorage.lang;
            }
        })
    });
});