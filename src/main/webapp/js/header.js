function logOut() {
    delete localStorage.currentUserName;
    delete localStorage.token;
    delete localStorage.role;
    location.assign("http://localhost:8189");
}


function tryToAuth() {
    let formData = {
        username: $("#username").val(),
        password: $("#password").val()
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
            console.log(result);
            localStorage.currentUserName = formData.username;
            localStorage.token = result.token;
            localStorage.role = result.userRole;

            window.location.reload();
        }
    });
}

function isUserLoggedIn() {
    if (localStorage.currentUserName) {
        if (localStorage.role === "[ROLE_USER]") {
            $("#orders").show();
            hiddenForUser();
        } else if (localStorage.role === "[ROLE_ADMIN]") {
            ordersHidden()
            $("#products").hide();
            $("#cart").hide();
            $("#addProduct").show();
            $("#changeOrder").show();
            $("#statistic").show();
        }
        $("#username").hide();
        $("#password").hide();
        $("#logIn").hide();
        $("#regButton").hide();
        $("#exitButton").show();
    } else {
        hiddenForUser();
        ordersHidden();
        $("#username").show();
        $("#password").show();
        $("#logIn").show();
        $("#regButton").show();
        $("#exitButton").hide();
    }
}

function hiddenForUser() {
    $("#addProduct").hide();
    $("#changeOrder").hide();
    $("#statistic").hide();
    $("#changeAccount").hide();
}

function ordersHidden() {
    $("#orders").hide();
}

function addProduct() {
    $.ajax({
        type: "GET",
        url: "http://localhost:8189/addproducts",
            headers: {
                "Authorization": "Bearer " + localStorage.token
            },
        success: function () {
            location.assign("http://localhost:8189/addproducts");
        }
    });
}

$(document).ready(function () {
    isUserLoggedIn();


    $("#logIn").click(function (event) {
        event.preventDefault();
        tryToAuth()
    });

    $("#exitButton").click(function (event) {
        event.preventDefault();
        logOut()
    });
    $("#addProduct").click(function (event) {
        event.preventDefault();
        addProduct()
    });

});