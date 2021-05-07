function checkout() {
    if ($("#form").valid()) {
        $("#errorMassage").empty();
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
                houseNumber: $("#house_number").val(),
                flatNumber: $("#flat").val(),
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
                console.log(result)
                $("#username").val($("#firstName").val());
                $("#password").val($("#userPassword").val());
                tryToAuth();
            }, error: function (response) {
                console.log(response);
                if (response.responseJSON.message.isArray){
                    for (let k = 0; k < response.responseJSON.message.length; k++) {
                        $("#errorMassage").append("<input type=\"text\" " +
                            "disabled    class=\"errorText\" style=\"text-align: center; width: 100%; border: none;" +
                            "outline: none;\" value='" + response.responseJSON.message[k] + "'>")
                    }
                } else {
                    $("#errorMassage").append("<input type=\"text\" " +
                        "disabled    class=\"errorText\" style=\"text-align: center; width: 100%; border: none;" +
                        "outline: none;\" value='" + response.responseJSON.message + "'>")
                }

            }
        });
    }
}


$(document).ready(function () {

    $("#checkout").click(function (event) {
        event.preventDefault();
        checkout()
    });


});
