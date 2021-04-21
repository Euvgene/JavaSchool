
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



$(document).ready(function () {
    if (!localStorage.marketCartUuid) {
        createCart();
    }
});