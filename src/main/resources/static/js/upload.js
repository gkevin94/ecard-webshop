window.addEventListener('load', init);

var productId = parseInt(window.location.href.split("=")[1]);
var imageContainer = document.querySelector("#image-container");

fetchImage(productId, 0);

function init() {
    let uploadButton = document.getElementById('uploadButton');
    uploadButton.addEventListener('click', startUpload);
    document.querySelector("#fileToUpload").addEventListener("change", function(){
        document.querySelector("[for='"+ this.id +"']").innerText = this.files[0].name;
    })
}

function startUpload() {
    let file = document.getElementById('fileToUpload').files[0];
    const formData = new FormData();
    formData.append('file', file);
    formData.append('productId', productId);

    fetch('/image', {
        method: 'POST',
        body: formData
    }).then(response => {
        return response.text();
    }).then(message => {
        alert(message);
        document.getElementById('uploadForm').reset();
        let messageNode = document.getElementById('messageP');
        fetchImage(productId, 0);
    });
}

function fetchImage(productId, offset) {
    if(offset == 0)
        imageContainer.innerHTML = "";
    fetch('/image/' + productId + '/' + offset)
    .then(function(response) {
      if(response.status == 200) {
        imageContainer.innerHTML += `<div class="col-md-4">
                                          <div class="card mb-4 box-shadow">
                                              <img class="card-img-top" id='img-${offset}'
                                                   alt="card image">
                                              <div class="card-body">
                                                  <p class="card-text">${response.headers.get("Content-Disposition").split("=")[1]}</p>
                                                  <small class="text-muted" id="file-type-${offset}">${response.headers.get("Content-Type")}</small>
                                                  <p><small class="text-muted" id="file-size-${offset}"></small></p>
                                                  <div class="d-flex justify-content-between align-items-center">
                                                      <div class="btn-group">
                                                          <button type="button" class="btn btn-lm btn-outline-secondary" onclick="deleteImage(${productId}, ${offset})">Törlés</button>
                                                      </div>
                                                  </div>
                                              </div>
                                          </div>
                                      </div>`;

        return response.blob();
      }
    })
    .then(function(blob) {
        if(blob) {
            document.querySelector(`#file-size-${offset}`).innerHTML = Math.round(blob.size/1024 * 100) / 100 +" kB";
            document.querySelector(`#img-${offset}`).src = URL.createObjectURL(blob);
            fetchImage(productId, offset+1);
        }
    });
}

function deleteImage(productId, offset) {
    fetch('/image/' + productId + '/' + offset, {
      method: "DELETE"
    })
    .then(function(response){
      fetchImage(productId, 0);
      return response.text();
    })
    .then(function(message){
      console.log(message);
    });
}