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
                            "<p ><img src=\"/images/" + data[i].fotoId + ".jpg" + " \" width=\"150\" height=\"150\" /></p>" +
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


/*
function logOut() {
    delete localStorage.currentUserName;
    delete localStorage.token;
    delete localStorage.role;
    location.assign("http://localhost:8189");
}


function tryToAuth() {
    let formData = {
        username: $("#username").val(),
        password: $("#password").val()
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
            console.log(result);
            localStorage.currentUserName = formData.username;
            localStorage.token = result.token;
            localStorage.role = result.userRole;

            window.location.reload();
        }
    });
}

function isUserLoggedIn() {
    if (localStorage.currentUserName) {
        if (localStorage.role === "[ROLE_USER]") {
            $("#orders").show();
            hiddenForUser();
        } else if (localStorage.role === "[ROLE_ADMIN]") {
            ordersHidden()
            $("#products").hide();
            $("#cart").hide();
            $("#addProduct").show();
            $("#changeOrder").show();
            $("#statistic").show();
        }
        $("#username").hide();
        $("#password").hide();
        $("#logIn").hide();
        $("#regButton").hide();
        $("#exitButton").show();
    } else {
        hiddenForUser();
        ordersHidden();
        $("#username").show();
        $("#password").show();
        $("#logIn").show();
        $("#regButton").show();
        $("#exitButton").hide();
    }
}

function hiddenForUser() {
    $("#addProduct").hide();
    $("#changeOrder").hide();
    $("#statistic").hide();
    $("#changeAccount").hide();
}

function ordersHidden() {
    $("#orders").hide();
}
*/

$(document).ready(function () {
   /* isUserLoggedIn();*/
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

  /*  $("#logIn").click(function (event) {
        event.preventDefault();
        tryToAuth()
    });

    $("#exitButton").click(function (event) {
        event.preventDefault();
        logOut()
    });*/

    $("#nextPage").click(function (event) {
        let pageIndx = Number(localStorage.getItem("pageIndx"));
        localStorage.setItem("pageIndx", ++pageIndx);
        event.preventDefault();

        ajaxGet1();
    });

});
