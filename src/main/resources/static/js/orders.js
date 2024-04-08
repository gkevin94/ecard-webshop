window.onload = function () {
    fetchOrders();
}

function fetchOrders() {
    fetch("/orders")
        .then(function (response) {
            return response.json();
        })
        .then(function (jsonData) {
            checkOrderStatus(jsonData);
        });
}

var selector = document.querySelector("#checkStatus");
selector.addEventListener('change', function (event) {
    if (selector.checked) {
        fetchOrders();
    } else {
        fetchOrders();
    }
});

function checkOrderStatus(jsonData) {
    var checkStatus = document.querySelector("#checkStatus").checked;
    if (checkStatus) {
        showTable(jsonData);
    } else {
        showTable(jsonData.filter(e => e.orderStatus == "ACTIVE"));
    }
}

function showTable(jsonData) {
    console.log(jsonData);
    var table = document.querySelector("#orders-table");
    table.innerHTML = "";
    for (var i = 0; i < jsonData.length; i++) {
        var tr = document.createElement("tr");
        tr["raw-data"] = jsonData[i];

        var idTd = document.createElement("td");
        idTd.innerHTML = jsonData[i].userId;
        var idTdId = 'idTd' + i;
        idTd.setAttribute('id', idTdId);
        tr.appendChild(idTd);

        var purchaseDateTd = document.createElement("td");
        purchaseDateTd.innerHTML = jsonData[i].purchaseDate;
        var purchaseDateTdId = 'purchaseDateTd' + i;
        purchaseDateTd.setAttribute('id', purchaseDateTdId);
        tr.appendChild(purchaseDateTd);

        var userId = document.createElement("td");
        userId.innerHTML = jsonData[i].sumQuantity;
        var userTdId = 'userId' + i;
        userId.setAttribute('id', userTdId);
        tr.appendChild(userId);

        var total = document.createElement("td");
        total.innerHTML = jsonData[i].total;
        var totalTdId = 'total' + i;
        total.setAttribute('id', totalTdId);
        tr.appendChild(total);

        var orderStatusTd = document.createElement("td");
        if(jsonData[i].orderStatus == 'ACTIVE'){
            orderStatusTd.innerHTML = "aktív";
        } else if(jsonData[i].orderStatus == 'SHIPPED'){
            orderStatusTd.innerHTML = "kiszállítva";
        } else {
            orderStatusTd.innerHTML = "törölt";
        }
        var orderStatusTdId = 'manTd' + i;
        orderStatusTd.setAttribute('id', orderStatusTdId);
        tr.appendChild(orderStatusTd);
console.log(jsonData[i].delivery);
        var deliveryAddressTd = document.createElement("td");
        deliveryAddressTd.innerHTML = jsonData[i].delivery.deliveryAddress;
        var deliveryAddressTdId = 'delTd' + i;
        deliveryAddressTd.setAttribute('id', deliveryAddressTdId);
        tr.appendChild(deliveryAddressTd);

        if(jsonData[i].orderStatus == "ACTIVE"){
          var editButtonTd = document.createElement("td");
          var editButton = document.createElement("button");
          var editButtonId = 'editbutton' + i;
          editButton.setAttribute('id', editButtonId);
          editButton.setAttribute('class', 'btn');
          editButton.setAttribute('onclick', `editTds(${jsonData[i].id})`);
          editButtonTd.appendChild(editButton);

          var saveButton = document.createElement("button");
          var saveButtonId = 'savebutton' + i;
          saveButton.setAttribute('id', saveButtonId);
          saveButton.setAttribute('class', 'btn');
          saveButton.setAttribute('onclick', `saveTds(${i})`);
          saveButton.style.display = 'none';
          editButtonTd.appendChild(saveButton);

          var shippedButtonTd = document.createElement("td");
          var shippedButton = document.createElement("button");
          var shippedButtonId = "shippedButton" + i;
          shippedButton.setAttribute("id", shippedButtonId);
          shippedButton.setAttribute("class", "btn");
          shippedButton.setAttribute("onclick", `changeStatusToShipped(${i})`);
          shippedButtonTd.appendChild(shippedButton);

          editButton.innerHTML = `<i class="fas fa-edit"></i> Szerkesztés`;
          saveButton.innerHTML = `<i class="fa fa-save"></i> Mentés`;
          shippedButton.innerHTML = `<i class="fas fa-truck"></i> Kiszállítva`;

          var deleteButtonTd = document.createElement("td");
          var deleteButton = document.createElement("button");
          var deleteButtonId = 'deletebutton' + i;
          deleteButton.setAttribute('id', deleteButtonId);
          deleteButton.setAttribute('class', 'btn');
          deleteButton.setAttribute('onclick', `deleteOrder(${i})`);
          deleteButton['raw-data'] = jsonData[i];
          deleteButtonTd.appendChild(deleteButton);
          deleteButton.innerHTML = `<i class="fas fa-trash-alt"></i> Törlés`;

        } else {
          var editButtonTd = document.createElement("td");
          var saveButton = document.createElement("button");
          var deleteButtonTd = document.createElement("td");
          var shippedButtonTd = document.createElement("td");
        }

        tr.appendChild(deleteButtonTd);
        tr.appendChild(editButtonTd);
        tr.appendChild(shippedButtonTd);
        table.appendChild(tr);
    }
}

function editTds(num) {
    window.location.href = `editorder.html?id=${num}`;
}


function deleteOrder(num) {
    var id = document.getElementById(`deletebutton${num}`).parentElement.parentElement['raw-data'].id;
    if (!confirm("Biztos, hogy törli a megrendelést?")) {
        return;
    }
    fetch("/orders/" + id, {
            method: "POST",
        })
        .then(function (response) {
            document.getElementById("message-div").setAttribute("class", "alert alert-success");
            document.querySelector("#message-div").innerHTML = "Törölve"
            fetchOrders();
        });
}

function changeStatusToShipped(num) {
    var id = document.getElementById(`deletebutton${num}`).parentElement.parentElement['raw-data'].id;
    if (!confirm("Kiszállítottra állítja a megrendelés állapotát?")) {
        return;
    }

    fetch("/orders/" + id + "/shipped", {
            method: "POST",
        })
        .then(function (response) {
            document.getElementById("message-div").setAttribute("class", "alert alert-success");
            document.querySelector("#message-div").innerHTML = "Státusz módosítva erre: kiszállítva"
            fetchOrders();
        });

}