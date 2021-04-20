function checkout() {
    let formData = {

        firstName: $("#firstName").val(),
        lastName: $("#lastName").val(),
        password: $("#password").val(),
        email: $("#email").val(),
        birthday: document.getElementById('birthday').value,
        userAddress: {
            addressId: 1,
            country: "Russia",
            city: "SinCity",
            postalCode: "165-15-15",
            streetName: "Nevskii",
            houseNumber: 13,
            flatNumber: 13
        },
        role: {
            id: 1,
            roleName: "ROLE_USER"
        }
    }
    console.log(JSON.stringify(formData));
    // DO POST
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "http://localhost:8189/api/v1/users",
        data: JSON.stringify(formData),
        dataType: 'json',
        success: function (result) {

           /* localStorage.currentUserName = formData.username;
            localStorage.token = result.token;
            localStorage.role = result.userRole;
            if (result.userRole == "[ROLE_USER]") {
                window.location.href = "userMain"
            } else {
                window.location.href = "adminMain"
            }*/
        }
    });
}


$(document).ready(function () {

    $("#checkout").click(function (event) {
        event.preventDefault();
        checkout();
    });

});