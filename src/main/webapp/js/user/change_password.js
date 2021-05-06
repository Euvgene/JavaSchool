changePassword = function () {
    if ($("#form").valid()) {
        let formData = {
            oldPassword: $("#oldPassword").val(),
            newPassword: $("#firstPassword").val()
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
                document.getElementById("oldPassword").style.borderColor = 'green';
                document.getElementById("firstPassword").style.borderColor = 'green';
                document.getElementById("secondPassword").style.borderColor = 'green';
                const delay = 2500;
                setTimeout(function () {
                    location.assign("http://localhost:8189/user-products")
                }, delay);
            }, error: function () {
                document.getElementById("oldPassword").style.borderColor = 'red';
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