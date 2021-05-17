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

appendMessage = function (response) {
    $("#errorMassage").append("<input type=\"text\" " +
        "disabled    class=\"errorText\" style=\"text-align: center; width: 100%; border: none;outline: none;\"" +
        " value='" + response + "'>")
}

changeUser = function () {
    if (validName && validEmail && $("#form").valid()) {
        let formData = {
            firstName: $("#name").val(),
            lastName: $("#lastName").val(),
            email: $("#email").val(),
            password:$("#userPassword").val(),
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
            headers: {
                "Authorization": "Bearer " + localStorage.token
            },
            success: function () {
                $.ajax({
                    type: "GET",
                    url: 'http://localhost:8189/logout',
                    success: function () {
                        formData = {
                            username: $("#name").val(),
                            password: $("#userPassword").val(),
                            cartId: localStorage.marketCartUuid
                        }
                        $.ajax({
                            type: "POST",
                            contentType: "application/json",
                            url: "http://localhost:8189/api/v1/auth",
                            data: JSON.stringify(formData),
                            dataType: 'json',
                            success: function (result) {
                                document.getElementById("form").style.background = '#a8e3a4';
                                localStorage.token = result.token;
                                const delay = 1500;
                                setTimeout(function () {
                                    location.assign("http://localhost:8189/user-products")
                                }, delay);
                            }, error: function (response) {
                                console.log(response)
                                appendMessage( response.responseJSON.message)
                            }
                        })
                    }
                })
            }, error: function (response) {
                console.log(response)
                if (response.responseJSON.message.length > 1) {
                    for (let k = 0; k < response.responseJSON.message.length; k++) {
                        appendMessage( response.responseJSON.message[k])
                    }
                } else {
                    appendMessage(response.responseJSON.message)
                }
            }
        })
    }
}

function checkUserName(str) {
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: 'http://localhost:8189/api/v1/users/' + str,
        data: JSON.stringify({value: $("#" + str).val(),}),
        dataType: 'json',
        success: function () {
            if (str === "name") validName = true;
            else validEmail = true;
        },
        error: function (response) {
            if (str === "name") {
                if (oldName === $('#name').val()) validName = true
                else {
                    $("#validName").append("<input type=\"text\" " +
                        "disabled    class=\"errorText\" style=\"text-align: center; width: 100%; border: none;" +
                        "outline: none;\" value='" + response.responseJSON.message + "'>")
                    validName = false
                }
            } else {
                if (oldEmail === $('#email').val()) validEmail = true
                else {
                    $("#validEmail").append("<input type=\"text\" " +
                        "disabled    class=\"errorText\" style=\"text-align: center; width: 100%; border: none;" +
                        "outline: none;\" value='" + response.responseJSON.message + "'>")
                    validEmail = false
                }
            }
        }
    })
}

$(document).ready(function () {

    $("#changeUser").click(function (event) {
        event.preventDefault();
        $("#errorMassage").empty()
        changeUser();
    });

    $("#userPasswordButton").click(function (event) {
        event.preventDefault();
        location.assign("http://localhost:8189/change-password")
    });

    $("#name").on('change', function (event) {
        event.preventDefault();
        $("#validName").empty();
        checkUserName("name");
    })

    $("#email").on('change', function (event) {
        event.preventDefault();
        $("#validEmail").empty();
        checkUserName("email");
    })
});
