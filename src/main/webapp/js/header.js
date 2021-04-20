
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

            localStorage.currentUserName = formData.username;
            localStorage.token = result.token;
            localStorage.role = result.userRole;
            if (result.userRole === "[ROLE_USER]") {
                window.location.href = "userMain"
            } else {
                window.location.href = "adminMain"
            }
        }
    });
}

function isUserLoggedIn() {
    if (localStorage.currentUserName) {
        if (localStorage.role === "[ROLE_USER]") {
            location.assign("http://localhost:8189/userMain");

        } else if (localStorage.role === "[ROLE_ADMIN]") {
            location.assign("http://localhost:8189/adminMain");
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