localStorage.statisticName = "product";
clearTable = function () {
    $('#cartHeader').empty();
    $('#cartHead').empty();
}


function checkFirstDate() {
    if ($("#firstDate").val() !== "" && $("#secondDate").val() !== "") {
        if ($("#firstDate").val() > $("#secondDate").val()) $("#secondDate").val($("#firstDate").val())
    }
}

function checkSecondDate() {
    if ($("#firstDate").val() !== "" && $("#secondDate").val() !== "") {
        if ($("#secondDate").val() < $("#firstDate").val()) $("#firstDate").val($("#secondDate").val())
    }
}

function chooseStatistic() {
    if (localStorage.statisticName === "product") {
        getProductStatistic()
    } else {
        getStatistic(localStorage.statisticName)
    }

}

$(document).ready(function () {
    getProductStatistic();
    $("#productButton").click(function (event) {
        event.preventDefault();
        getProductStatistic();
    });

    $("#userButton").click(function (event) {
        event.preventDefault();
        getStatistic("User")
    });

    $("#proceeds").click(function (event) {
        event.preventDefault();
        getStatistic("Proceeds");
    });

    $("#firstDate").on('change', function (event) {
        event.preventDefault();
        checkFirstDate()
        chooseStatistic();
    })
    $("#secondDate").on('change', function (event) {
        event.preventDefault();
        checkSecondDate()
        chooseStatistic();
    })
});