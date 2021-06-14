function tryToAuth() {
    $("#errorMassageAuth").empty();
    let formData = {
        username: $("#username").val(),
        password: $("#password").val(),
        cartId: localStorage.marketCartUuid
    }
    console.log(JSON.stringify(formData));
    // DO POST
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "http://localhost:8189/api/v1/auth",
        data: JSON.stringify(formData),
        dataType: 'json',
        success: function (result) {

            localStorage.currentUserName = formData.username;
            localStorage.token = result.token;
            localStorage.role = result.userRole;
            let param = window.location.search;
            if (window.location.href.match("/cart")) {
                if (localStorage.role === "[ROLE_USER]") {
                    $.ajax({
                        type: "GET",
                        url: 'http://localhost:8189/',
                        headers: {
                            "Authorization": "Bearer " + localStorage.token
                        }, complete: function () {
                            window.location.href = "user-cart"+ param;
                        }
                    })
                } else {
                    $.ajax({
                        type: "GET",
                        url: 'http://localhost:8189/',
                        headers: {
                            "Authorization": "Bearer " + localStorage.token
                        }, complete: function () {
                            window.location.href = "admin-products"+ param;
                        }
                    })
                }
            } else if (window.location.href.match("/products")) {
                if (localStorage.role === "[ROLE_USER]") {
                    $.ajax({
                        type: "GET",
                        url: 'http://localhost:8189/',
                        headers: {
                            "Authorization": "Bearer " + localStorage.token
                        }, complete: function () {
                            window.location.href = "user-products"+ param;
                        }
                    })
                } else {
                    $.ajax({
                        type: "GET",
                        url: 'http://localhost:8189/',
                        headers: {
                            "Authorization": "Bearer " + localStorage.token
                        }, complete: function () {
                            window.location.href = "admin-products"+ param;
                        }
                    })
                }
            } else if (window.location.href.match("/registration")) {

                    $.ajax({
                        type: "GET",
                        url: 'http://localhost:8189/products',
                        headers: {
                            "Authorization": "Bearer " + localStorage.token
                        }, complete: function () {
                            window.location.href = "/"+ param;
                        }
                    })
            }
        }, error: function (response) {
            console.log(response)
            $("#errorMassageAuth").append("<input type=\"text\" " +
                "disabled    class=\"errorText\" style=\"text-align: center; width: 100%; border: none;outline: none;\"" +
                " value='" + response.responseJSON.message + "'>")
        }

    });
}

function isUserLoggedIn() {
    if (localStorage.currentUserName) {
        if (localStorage.role === "[ROLE_USER]") {
            $.ajax({
                type: "GET",
                url: 'http://localhost:8189/',
                headers: {
                    "Authorization": "Bearer " + localStorage.token
                }, complete: function () {
                    window.location.href = "user-products";
                }
            })

        } else if (localStorage.role === "[ROLE_ADMIN]") {
            $.ajax({
                type: "GET",
                url: 'http://localhost:8189/',
                headers: {
                    "Authorization": "Bearer " + localStorage.token
                }, complete: function () {
                    window.location.href = "admin-products";
                }
            })
        }
    }
}


function goToRegistration() {
    window.location.href = "registration"
}

function auth() {
    $.ajax({
        type: "GET",
        url: 'http://localhost:8189/logout'
        , complete: function () {
            tryToAuth()
        }
    })

}

$(document).ready(function () {

    isUserLoggedIn();

    $("#logIn").click(function (event) {
        event.preventDefault();
        auth()
    });

    $("#regButton").click(function (event) {
        event.preventDefault();
        goToRegistration()
    });

});