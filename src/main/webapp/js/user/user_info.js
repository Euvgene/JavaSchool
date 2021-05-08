let validName = true;
let validEmail = true;
let oldName;
let oldEmail;

getUser = function () {
    console.log(localStorage.token)
    $.ajax({
        type: "GET",
        url: 'http://localhost:8189/api/v1/users',
        headers: {
            "Authorization": "Bearer " + localStorage.token
        }, success: function (result) {
            $('#birthday').val(result.birthday)
            $('#name').val(result.firstName)
            $('#lastName').val(result.lastName)
            $('#email').val(result.email)
            $('#street').val(result.userAddress.streetName)
            $('#house_number').val(result.userAddress.houseNumber)
            $('#flat').val(result.userAddress.flatNumber)
            $('#country').val(result.userAddress.country)
            $('#city').val(result.userAddress.city)
            $('#zip').val(result.userAddress.postalCode)
            oldEmail = $('#email').val()
            oldName = $('#name').val()
        }
    });
}

changeUser = function () {
    /* if ($("#form").valid()) {*/
    console.log(validEmail + validName)
    if (validName && validEmail) {
        let formData = {
            firstName: $("#name").val(),
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
            role: {
                id: 1,
                roleName: "ROLE_USER"
            }
        }

        $.ajax({
            type: "PUT",
            contentType: "application/json",
            url: "http://localhost:8189/api/v1/users",
            data: JSON.stringify(formData),
            dataType: 'json',
            success: function (response) {
                /* const delay = 3000;
                 setTimeout(function () {
                     location.assign("http://localhost:8189/user-products")
                 }, delay);*/
            }, success: function () {
                $.ajax({
                    type: "GET",
                    url: 'http://localhost:8189/logout',
                    success: function () {
                        formData = {
                            username: $("#name").val(),
                            password: $("#userPassword").val(),
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
                                localStorage.token = result.token;
                            }, error: function (resp) {
                                console.log(resp)
                            }
                        })
                    }
                })
            }, error: function (resp) {
                console.log(resp)
            }
        })
    }
}

function checkUserName(str) {
    console.log($("#"+ str).val() + "this")
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: 'http://localhost:8189/api/v1/users/' + str,
        data: JSON.stringify({userName: $("#" + str).val(),}),
        dataType: 'json',
        success: function () {
            if (str === "name") {
                validName = true;
            } else {
                validEmail = true;
            }
        },
        error: function (res) {
            if (str === "name") {
                if(oldName === $('#name').val()) validName = true
                else validName = false
            } else {
                if(oldEmail === $('#email').val()) validEmail = true
                else validEmail = false
            }
            console.log(res)
        }
    })
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

    $("#name").on('change', function (event) {
        event.preventDefault();
        checkUserName("name");
    })

    $("#email").on('change', function (event) {
        event.preventDefault();
        checkUserName("email");
    })
});
