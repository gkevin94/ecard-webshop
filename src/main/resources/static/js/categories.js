fetchCategories();

function fetchCategories() {
    fetch("/categories")
        .then(function (response) {
            return response.json();
        })
        .then(function (jsonData) {
            showTable(jsonData);
        });
}

function showTable(jsonData) {
var table = document.querySelector("#admincategories-table");
table.innerHTML = "";
for (var i = 0; i < jsonData.length; i++) {
    var tr = document.createElement("tr");
    tr["raw-data"] = jsonData[i];

    var ordinalTd = document.createElement("td");
    ordinalTd.innerHTML = jsonData[i].ordinal;
    var ordinalTdId = 'ordinalTd' + i;
    ordinalTd.setAttribute('id', ordinalTdId);
    tr.appendChild(ordinalTd);

    var nameTd = document.createElement("td");
    nameTd.innerHTML = jsonData[i].name;
    var nameTdId = 'nameTd' + i;
    nameTd.setAttribute('id', nameTdId);
    tr.appendChild(nameTd);

    var editButtonTd = document.createElement("td");
    var editButton = document.createElement("button");
    var editButtonId = 'editbutton' + i;
    editButton.setAttribute('id', editButtonId);
    editButton.setAttribute('class', 'btn');
    editButton.setAttribute('onclick', `editTds(${i})`);
    editButtonTd.appendChild(editButton);

    var saveButton = document.createElement("button");
    var saveButtonId = 'savebutton' + i;
    saveButton.setAttribute('id', saveButtonId);
    saveButton.setAttribute('class', 'btn');
    saveButton.setAttribute('onclick', `saveTds(${i})`);
    saveButton.style.display = 'none';
    editButtonTd.appendChild(saveButton);

    var deleteButtonTd = document.createElement("td");
    var deleteButton = document.createElement("button");
    var deleteButtonId = 'deletebutton' + i;
    deleteButton.setAttribute('id', deleteButtonId);
    deleteButton.setAttribute('class', 'btn');
    deleteButton.setAttribute('onclick', `deleteCategory(${i})`);
    deleteButton['raw-data'] = jsonData[i];
    deleteButtonTd.appendChild(deleteButton);

    editButton.innerHTML = `<i class="fas fa-edit"></i>Szerkesztés`;
    saveButton.innerHTML = `<i class="fa fa-save"></i>Mentés`;
    deleteButton.innerHTML = `<i class="fas fa-trash-alt"></i>Törlés`;

    tr.appendChild(editButtonTd);
    tr.appendChild(deleteButtonTd);

    table.appendChild(tr);
}

var createButton = document.getElementById('createButton');
createButton.setAttribute('onclick', `showNewRow(${jsonData.length})`);

}

function editTds(num){

    var name = document.getElementById(`nameTd${num}`);
    var ordinal = document.getElementById(`ordinalTd${num}`);

    var nameData = name.innerHTML;
    var ordinalData = ordinal.innerHTML;

    name.innerHTML = `<input id="nameInput${num}" type='text' minLength='1' maxLength='255' class='input-box'  value = '${nameData}' required>`
    ordinal.innerHTML = `<input id="ordinalInput${num}" type='text' minLength='1' maxLength='255' class='input-box'  value='${ordinalData}' required>`

    var edit = document.getElementById(`editbutton${num}`);
    edit.style.display = 'none';
    var save = document.getElementById(`savebutton${num}`);
    save.style.display = 'inline';
}

