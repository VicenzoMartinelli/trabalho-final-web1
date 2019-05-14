"use strict";

function remove(id, url) {
    let trat = () => {
        let destino = url + '/' + id;
        $.ajax({
            type: 'DELETE',
            url: destino,
            success: function () {
                $('#row_' + id).remove();
                swal(
                    'Removido',
                    'Registro removido com sucesso!',
                    'success'
                );
            },
            error: () => {
                swal(
                    'Errow',
                    'Falha ao remover registro!',
                    'error'
                );
            }
        })

    };

    swal({
        title: 'Confirma a remoção do registro?',
        text: 'Esta ação não poderá ser desfeita!',
        type: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#DD6B55',
        cancelButtonText: 'Cancelar',
        confirmButtonText: 'Remover',
        closeOnConfirm: false
    }, trat);

}

function save(urlDestino) {
    $.ajax({
        type: $('#frm').attr('method'),
        url: $('#frm').attr('action'),
        data: $('#frm').serialize(),
        success: (e) => {
            swal({
                title: 'Salvo!',
                text: 'Registro Salvo com Sucesso!',
                type: 'success'
            }, () => {
                window.location = urlDestino;
            });

        },
        error: (e) => {
            swal('Errou!', 'Falha ao salvaro registro!', 'error');
        }
    })
}

function edit(url) {
    $.get(
        url,
        function (entity, status) {
            $("#id").val(entity.id);
            $("#name").val(entity.name);
        }
    );

    $("#modal-form").modal();
}