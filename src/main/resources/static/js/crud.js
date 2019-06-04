"use strict";

function remove(id, url) {
    let trat = () => {
        let destino = url + '/' + id;
        $.ajax({
            type: 'DELETE',
            url: destino,
            success: function () {
                swal({
                    title: 'Removido',
                    text: 'Registro removido com sucesso!',
                    type: 'success'
                }, () => {
                    window.location.reload();
                });
            },
            error: () => {
                swal(
                    'Erro',
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

    const serializedForm = getCleanFormSerialized('#frm');

    $.ajax({
        type: $('#frm').attr('method'),
        url: $('#frm').attr('action'),
        data: serializedForm,
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

function getCleanFormSerialized(selector) {
    const rCRLF = /\r?\n/g,
        rsubmitterTypes = /^(?:submit|button|image|reset|file)$/i,
        rsubmittable = /^(?:input|select|textarea|keygen)/i,
        rcheckableType = (/^(?:checkbox|radio)$/i);

    return $(selector)
        .map(function () {
            let elements = jQuery.prop(this, "elements");
            return elements ? jQuery.makeArray(elements) : this;
        })
        .filter(function () {
            let type = this.type;

            return this.name && !jQuery(this).is(":disabled") &&
                rsubmittable.test(this.nodeName) && !rsubmitterTypes.test(type) &&
                (this.checked || !rcheckableType.test(type));
        })
        .map(function (i, elem) {
            let val = jQuery(this).cleanVal();

            return val == null ?
                null :
                jQuery.isArray(val) ?
                    jQuery.map(val, function (val) {
                        return {name: elem.name, value: val.replace(rCRLF, "\r\n")};
                    }) :
                    {name: elem.name, value: val.replace(rCRLF, "\r\n")};
        }).get();
}

function edit(url) {
    onOpenModal();

    $.get(
        url,
        function (entity, status) {
            $("#id").val(entity.id);
            $("#name").val(entity.name);

            onEnter($("#name").get()[0]);
        }
    );

    $("#modal-form").modal();
}

function onOpenModal() {
    $('#frm input, #frm textarea, #frm select').val(null);

    let element = $("#frm").find('input')[0];

    if (element) {
        $(element).focus();
    }
}

$(() => {
    $('[data-toggle="modal"]').click(onOpenModal);
});