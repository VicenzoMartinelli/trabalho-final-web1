"use strict";
$(document).ready(function () {
    $("#frm").validate({
        rules: {
            name: {
                required: true,
                maxlength: 120
            },
            username: {
                required: true,
                maxlength: 120
            },
            cpf: {
                required: true,
                cpfBR: true
            },
            image: 'required',
            email: {
                required: true,
                email: true,
                equalTo: '#txtEmailCadConf'
            },
            password: {
                required: true,
                minlength: 3,
                equalTo: '#txtPasswordConf'
            }
        },
        messages: {
            name: {
                required: 'Insira o nome do usuário',
                maxlength: 'O nome pode ter no máximo 120 caractéres!'
            },
            username: {
                required: 'Insira o login do usuário',
                maxlength: 'O login pode ter no máximo 120 caractéres!'
            },
            cpf: {
                required: 'Insira o cpf do usuário',
                cpfBR: 'Insira um cpf válido'
            },
            image: 'Insira a foto do usuário',
            email: {
                required: 'Insira o email do usuário',
                email: 'Insira um email válido',
                equalTo: 'Os emails não conferem'
            },
            password: {
                required: 'Insira a senha do usuário',
                minlength: 'A senha deve ter no mínimo 3 dígitos',
                equalTo: 'As senhas não conferem'
            }
        },
        submitHandler: function (form) {
            form.submit();
        }
    });
});

function editAccount(url) {

    $('#frm input').val(null);
    clearImageData();


    $.get(
        url,
        function (entity, status) {
            $("#id").val(entity.id);
            $("#username").val(entity.username);
            $("#name").val(entity.name);
            $("#txtEmailCad").val(entity.email);
            $("#txtEmailCadConf").val(entity.email);
            $('#txtCPF').val($('#txtCPF').masked(entity.cpf));

            $.each($('#frm input, #frm textarea'), (index, item) => {
                onEnter(item);
            });
        }
    );

    $("#modal-form").modal();
}

function saveAccount(url) {
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
    var formData = new FormData();

    getCleanFormSerialized('#frm')
        .forEach(x => {
            formData.append(x.name, x.value);
        });

    $.ajax({
        data: formData,
        enctype: 'multipart/form-data',
        type: $('#frm').attr('method'),
        url: $('#frm').attr('action'),
        contentType: false,
        processData: false,
        success: (e) => {
            swal({
                title: 'Salvo!',
                text: 'Registro Salvo com Sucesso!',
                type: 'success'
            }, () => {
                window.location = url;
            });

        },
        error: (e) => {
            swal('Errou!', 'Falha ao salvaro registro!', 'error');
        }
    })
}

function clearImageData() {
    $(".img-preview").src('');
}
