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
    box.loading = true;

    getAjaxResponseForUrl("/getCategories", box);
    box.addEventListener("selected-item-changed", function () {
        var values = document.querySelector("#selected-category");
        createSelectedOption(box.selectedItem, values);
        box.value = "";
    })
}

function fillRelations() {
    var box = document.querySelector('#relations');
    box.loading = true;


    $("#relations input").on("input", function () {
        if ($(this).val().length > 2) {
            getAjaxResponseForUrl("/getRelations", box, $(this).val());
            box.loading = false;
        }
    });

    box.addEventListener("selected-item-changed", function (item) {
        var values = document.querySelector("#selected-relations");
        createSelectedOption(box.selectedItem, values);
        box.value = "";
    })

}

function getAjaxResponseForUrl(url, form, body) {
    var request = getAjaxRequest();

    request.onreadystatechange = function () {
        if (request.readyState == 3 && request.status == 200) {
            var array = [];
            array = JSON.parse(request.response);
            for (var i = 0; i < array.length; ++i) {
                array[i].label = array[i].name;
                array[i].value = array[i].id;
            }
            form.items = array;
            form.loading = false;
        }
    };

    if (!body || body === "") {
        request.open("GET", url, true);
        request.send();
    } else {
        request.open("POST", url, true);
        request.send(body);
    }
}