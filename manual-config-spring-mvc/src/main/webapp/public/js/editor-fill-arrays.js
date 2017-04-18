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
    //TODO : ajax request-response for categories
    //getAjaxResponseForUrl("/getCategories", box.items);

    box.addEventListener("value-changed", function (item) {
        var values = document.querySelector("#selected-category");
        createSelectedOption(item.detail.value, values);
        box.value = "";
    })
}

function fillRelations() {
    var box = document.querySelector('#relations');

    //TODO : ajax request-response for relations
    getAjaxResponseForUrl("/getRelations", box.items);

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
            form = JSON.parse(request.responseText);
        }

        if (request.readyState == 4) {
            request.open("GET", url, true);
            request.send();
            setTimeout(function(){}, 1000000);
        }
    };

    request.open("GET", url, true);
    request.send();

}