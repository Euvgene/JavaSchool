
function checkout() {
    const name = document.getElementById('birthday').value;
   ;
    console.log(name)
   /* let formData = {
        firstName: $("#username").val(),
        lastName: $("#lastName").val(),
        password: $("#password").val(),
        email:$("#email").val(),
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
            if (result.userRole == "[ROLE_USER]") {
                window.location.href = "userMain"
            } else {
                window.location.href = "adminMain"
            }
        }
    });*/
}




$(document).ready(function () {

    $("#checkout").click(function (event) {
        event.preventDefault();
        checkout();
    });

});