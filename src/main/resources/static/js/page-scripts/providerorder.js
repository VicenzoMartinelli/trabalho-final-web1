"use strict";

$(document).ready(function () {

});

function saveProviderOrder() {
    if (!($('#frm').valid())) {
        swal({
            title: 'Erro!',
            text: 'Verifique as informações do seu formulário!',
            type: 'error',
            showConfirmButton: false,
            timer: 1500
        });
        return;
    }

    $.ajax({
        type: $('#frm').attr('method'),
        url: $('#frm').attr('action'),
        data: getCleanFormSerialized('#frm'),
        success: (e) => {
            swal({
                title: 'Salvo!',
                text: 'Registro Salvo com Sucesso!',
                type: 'success'
            }, () => {
                window.location = '/providerorder';
            });

        },
        error: (e) => {
            swal('Errou!', 'Falha ao salvaro registro!', 'error');
        }
    })
}