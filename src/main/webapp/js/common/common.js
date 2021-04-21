function logOut() {
    delete localStorage.currentUserName;
    delete localStorage.token;
    delete localStorage.role;
    delete localStorage.marketCartUuid;
    location.assign("http://localhost:8189");
}



$(document).ready(function () {
    $("#exitButton").click(function (event) {
        event.preventDefault();
        logOut()
    });


});