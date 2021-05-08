function logOut() {
    delete localStorage.currentUserName;
    delete localStorage.token;
    delete localStorage.pageIndx;
    if (localStorage.role==="[ROLE_USER]") {
        delete localStorage.marketCartUuid;
    }
    delete localStorage.role;
    location.assign("http://localhost:8189/logout");
    location.assign("http://localhost:8189/");
}


$(document).ready(function () {
    $("#exitButton").click(function (event) {
        event.preventDefault();
        logOut()
    });


});