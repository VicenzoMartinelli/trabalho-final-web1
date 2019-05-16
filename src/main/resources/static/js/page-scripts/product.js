$(() => {
    document.querySelector("#acCidade").classList.add("out");
    document.querySelector("#autoComplete_results_list").style.display = "none";

    $('#cnpj').mask('00.000.000/0000-00', {reverse: true});
    $('#phone').mask('(00) 00000-0000');
    $('#cepCode').mask('00000-000');

    $("#frm").validate({
        rules: {
            name: "required",
            cnpj: {
                required: true,
                cnpjBr: true
            },
            phone: {
                required: true
            },
            address: "required",
            burgh: "required",
            addressNumber: {
                required: true
            },
            cepCode: {
                required: true,
                postalcodeBR: true,
                minlength: 9
            }
        },
        messages: {
            nome: "Insira seu nome!",
            email: {
                required: "Insira um email",
                email: "Email inválido"
            },
            confEmail: {
                required: "Confirme o email",
                equalTo: "Os emails não conferem"
            },
            address: "Insira o endereço",
            burgh: "Insira o bairro",
            addressNumber: {
                required: 'Informe o nº'
            },
            cepCode: {
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

const autoCompleteJs = new autoComplete({
    data: {
        src: cidades,
        key: ["label"],
    },
    selector: "#acCidade",
    threshold: 3,
    placeHolder: 'Cidade*',
    debounce: 200,
    searchEngine: "loose",
    highlight: true,
    maxResults: 3,
    onSelection: val => {
        $("#acCidade").val(val.selection.value.label);
        $("#acCidadeId").val(val.selection.value.value);
    }
});

document.querySelector("#acCidade").addEventListener('blur', () => {
    document.querySelector("#autoComplete_results_list").style.display = "none";
});
document.querySelector("#acCidade").addEventListener('focus', () => {
    document.querySelector("#autoComplete_results_list").style.display = "block";
});
["focusin", "focusout", "keydown"].forEach(eventType => {
    document.querySelector("#autoComplete_results_list").addEventListener(eventType, event => {
        if (eventType === "focusin") {
            if (event.target && event.target.nodeName === "LI") {
                document.querySelector("#acCidade").classList.remove("out");
                document.querySelector("#acCidade").classList.add("in");
            }
        } else if (eventType === "focusout" || event.keyCode === 13) {
            document.querySelector("#acCidade").classList.remove("in");
            document.querySelector("#acCidade").classList.add("out");
        }
    });
});

"use strict";

function editProvider(url) {
    $.get(
        url,
        function (entity, status) {
            $("#id").val(entity.id);
            $("#name").val(entity.name);
            $("#acCidadeId").val(entity.city.id);
            $("#acCidade").val(entity.city.name);
            $("#address").val(entity.address);
            $("#addressNumber").val(entity.addressNumber);
            $("#phone").val(entity.phone);
            $("#cepCode").val(entity.cepCode);
            $("#cnpj").val(entity.cnpj);
            $("#burgh").val(entity.burgh);
        }
    );

    $("#modal-form").modal();
}

