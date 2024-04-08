var id = (new URL(document.location)).searchParams.get('id');

window.onload = function () {
    fetchOrders();
}

function fetchOrders() {
    fetch("/orders/" + id)
        .then(function (response) {
            return response.json();
        })
        .then(function (jsonData) {
            console.log(jsonData);
            showTable(jsonData);
        });
}

function showTable(jsonData) {
    var table = document.querySelector("#order-table");
    table.innerHTML = "";
    for (var i = 0; i < jsonData.length; i++) {
        var tr = document.createElement("tr");
        tr["raw-data"] = jsonData[i];

        var idTd = document.createElement("td");
        idTd.innerHTML = jsonData[i].orderingName;
        var idTdId = 'idTd' + i;
        idTd.setAttribute('id', idTdId);
        tr.appendChild(idTd);

        var orderingPriceTd = document.createElement("td");
        orderingPriceTd.innerHTML = jsonData[i].orderingPrice;
        var orderingPriceTdId = 'priceTd' + i;
        orderingPriceTd.setAttribute('id', orderingPriceTdId);
        tr.appendChild(orderingPriceTd);

        var pieceTd = document.createElement("td");
        pieceTd.innerHTML = jsonData[i].pieces;
        var pieceTdId = 'pieceTd' + i;
        pieceTd.setAttribute('id', pieceTdId);
        tr.appendChild(pieceTd);

        var editButtonTd = document.createElement("td");
        var editButton = document.createElement("button");
        var editButtonId = 'editbuttonOp' + i;
        editButton.setAttribute('id', editButtonId);
        editButton.setAttribute('class', 'btn');
        editButton.setAttribute('onclick', `editPiece(${i})`);
        editButtonTd.appendChild(editButton);

        var saveButton = document.createElement("button");
        var saveButtonId = 'savebuttonOp' + i;
        saveButton.setAttribute('id', saveButtonId);
        saveButton.setAttribute('class', 'btn');
        saveButton.setAttribute('onclick', `savePiece(${i})`);
        saveButton.style.display = 'none';
        editButtonTd.appendChild(saveButton);

        var deleteButtonTd = document.createElement("td");
        var deleteButton = document.createElement("button");
        var deleteButtonId = 'deletebuttonOp' + i;
        deleteButton.setAttribute('id', deleteButtonId);
        deleteButton.setAttribute('class', 'btn');
        deleteButton.setAttribute('onclick', `deleteItem(${i})`);
        deleteButton['raw-data'] = jsonData[i];
        deleteButtonTd.appendChild(deleteButton);

        editButton.innerHTML = `<i class="fas fa-edit"></i>Szerkesztés`;
        saveButton.innerHTML = `<i class="fa fa-save"></i>Mentés`;
        deleteButton.innerHTML = `<i class="fas fa-trash-alt"></i>Törlés`;

        tr.appendChild(editButtonTd);
        tr.appendChild(deleteButtonTd);
        table.appendChild(tr);
    }
}


function editPiece(num){
    var piece = document.getElementById(`pieceTd${num}`);
    var pieceData = piece.innerHTML;

    piece.innerHTML = `<input id="inputQuantityOp${num}" type="number" step="1" value= '${pieceData}' style="width:40px;">`

    var edit = document.getElementById(`editbuttonOp${num}`);
    edit.style.display = 'none';
    var save = document.getElementById(`savebuttonOp${num}`);
    save.style.display = 'inline';
}

function savePiece(num){
    var productId = document.getElementById(`editbuttonOp${num}`).parentElement.parentElement['raw-data'].productId;
    var piece = document.getElementById(`inputQuantityOp${num}`).value;

    var request = {
        "pieces": piece,
        "productId": productId
    }

    fetch("/orders/piece", {
            method: "POST",
            body: JSON.stringify(request),
            headers: {
                "Content-type": "application/json"
            }
        })
        .then(function (response) {
            return response;
        })
        .then(function (jsonData) {
            if (jsonData.ok == true) {

            document.getElementById(`pieceTd${num}`).innerHTML = piece;
            document.getElementById(`editbuttonOp${num}`).style.display = 'inline';
            document.getElementById(`savebuttonOp${num}`).style.display = 'none';

            }
        });
        return false;
}


function deleteItem(i){
console.log(i);

    var productId = document.getElementById(`deletebuttonOp${i}`).parentElement.parentElement['raw-data'].productId;
    fetch('/products/' + productId)
        .then(res => res.json())
        .then(data => {
            removeItemFromOrders(i, data)
        })
}

function removeItemFromOrders(i, product){
    var id = document.getElementById(`deletebuttonOp${i}`).parentElement.parentElement['raw-data'].orderId;

    console.log("/orders/" + id +"/" +  product.address);
    if (!confirm("Biztos, hogy törli a tételt?")) {
        return;
    }
    fetch("/orders/" + id +"/" +  product.address, {
        method: "DELETE"
    })
    .then(response => response.json())
    .then(jsonData => {
        if(jsonData.status=='OK') {
            document.getElementById("message-div").setAttribute("class", "alert alert-success");
            document.querySelector("#message-div").innerHTML = "Törölve a(z) " + product.address + " termék";
            fetchOrders();
        } else {
            document.getElementById("message-div").setAttribute("class", "alert alert-danger");
            document.querySelector("#message-div").innerHTML = "Nem sikerült a törlés";
        }
    });
}