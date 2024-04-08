fetchUserDeliveries()

function fetchUserDeliveries() {
    var url = "/delivery";
    fetch(url)
        .then(function (response) {
            return response.json();
        })
        .then(function (jsonData) {
            console.log(jsonData);
            showdeliveries(jsonData);
        });
}

function showdeliveries(jsonData) {
    var deliveryDiv = document.querySelector('#deliveryDiv');
    for (var i = 0; i < jsonData.length; i++) {
        var div = document.createElement('div');
        div.setAttribute('class', 'delivery-box', 'd-flex justify-content-center');
        div.setAttribute('id', `div${i}`);
        div.innerHTML = `<h5>${jsonData[i].deliveryAddress}</h5><h6>${jsonData[i].paymentType}</h6><input type='radio' id="input${i}" class='radio-input' name="delivery" >`

        deliveryDiv.appendChild(div);
    }
}

function addNewAddress() {
    document.querySelector("#btnRow").style.visibility = "hidden";
    var div = document.querySelector('#new-address');
    div.innerHTML = `
    <form id="newAddressForm">
        <div class="form-group">
          <label for="inputCountry">Ország:</label>
          <input type="text" class="form-control" id="inputCountry" placeholder="**Ország**">
        </div>
        <div class="form-group">
          <label for="inputCity">Város:</label>
          <input type="text" class="form-control" id="inputCity" placeholder="**Város**">
        </div>
        <div class="form-group">
          <label for="inputZIP">Irányítószám:</label>
          <input type="text" class="form-control" id="inputZIP" placeholder="**Irányítószám**">
        </div>
        <div class="form-group">
          <label for="inputStreet">Utca, házszám:</label>
          <input type="text" class="form-control" id="inputStreet" placeholder="**Utca, házszám**">
        </div>
        <div id="paymentDiv">
          <div class="form-check">
            <input class="form-check-input" type="radio" name="payment" id="payment1" value="Utánvétes fizetés" checked>
            <label class="form-check-label" for="payment1">Utánvétes fizetés</label>
          </div>
          <div class="form-check">
            <input class="form-check-input" type="radio" name="payment" id="payment2" value="Online bankkártyás fizetés">
            <label class="form-check-label" for="payment2">Online bankkártyás fizetés</label>
          </div>
        </div>
        <button type="submit" class="btn btn-primary" onclick="saveAddress()">Mentés</button>
        <button type="submit" class="btn btn-primary" onclick="cancel()">Mégsem</button>
    </form>`;
}

function cancel() {
    document.querySelector("#newAddressForm").remove();
    document.querySelector("#btnRow").style.visibility = "visible";
}

function saveAddress() {
    var paymentDiv = document.querySelector('#paymentDiv');
    var paymentType = "";
    for (var i = 0; i < paymentDiv.children.length; i++) {
        if (paymentDiv.children[i].children[0].checked == true) {
            paymentType = paymentDiv.children[i].children[0].value;
        }
    }

    var request = {
        "deliveryAddress": document.querySelector('#inputCountry').value + " " + document.querySelector('#inputZIP').value + " " +
            document.querySelector('#inputCity').value + " " + document.querySelector('#inputStreet').value,
        "paymentType": paymentType
    }
    console.log(request);
    fetch("/delivery", {
        method: "POST",
        body: JSON.stringify(request),
        headers: {
            "Content-type": "application/json"
        }
    })
        .then(function (response) {
            console.log(response);
            return response.json();
        }).then(function (jsonData) {
            console.log(jsonData.status);
        })
}

function addToOrders(address, card) {
    console.log(address);
    var request = {
        "deliveryAddress": address
    }

    var url = "/myorders";
    fetch(url, {
        method: "POST",
        body: JSON.stringify(request),
        headers: {
            "Content-type": "application/json"
        }
    })
        .then(function (response) {
            return response.json();
        }).then(function (jsonData) {
            console.log(jsonData.status);
            console.log(jsonData.message);
            if (jsonData.status == 'OK') {
                if(card == true){
                    $('#exampleModal').modal('show');
                    setTimeout(function () {
                        $("#exampleModal").modal("hide");
                        window.location.href = "/succesfulorder.html";
                    }, 3000); 
                } else {
                    window.location.href = "/succesfulorder.html";
                }
            }
        })
}

function checkAddress() {
    var deliveryDiv = document.querySelector('#deliveryDiv');
    for (var i = 0; i < deliveryDiv.children.length; i++) {
        if (deliveryDiv.children[i].children[2].checked == true) {
            if(deliveryDiv.children[i].children[1].innerHTML == "Online bankkártyás fizetés"){
                addToOrders(deliveryDiv.children[i].children[0].innerHTML, true);
                return;
            } else {
                addToOrders(deliveryDiv.children[i].children[0].innerHTML, false);
                return;
            }
        }
    }
}


function checkoutRadios() {
    var deliveryDiv = document.querySelector('#deliveryDiv');
    for (var i = 0; i < deliveryDiv.children.length; i++) {
        deliveryDiv.children[i].children[2].checked = false;
    }
}