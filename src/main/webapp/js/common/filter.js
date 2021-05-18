function loadCategory() {
    $.ajax({
        type: "GET",
        url: 'http://localhost:8189/api/v1/category',
        success: function (result) {
            for (let i = 0; i < result.length; i++) {
                $('#category').append('<option>' + result[i].categoryName + '</option>');
            }
        }, error: function (response) {
        }
    });
}