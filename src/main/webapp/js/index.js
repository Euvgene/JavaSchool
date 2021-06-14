function createCart() {
    $.ajax({
        type: "POST",
        url: "http://localhost:8189/api/v1/cart",
        success: function (response) {
            localStorage.marketCartUuid = response;
            console.log(localStorage.marketCartUuid)
        }
    });
}

requestWithToken = function (){
    $.ajax({
        type: "GET",
        url: 'http://localhost:8189/',
        headers: {
            "Authorization": "Bearer " + localStorage.token
        },success:function (){
            if(localStorage.lang){
                window.location.href = "http://localhost:8189/products"+ localStorage.lang;
            } else {
                window.location.href = "http://localhost:8189/products";
            }

        }
    })
}


function authGuest() {
    let formData = {
        username: "guest",
        password: "12345678",
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
            localStorage.role = result.userRole;
         requestWithToken()
        }
    })
}

$(document).ready(function () {
/*
       delete localStorage.marketCartUuid
*/
    if(!localStorage.role){
        authGuest()
    } else{
        requestWithToken()
    }

    if (!localStorage.marketCartUuid) {
        createCart();
    }

    console.log(localStorage.marketCartUuid)

});