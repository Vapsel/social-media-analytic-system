var counter = 0;
var categories = new Set();
var relations = new Set();

function getRelationArray() {
    var array = [];
    relations.forEach(function(entry){
        array.push(entry);
    });
    return array;
}
function btnAddAction() {
    var key = document.querySelector("#keyForm").value;

    var newCategories = [], existingCategories = [];
    setCategories(newCategories, existingCategories);

    var relationsArray = getRelationArray();

    var summary = {
        key: key,
        relations: relationsArray,
        existingCategories: existingCategories,
        newCategories: newCategories
    };

    sendSummaryViaAjax(summary);
}

function createSelectedOption(item, parent) {
    var name = item.label;
    name = name.trim();
    if (name == null || name == "" || name == undefined) {
        return;
    }

    var set = null ;

    if (parent.id === "selected-category") {
        set = categories;
    } else if (parent.id === "selected-relations") {
        set = relations;
    }else{
        return;
    }

    if (!set.has(item)) {
        ++counter;
        var htmlTemplate = "\<div class=\"option\" id=\"option-" + counter + "\"\>" +
            "\<span class=\"option-name\" id=\"option-text-" + counter + "\"\>" + name + "\</span>" +
            "\<img src=\"public/img/close-small.png\" class=\"option-close-image\" onclick=\"deleteSelectedOption("+ counter + "," + "\'" + parent.id.trim() + "\'" + ")\"\>" +
            "\</div>";

        set.add(item);

        parent.innerHTML += htmlTemplate;
    }
}

function deleteSelectedOption(nb, parentId) {
    var text = document.querySelector("#option-text-" + nb).innerHTML;
    text = text.trim();

    var set;
    parentId = parentId.trim();
    if (parentId === "selected-category") {
        set = categories;
    } else if (parentId === "selected-relations") {
        set = relations;
    }else{
        return;
    }

    set.forEach(function(entry){
        if(entry.label === text){
            set.delete(entry);
        }
    });

    document.querySelector("#option-" + nb).remove();
}

function setCategories(newCategories, existingCategories) {
    categories.forEach(function (entry) {
       if(entry.value){
            existingCategories.push(entry);
       }else{
           newCategories.push(entry);
       }
    });
}

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

function sendSummaryViaAjax(summary){
    var body = JSON.stringify(summary);
    var request = getAjaxRequest();

    request.open("POST", "/editor", true);
    request.send(body);

    location.reload();
}