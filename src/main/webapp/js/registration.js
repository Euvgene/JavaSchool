function checkout() {

    let formData = {
        firstName: $("#firstName").val(),
        lastName: $("#lastName").val(),
        password: $("#userPassword").val(),
        email: $("#email").val(),
        birthday: document.getElementById('birthday').value,
        userAddress: {
            country: $("#country").val(),
            city: $("#city").val(),
            postalCode: $("#zip").val(),
            streetName: $("#street").val(),
            houseNumber:$("#house_number").val(),
            flatNumber:$("#flat").val(),
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

        }
    });
}


$(document).ready(function () {

    $("#checkout").click(function (event) {
        event.preventDefault();
        checkout();
    });

});