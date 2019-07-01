$(function () {
    $(document).scroll(function () {
        let $nav = $(".navbar-fixed-top");
        $nav.toggleClass('scrolled', $(this).scrollTop() > $nav.height() * 5);

        let $btn = $(".floating-button");
        $btn.toggleClass('scrolled', $(this).scrollTop() > $nav.height() * 5);

        let navItems = $(".nav-link");
        navItems.toggleClass('scrolled', $(this).scrollTop() > $nav.height() * 5);
    });

    $('#btnEntrar').on('click', () => {
        $('#signup-tab').on('click', () => {
            let mLogin = $('.modal-dialog');
            if (!mLogin.hasClass('signup')) {
                mLogin.toggleClass('signup');
            }
        });
        $('#login-tab').on('click', () => {
            let mLogin = $('.modal-dialog');
            if (mLogin.hasClass('signup')) {
                mLogin.removeClass('signup');
            }
        });

        $('#txtFrete').mask('00000-000');
    });

    const {href} = window.location;
    const notificationType = new URL(href).searchParams.get("n");
    const msg = new URL(href).searchParams.get("m");

    if (msg && notificationType) {
        swal({
            title: 'Atenção',
            text: base64.decode(msg),
            icon: notificationType == 0 ? 'success' : 'error',
            timer: 1500,
            buttons: false
        });
    }
    history.pushState({}, null, window.location.toString().split('?')[0]);
});

function submitForm(element) {
    $(element).parents('form').submit();
}

function openSearch() {
    let lstClass = document.getElementsByClassName('container-search')[0].classList;
    let cName = 'searching';
    if (lstClass.contains(cName)) {
        lstClass.remove(cName);
    } else
        lstClass.add(cName);
}

function onEnter(element) {
    debugger;
    if ((element.value !== undefined && element.value.length >= 0) || $(this).attr('placeholder') !== null) {
        if (element.parentNode.querySelector("label"))
            element.parentNode.querySelector("label").classList.add("active");
    }
}

function onExit(element) {
    if ((element.value !== undefined && element.value.length == 0) || $(this).attr('placeholder') === null) {
        element.parentNode.querySelector("label").classList.remove("active");
    }
}