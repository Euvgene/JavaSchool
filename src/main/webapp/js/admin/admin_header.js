
function addProduct() {
    $.ajax({
        type: "GET",
        url: "http://localhost:8189/add-products",
        headers: {
            "Authorization": "Bearer " + localStorage.token
        },
        success: function () {
            location.assign("http://localhost:8189/add-products");
        }
    });
}

$(document).ready(function () {
    $("#addProduct").click(function (event) {
        event.preventDefault();
        addProduct()
    });
});