/*const ELEMENTS_NUMBER_PER_LINE = 4

function ajaxGet1() {
    $.ajax({
        type: "GET",
        url: 'http://localhost:8189/api/v1/products',
        data: {
            p: localStorage.pageIndx,
            min_price: $("#filterMinCost").val() ? $("#filterMinCost").val() : null,
            max_price: $("#filterMaxCost").val() ? $("#filterMaxCost").val() : null,
            product_title: $("#filterTitle").val() ? $("#filterTitle").val() : null,
            gender: $("#filterGender").val() ? $("#filterGender").val() : null,
        },
        success: function (result) {
            const data = result;
            let elementsNumber = ELEMENTS_NUMBER_PER_LINE;
            let count = 0;
            while (count < data.length) {
                $('#example>tbody').empty();
                $('#current').empty(); // TODO rename
                let rd = $('<tr class=""></tr>');
                if (data.length > 0) {
                    for (let i = 0; i < data.length; i++) {
                        count++;
                        if (count > elementsNumber) {
                            elementsNumber = elementsNumber + 4;
                            rd = $('<tr class=""></tr>');
                        }
                        rd.append('<td>' +
                            "<p ><img src=\"/images/" + data[i].fotoId + ".png" + "\"+ width=\"150\" height=\"150\"></p>" +
                            "<p> Name: " + data[i].name + "</p>" +
                            "<p> Gender: " + data[i].gender + "</p>" +
                            "<p> Date of birth:  " + data[i].birthday.dayOfMonth + '.' + data[i].birthday.monthValue + '.' +
                            data[i].birthday.year + "</p>" +
                            "<p> Lifespan:  " + data[i].lifeSpan + "</p>" +
                            "<p> Price:  " + data[i].productPrice + "</p>" +
                            "<form name='f'  onsubmit=\"tryToAuth()\">" +
                            "<input type='submit'  value='Add to cart' />" +
                            "</form>" +
                            '</td>');

                        $('#example').append(rd);
                    }
                    $("#nextPage").attr('disabled', false);
                }
                count++;
            }
            if (data.length > 0) {
                $('#current').append("<span class=\"page-link\">" + localStorage.pageIndx + "</span>");
            }

            if (data.length === 0 && localStorage.pageIndx > 1) {
                $("#nextPage").attr('disabled', true);

                let pageIndx = Number(localStorage.getItem("pageIndx"));
                localStorage.setItem("pageIndx", --pageIndx);
            } else if (data.length === 0) {
                window.alert("No result")
            }
            console.log(result);
        }
    });
}


$(document).ready(function () {
   /!* isUserLoggedIn();*!/
    if (!localStorage.pageIndx) {
        localStorage.setItem("pageIndx", 1);
    }
    // GET REQUEST
    $("#prePage").click(function (event) {
        if (localStorage.pageIndx < 2) {
            $(this).attr('disabled', true);
        } else {
            $(this).attr('disabled', false);
            const pageIndx = Number(localStorage.getItem("pageIndx"));
            localStorage.setItem("pageIndx", String(pageIndx - 1));
            event.preventDefault();
            ajaxGet1();
        }
    });

    $("#filterButton").click(function (event) {
        localStorage.setItem("pageIndx", 1);
        event.preventDefault();
        ajaxGet1()
    });

    $("#nextPage").click(function (event) {
        let pageIndx = Number(localStorage.getItem("pageIndx"));
        localStorage.setItem("pageIndx", ++pageIndx);
        event.preventDefault();

        ajaxGet1();
    });

});*/
function loadCategory() {
    $("#newCategory").hide();
    $("#addCategoryButton").hide();
    $.ajax({
        type: "GET",
        url: 'http://localhost:8189/api/v1/category',
        success: function (result) {
            localStorage.setItem("categories", JSON.stringify(result));
            for (let i = 0; i < result.length; i++) {
                $('#category').append('<option>' + result[i].categoryName + '</option>');
            }
            console.log(result);
        }
    });
}


function showNewCategoryForm() {
    $("#newCategory").show();
    $("#addCategoryButton").show();
    $("#showNewCategoryForm").hide();
}

function hideCategoryForm() {
    $("#newCategory").hide();
    $("#addCategoryButton").hide();
    $("#showNewCategoryForm").show();
}

function createNewCategory() {
    let formData = {
        categoryName: $("#newCategory").val()
    }
    console.log(JSON.stringify(formData));
    // DO POST
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "http://localhost:8189/api/v1/category",
        data: JSON.stringify(formData),
        dataType: 'json'
    });
}

$(document).ready(function () {
    hideCategoryForm();
    $("#showNewCategoryForm").click(function (event) {
        event.preventDefault();
        showNewCategoryForm()
    });
});

let categories = JSON.parse(localStorage.getItem("categories") || "[]"); // вытаскивает из стораджа обьект