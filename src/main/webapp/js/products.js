const ELEMENTS_NUMBER_PER_LINE = 4

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
                $('#currentPage').empty(); // TODO rename
                let rd = $('<tr class=""></tr>');
                if (data.length > 0) {
                    for (let i = 0; i < data.length; i++) {
                        count++;
                        if (count > elementsNumber) {
                            elementsNumber = elementsNumber + 4;
                            rd = $('<tr class=""></tr>');
                        }
                        rd.append('<td>' +
                            "<p ><img src=\"/images/" + data[i].fotoId + ".png" + "\" + width=\"150\" height=\"150\"></p>" +
                            "<p> Name: " + data[i].name + "</p>" +
                            "<p> Gender: " + data[i].gender + "</p>" +
                            "<p> Age:  " + data[i].age  + "</p>" +
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
            }
            console.log(result);
        }
    });
}

$(document).ready(function () {
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

});
