"use strict";
let imgAtual;

$(document).ready(function () {
    let select;
    if(!isClient)
    {
        $('[allc-client-only]').hide();
        $('#city').remove();
    }
    else{
        select = new SlimSelect({
            select: '#city'
        });
    }

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

    if(opT != 0)
    {
        $.each($("#frm").get()[0].elements, function(e, inp) {
            if(inp.id.indexOf("txtPassword") == -1)
                $(inp).val(entity[$(inp).prop('name')]);
        });

        $("#txtEmailCadConf").val(entity.email);
        $('#txtPassword').rules('remove');
        $('#txtPasswordConf').rules('remove');

        debugger;
        if(entity.city && entity.city.id != 0)
            select.set(entity.city.id);

        if (entity.imageUrl && entity.imageUrl !== null && entity.imageUrl !== undefined) {
            $('#img-preview').attr('src', `/account/findImage/${entity.imageUrl}`);
            const parts = entity.imageUrl.split("_");
            const size = parseInt(parts[1]);
            parts.splice(0, 2);
            const name = parts.join("_");

            imgAtual = new File(
                [new ArrayBuffer(size)],
                name,
                {
                    type: `image/${name.split('.').pop()}`
                });
        }

        $.each($('#frm input[type=text], #frm input[type=email], #frm input[type=password], #frm input[type=tel], #frm textarea'), (index, item) => {
            onEnter(item);
        });
    }
    hideLoading();
});

function saveProfile(url) {
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

    formData.append('image', $('#image-input')[0].files[0] || imgAtual);

    $.ajax({
        data: formData,
        enctype: 'multipart/form-data',
        type: $('#frm').attr('method'),
        url: isClient ? '/profile/saveProfile' : '/account/addAccount',
        async: false,
        cache: false,
        contentType: false,
        processData: false,
        success: (e) => {
            debugger;
            swal({
                title: 'Cadastro salvo com sucesso!',
                text: 'Favor efetue login!',
                type: 'success'
            }, () => {
                returnToIndex();
            });

        },
        error: (e) => {
            debugger;
            swal('Errou!', 'Falha ao salvaro registro!', 'error');
        }
    })
}

function returnToIndex() {
    window.location = isClient ? '/' : '/admin';
}