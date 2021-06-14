function logOut() {
    delete localStorage.currentUserName;
    delete localStorage.token;
    delete localStorage.pageIndx;
    if (localStorage.role==="[ROLE_USER]") {
        delete localStorage.marketCartUuid;
    }
    localStorage.productId = null;
    localStorage.parametersId = null;
    delete localStorage.role;
    window.location.href = "http://localhost:8189/logout";
}


$(document).ready(function () {
    $("#exitButton").click(function (event) {
        event.preventDefault();
        logOut()
    });
    $("#EnLang").click(function (event) {
       localStorage.lang = "?lang=en"
    });

    $("#RuLang").click(function (event) {
        localStorage.lang = "?lang=ru"
    });

});