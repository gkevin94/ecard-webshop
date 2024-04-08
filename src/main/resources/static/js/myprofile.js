window.onload = function () {
    fetchUser();
}

var id = 0;
var name = "";

window.onkeydown = e => {
    if (e.keyCode == 13) { document.forms[0].submit(); }
}

function fetchUser() {
  fetch("/user")
    .then(function(response) {
      return response.json();
    })
    .then(function(jsonData) {
      id = jsonData.id;
      name = jsonData.name;
    });
}

function handleFormSubmit() {

    var div = document.querySelector('#response-box');
    var form = document.forms["form-register"];
    if(form.confirm.value!=form.password.value) {
        div.setAttribute("class", "alert alert-danger");
        div.innerHTML = "A két jelszó nem egyezik!";
        return;
        }
    var request = {
                    "name": name,
                    "email": "email",
                    "userName": form.username.value,
                    "password": form.password.value,
                    "enabled": 1,
                    "role": "ROLE_USER",
                    "userStatus": "ACTIVE"
                 }
    console.log(request);
    fetch("/users/" + id,{
             method: "POST",
             body: JSON.stringify(request),
             headers:{
             "Content-type": "application/json"
             }
             })
             .then(function(response){
                return response.json();
             })
             .then(function(json) {
             console.log(json);
             if(json.status=="OK") {
                div.setAttribute("class", "alert alert-success")
                div.innerHTML = "Sikeres módosítás! Átirányítunk...";
                setTimeout(function(){
                window.location.href = "/login.html";
                }, 1000);
             } else {
                div.innerHTML = json.message;
                div.setAttribute("class", "alert alert-danger");
//                form.name.focus();
             }});

    return false;
}

(function($){
  //password validator
  var Validate = {
    config: {
      minLen: 8,
      bothCase: true,
      alphNum: true,
      classContainer: '.formContainer',
      classPassword: '.pass1',
      classConfirm: '.pass2',
      classMsgBox:'.msgBox'
    },
    init: function(config){
      $.extend(Validate.config, config);
      this.elPassword = $(this.config.classPassword);
      this.elConfirm = $(this.config.classConfirm);
      this.elMsgBox = $(this.config.classMsgBox);

      var objInput = this.elPassword;
      this.elMsgBox.hide();
      this.elConfirm.on('click', Validate.setFocus);
      $('body').on('focus.password', this.config.classPassword, Validate.validate);
      $('body').on('blur.password', this.config.classPassword, Validate.validate);
      $('body').on('keyup.password', this.config.classPassword, Validate.validate);

    },
    setFocus: function(){
      var container = Validate.config.classContainer,
          msg = $(this).closest(container).find(Validate.config.classMsgBox).text();
      (msg !== 'passed') && $(this).closest(container).find(Validate.config.classPassword).focus();
    },
    showError: function(msg, objInput){
      var container = Validate.config.classContainer,
          msgBox = objInput.closest(container).find('.passwordValidator'),
          objConfirm = objInput.closest(container).find(Validate.config.classConfirm);
      if(msg === 'passed'){
        msgBox.html(msg).delay(500).slideUp();
      }else{
        msgBox.is(':hidden') ? msgBox.html(msg).slideDown() : msgBox.html(msg);
        objConfirm.val('');
      }
      return false
    },
    validate: function(){
      var objInput = $(this),
          regExp;

      if(objInput.val().length < Validate.config.minLen){
          Validate.showError("A jelszónak legalább " + Validate.config.minLen + " karakterből kell állnia ", objInput);
          return false;
        }
        else{

          regExp = /[a-z]/;
          if(Validate.config.bothCase && !regExp.test(objInput.val())){
            Validate.showError("A jelszónak tartalmaznia kell legalább egy kisbetűt (a-z)", objInput);
            return false;
          }

          regExp = /[A-Z]/;
          if(Validate.config.bothCase && !regExp.test(objInput.val())){
            Validate.showError("A jelszónak tartalmaznia kell legalább egy nagybetűt (A-Z)", objInput);
            return false;
          }

          regExp = /[0-9]/;
          if(Validate.config.alphNum && !regExp.test(objInput.val())){
            Validate.showError("A jelszónak tartalmaznia kell legalább egy számot (0-9)", objInput);
            return false;
          }

          Validate.showError("passed", objInput)
          return false;
        }
    }
  }

  Validate.init({
    classPassword: '.textPassword',
    classConfirm: '.textConfirmPassword',
    classMsgBox:'.passwordValidator'
  })
})(jQuery);