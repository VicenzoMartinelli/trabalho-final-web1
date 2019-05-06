"use strict";

function editSerie(url) {
    $.get(
        url,
        function (entity, status) {
            $("#id").val(entity.id);
            $("#nome").val(entity.nome);
            $("#nota").val(entity.nota);
            $("#resumo").val(entity.resumo);
            $("#genero").val(entity.genero.id);
            $("#produtora").val(entity.produtora.id);

            $("#dataEstreia").val(formatDate(entity.dataEstreia));

            if (entity.dataEncerramento) {
                $("#dataEncerramento").val(formatDate(entity.dataEncerramento));
            }
        }
    );

    $("#modal-form").modal();
}

function formatDate(inputFormat) {
    const pad = (s) => {
        return (s < 10) ? '0' + s : s;
    };

    let d = new Date(inputFormat);
    return [pad(d.getDate()), pad(d.getMonth() + 1), d.getFullYear()].join('/')

}