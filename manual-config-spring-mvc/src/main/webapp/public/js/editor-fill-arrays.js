function getAjaxRequest() {
    try {
        var request = new XMLHttpRequest();
    } catch (e1) {
        try {
            request = new ActiveXObject("Msxml2.XMLHTTP");
        } catch (e2) {
            try {
                request = new ActiveXObject("Microsoft.XMLHTTP");
            } catch (e3) {
                request = false;
            }
        }
    }
    return request;
}

function fillCategories() {
    var box = document.querySelector('#categories');

    getAjaxResponseForUrl("/getCategories", box);
    box.addEventListener("value-changed", function (item) {
        var values = document.querySelector("#selected-category");
        createSelectedOption(item.detail.value, values);
        box.value = "";
    })
}

function fillRelations() {
    var box = document.querySelector('#relations');

    getAjaxResponseForUrl("/getRelations", box);
    box.addEventListener("value-changed", function (item) {
        var values = document.querySelector("#selected-relations");
        createSelectedOption(item.detail.value, values);
        box.value = "";
    })
}

function getAjaxResponseForUrl(url, form) {
    var request = getAjaxRequest();

    request.onreadystatechange = function () {
        if (request.readyState == 3 && request.status == 200) {
            form.items = JSON.parse(request.response);
        }
    };

    request.open("GET", url, true);
    request.send();

}