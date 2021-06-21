appendMessage = function (response) {
    $("#errorMassage").append("<input type=\"text\" " +
        "disabled    class=\"errorText\" style=\"text-align: center; width: 100%; border: none;outline: none;\"" +
        " value='" + response + "'>")
}


changePassword = function () {
    if ($("#form").valid()) {
        let formData = {
            oldPassword: $("#oldPassword").val(),
            firstPassword: $("#firstPassword").val(),
            secondPassword: $("#secondPassword").val()
        }
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "http://localhost:8189/api/v1/users/password",
            headers: {
                "Authorization": "Bearer " + localStorage.token
            },
            data: JSON.stringify(formData),
            dataType: 'json',
            success: function (response) {
                document.getElementById("form").style.background = '#a8e3a4'
                const delay = 1500;
                setTimeout(function () {
                    location.assign("http://localhost:8189/user-products")
                }, delay);
            }, error: function (response) {
                console.log(response)
                    appendMessage(response.responseJSON.message)
            }
        });
    }
}

submitChanges = function () {
    const inp1 = document.getElementById("firstPassword"), inp2 = document.getElementById("secondPassword");
    if (inp1.value !== inp2.value) {
        document.getElementById("firstPassword").style.borderColor = 'red';
        document.getElementById("secondPassword").style.borderColor = 'red';
    } else {
        changePassword()
    }
}

$(document).ready(function () {
    $("#submitChanges").click(function (event) {
        event.preventDefault();
        submitChanges()
    });

    $("#firstPassword").click(function (event) {
        event.preventDefault();
        document.getElementById("firstPassword").style.borderColor = 'grey'
    });

    $("#secondPassword").click(function (event) {
        event.preventDefault();
        document.getElementById("secondPassword").style.borderColor = 'grey'
    });
});