function tryToAuth() {
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
            if (window.location.href.endsWith("/cart")) {
                    if(localStorage.role === "[ROLE_USER]"){
                        window.location.href = "user-cart";
                    } else {
                        window.location.href = "admin-main";
                    }
            } else if (window.location.href.endsWith("/products")){
                if(localStorage.role === "[ROLE_USER]"){
                    window.location.href = "user-products";
                } else {
                    window.location.href = "admin-products";
                }
            } else {
                if(localStorage.role === "[ROLE_USER]"){
                    window.location.href = "user-products";
                } else {
                    window.location.href = "admin-products";
                }
            }
        }

    });
}

function isUserLoggedIn() {
    if (localStorage.currentUserName) {
        if (localStorage.role === "[ROLE_USER]") {
            location.assign("http://localhost:8189/user-main");

        } else if (localStorage.role === "[ROLE_ADMIN]") {
            location.assign("http://localhost:8189/admin-main");
        }
    }
}


function goToRegistration() {
    window.location.href = "registration"
}

$(document).ready(function () {

    isUserLoggedIn();

    $("#logIn").click(function (event) {
        event.preventDefault();
        tryToAuth()
    });

    $("#regButton").click(function (event) {
        event.preventDefault();
        goToRegistration()
    });

});