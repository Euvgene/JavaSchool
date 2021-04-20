
function addProduct() {
    $.ajax({
        type: "GET",
        url: "http://localhost:8189/addproducts",
        headers: {
            "Authorization": "Bearer " + localStorage.token
        },
        success: function () {
            location.assign("http://localhost:8189/addproducts");
        }
    });
}



$(document).ready(function () {
    $("#addProduct").click(function (event) {
        event.preventDefault();
        addProduct()
    });
});