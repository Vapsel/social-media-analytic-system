function btnAddAction(){
    var text = document.querySelector("#keyForm").value;
    if(text.trim() === ""){
        return;
    }
    var categories = document.querySelector("#selected-category").innerHTML;
    var relations = document.querySelector("#selected-relations").innerHTML;

    var summary = {
        key: text,
        categories: categories,
        relations: relations
    };

    console.log(summary);
}

var counter = 0;
var categories = new Set();
var relations = new Set();

function createSelectedOption(name, parent) {
    name = name.trim();
    console.log(name);
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

    if (! set.has(name)) {
        ++counter;
        var htmlTemplate = "\<div class=\"option\" id=\"option-" + counter + "\"\>" +
            "\<span class=\"option-name\" id=\"option-text-" + counter + "\"\>" + name + "\</span>" +
            "\<img src=\"public/img/close-small.png\" class=\"option-close-image\" onclick=\"deleteSelectedOption("+ counter + "," + "\'" + parent.id.trim() + "\'" + ")\"\>" +
            "\</div>";

        set.add(name);

        parent.innerHTML += htmlTemplate;
    }
}

function deleteSelectedOption(nb, parentId) {
    console.log(parentId);
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

    set.delete(text);

    document.querySelector("#option-" + nb).remove();
}