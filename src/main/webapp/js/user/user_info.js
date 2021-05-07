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

changeUser = function () {
   /* if ($("#form").valid()) {*/
        let formData = {
            firstName: $("#firstName").val(),
            lastName: $("#lastName").val(),
            email: $("#email").val(),
            birthday: $("#birthday").val(),
            password: $('#userPassword').val(),
            userAddress: {
                country: $("#country").val(),
                city: $("#city").val(),
                postalCode: $("#zip").val(),
                streetName: $("#street").val(),
                houseNumber: $("#house_number").val(),
                flatNumber: $("#flat").val(),
            },
            role: {
                id: 1,
                roleName: "ROLE_USER"
            }
        }
        console.log(localStorage.token +    "_____" + localStorage.marketCartUuid)
        $.ajax({
            type: "PUT",
            contentType: "application/json",
            url: "http://localhost:8189/api/v1/users",
            data: JSON.stringify(formData),
            dataType: 'json',
            success:function (response){
                console.log(response)
                localStorage.token = response.token;
            },
            complete: function (){
                let newData = {
                    username: $("#firstName").val(),
                    password: $("#userPassword").val(),
                    cartId: localStorage.marketCartUuid
                }
                console.log(JSON.stringify(formData));
                // DO POST
                $.ajax({
                    type: "POST",
                    contentType: "application/json",
                    url: "http://localhost:8189/api/v1/auth",
                    data: JSON.stringify(newData),
                    dataType: 'json',
                    success: function (result){
                        console.log(result)
                        localStorage.token = result.token;
                    },
                    error:function (resp){
                        console.log(resp)
                    }
                })
            }
        })


   /* }*/
}

$(document).ready(function () {

    $("#changeUser").click(function (event) {
        event.preventDefault();
        changeUser();
    });

    $("#userPasswordButton").click(function (event) {
        event.preventDefault();
        location.assign("http://localhost:8189/change-password")
    });

});