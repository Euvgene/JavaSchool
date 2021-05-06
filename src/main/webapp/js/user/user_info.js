getUser = function () {
    $.ajax({
        type: "GET",
        url: 'http://localhost:8189/api/v1/users',
        dataType: 'json',
        headers: {
            "Authorization": "Bearer " + localStorage.token
        }, success: function (result) {
            $('#birthday').val(result.birthday)
            $('#firstName').val(result.firstName)
            $('#lastName').val(result.lastName)
            $('#userPassword').val(result.password)
            $('#email').val(result.email)
            $('#street').val(result.userAddress.streetName)
            $('#house_number').val(result.userAddress.houseNumber)
            $('#flat').val(result.userAddress.flatNumber)
            $('#country').val(result.userAddress.country)
            $('#city').val(result.userAddress.city)
            $('#zip').val(result.userAddress.postalCode)

        }
    });
}

changeUser = function (){
    if ($("#form").valid()) {
        let formData = {
            firstName: $("#firstName").val(),
            lastName: $("#lastName").val(),
            email: $("#email").val(),
            birthday: $("#birthday").val(),
            userAddress: {
                country: $("#country").val(),
                city: $("#city").val(),
                postalCode: $("#zip").val(),
                streetName: $("#street").val(),
                houseNumber: $("#house_number").val(),
                flatNumber: $("#flat").val(),
            },
        }
        $.ajax({
            type: "PUT",
            contentType: "application/json",
            url: "http://localhost:8189/api/v1/users",
            data: JSON.stringify(formData),
            dataType: 'json',
        })
        location.assign("http://localhost:8189/user-products")
    }
}

$(document).ready(function () {

    $("#changeUser").click(function (event) {
        event.preventDefault();
        changeUser();
    });

    $("#userPassword").click(function (event) {
        event.preventDefault();
        location.assign("http://localhost:8189/change-password")
    });

});