function saveTds(num){

    var id = document.getElementById(`savebutton${num}`).parentElement.parentElement['raw-data'].id;

    var name = document.getElementById(`nameInput${num}`).value;
    var ordinal = document.getElementById(`ordinalInput${num}`).value;

    var request = {
        "id": id,
        "name": name,
        "ordinal": ordinal
    }

    fetch("/categories/" + id, {
            method: "POST",
            body: JSON.stringify(request),
            headers: {
                "Content-type": "application/json"
            }
        })
        .then(function (response) {
            return response.json();
        }).
    then(function (jsonData) {
        if (jsonData.status == 'OK') {

           document.getElementById(`nameTd${num}`).innerHTML = name;
           document.getElementById(`ordinalTd${num}`).innerHTML = ordinal;

           fetchCategories();
           document.getElementById("message-div").setAttribute("class", "alert alert-success");
        } else {
            document.getElementById("message-div").setAttribute("class", "alert alert-danger");
        }
        document.getElementById("message-div").innerHTML = jsonData.message;
    });
    return false;
}

function showNewRow(length){
    var num = length + 1;
    var table = document.querySelector("#admincategories-table");
    var tr = document.createElement('tr');

    var ordinalTd = document.createElement('td');
    ordinalTd.setAttribute('id', `ordinalTd${num}`);
    var nameTd = document.createElement('td');
    nameTd.setAttribute('id', `nameTd${num}`);

    var saveButtonTd = document.createElement('td');
    var saveButton = document.createElement('button');
    saveButton.setAttribute('class', 'btn');
    saveButton.setAttribute('onclick', `addNewCategory(${num})`);
    var deleteButtonTd = document.createElement('td');
    var deleteButton = document.createElement('button');
    deleteButton.setAttribute('onclick', 'deleteNewRow()');
    deleteButton.setAttribute('class', 'btn');
    saveButtonTd.appendChild(saveButton);
    deleteButtonTd.appendChild(deleteButton);

    nameTd.innerHTML = `<input id="nameInputNew${num}" type='text' minLength='1' maxLength='255' class='input-box' required>`
    ordinalTd.innerHTML = `<input id="ordinalInputNew${num}" type='text' minLength='1' maxLength='255' class='input-box' required>`

    saveButton.innerHTML = `<i class="fa fa-save"></i>Mentés`;
    deleteButton.innerHTML = `<i class="fas fa-trash-alt"></i>Törlés`;

    tr.appendChild(ordinalTd);
    tr.appendChild(nameTd);
    tr.appendChild(saveButtonTd); tr.appendChild(deleteButtonTd);
    table.appendChild(tr);

}

function addNewCategory(num){

    var name = document.getElementById(`nameInputNew${num}`).value;
    var ordinal = document.getElementById(`ordinalInputNew${num}`).value;

    var request = {
        "name": name,
        "ordinal": ordinal
    }

    fetch("/categories", {
            method: "POST",
            body: JSON.stringify(request),
            headers: {
                "Content-type": "application/json"
            }
        })
        .then(function (response) {
            return response.json();
        })
        .then(function (jsonData) {
            if (jsonData.status == "OK") {
                document.getElementById(`nameTd${num}`).innerHTML = name;
                document.getElementById(`ordinalTd${num}`).innerHTML = ordinal;
                fetchCategories();
                document.getElementById("message-div").setAttribute("class", "alert alert-success");
            } else {
                document.getElementById("message-div").setAttribute("class", "alert alert-danger");
            }
            document.getElementById("message-div").innerHTML = jsonData.message;
        });
    return false;
}

function deleteNewRow(){
    var table = document.querySelector("#admincategories-table");
    table.removeChild(table.lastChild);
}

function deleteCategory(num){

    var id = document.getElementById(`deletebutton${num}`).parentElement.parentElement['raw-data'].id;

    if (!confirm("Biztos, hogy törli a kategóriát?")) {
        return;
    }

    fetch("/categories/" + id, {
            method: "DELETE",
    })
    .then(function (response) {
        return response.json();
    })
    .then(function (jsonData) {
        if(jsonData.status == "OK")
            document.getElementById("message-div").setAttribute("class", "alert alert-success");
        else
            document.getElementById("message-div").setAttribute("class", "alert alert-danger");
        document.querySelector("#message-div").innerHTML = jsonData.message;
        fetchCategories();
    });
}

