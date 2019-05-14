$(() => {
    $('#txtCPF').mask('000.000.000-00', {reverse: true});
    $('#txtTelefone').mask('(00) 00000-0000');
    $('#txtCEP').mask('00000-000');
    $('#txtFrete').mask('00000-000');


    $("#entrar").validate({
        rules: {
            username: {
                required: true
            },
            senha: {
                required: true,
            }
        },
        messages: {
            password: {
                required: "Insira sua senha"
            },
            username: {
                required: "Insira um email"
            }
        },
        submitHandler: function (form) {
            form.submit();
        }
    });

    $("#signup").validate({
        rules: {
            nome: "required",
            cpf: {
                required: true,
                cpfBR: true
            },
            email: {
                required: true,
                email: true
            },
            confEmail: {
                required: true,
                equalTo: '#txtEmailCad'
            },
            senha: {
                required: true,
                minlength: 8
            },
            confSenha: {
                required: true,
                equalTo: "#txtSenhaCad"
            },
            rua: "required",
            endenumero: {
                required: true
            },
            cep: {
                required: true,
                postalcodeBR: true,
                minlength: 9
            }
        },
        messages: {
            nome: "Insira seu nome!",
            senha: {
                required: "Insira sua senha",
                minlength: "Insira ao menos 8 caracteres"
            },
            confSenha: {
                required: "Confirme a senha",
                equalTo: "As senhas não conferem"
            },
            cpf: {
                required: "Insira um CPF",
                cpfBR: "Insira um cpf válido"
            },
            email: {
                required: "Insira um email",
                email: "Email inválido"
            },
            confEmail: {
                required: "Confirme o email",
                equalTo: "Os emails não conferem"
            },
            rua: "Insira o endereço",
            endenumero: {
                required: 'Informe o nº'
            },
            cep: {
                required: "Insira o CEP",
                postalcodeBR: "CEP inválido",
                minlength: 'CEP inválido'
            }
        },
        submitHandler: function (form) {
            form.submit();
        }
    });
});