getNavbar();
fetchCategories();
basketRefresh();

function getNavbar() {
    $.getJSON("/role", json => {

        let role = json.role || 'VISITOR';
        let name = json.userName;

        var nav = document.querySelector('#basket');
        var parent = nav.parentNode;
        parent.style.backgroundColor = 'black';
        var helper = document.createElement('div');

        if (role == 'ROLE_ADMIN') {
            helper.innerHTML +=
                `<div class="btn-group" style="margin-right: 10px;">
                    <button class="btn dropdown-toggle text-dark" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        Admin menü
                      </button>
                <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                    <a class="dropdown-item" href="/adminproducts.html">Termékek adminisztrációja</a>
                    <a class="dropdown-item" href="/categories.html">Kategóriák adminisztrációja</a>
                    <a class="dropdown-item" href="/users.html">Felhasználók adminisztrációja</a>
                    <a class="dropdown-item" href="/orders.html">Rendelések adminisztrációja</a>
                    <div class="dropdown-divider"></div>
                    <a class="dropdown-item" href="/dashboard.html">Dashboard</a>
                    <a class="dropdown-item" href="/reports.html">Riportok</a>
                </div>
            </div>`
        }

        if (role == 'VISITOR') {
            helper.innerHTML +=
                `<li class="nav-item active" id="sign-up">
                <a class="nav-link" href="/register.html">Regisztráció</a>
            </li>
            <li class="nav-item active" id="login">
                <a class="nav-link" href="/login.html">Bejelentkezés</a>
            </li>`
        } else {
            helper.innerHTML +=
                `<div class="btn-group">
                    <button type="button" class="btn text-dark dropdown-toggle dropdown-toggle-split" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        Üdvözlünk ${name}!
                    </button>
                <div class="dropdown-menu">
                    <a class="dropdown-item" href="/myorders.html">Rendeléseim</a>
                    <a class="dropdown-item" href="/myprofile.html">Saját adatlap</a>
                    <div class="dropdown-divider"></div>
                    <a class="dropdown-item" href="/logout">Kijelentkezés</a>
                </div>
            </div>`
        }

        while (helper.firstChild) {
            parent.insertBefore(helper.firstChild, nav);
        }
    });
}

function basketRefresh() {
    $.getJSON("/basket", json => {
    var sum = 0;
    for(var i = 0; i<json.length; i++) {
        if(json[i].pieces)
            sum += json[i].pieces;
    }
    document.querySelector('#cartCount').innerHTML = sum;
    });
}

function fetchCategories() {
    $.getJSON("/categories", json => {
        var categoryDropdown = document.querySelector("#category-select");
        var categoryNavbar = document.querySelector('#categories');

        for (let i = 0; i<json.length; i++) {
            if(categoryNavbar)
                categoryNavbar.innerHTML += `<a class="nav-link text-light" href="/index.html?category=${json[i].name}">${json[i].name}</a>`;
            if(categoryDropdown)
                categoryDropdown.innerHTML += `<option value="${json[i].name}">${json[i].name}</option>`;
        }
        if(categoryDropdown)
            categoryDropdown.value = new URL(document.location).searchParams.get("category") || 'all';
    });
}