$(() => {

    $('#estrelas button').click((e) => {
        e.preventDefault();

        let star = $(e.currentTarget).children('i');
        if (star.hasClass('fa-star-o')) {
            $(star).addClass('fa-star');
            $(star).removeClass('fa-star-o');
        } else {
            $(star).addClass('fa-star-o');
            $(star).removeClass('fa-star');
        }
    });

    $("#avaliar-form").validate({
        rules: {
            titulo: {
                required: true
            },
            comentario: {
                required: true,
            },
            apelido: {
                required: true,
            },
            localizacao: {
                required: true,
            }
        },
        messages: {
            titulo: {
                required: 'Insira o título!'
            },
            comentario: {
                required: "Insira um comentário"
            },
            apelido: {
                required: 'Insira um apelido!'
            },
            localizacao: {
                required: 'Insira uma localização!'
            }
        },
        submitHandler: function (form) {
            //$('#enviar-avalicao').click();
        }
    });

    $('#enviar-avalicao').click((e) => {
        e.preventDefault();

        if (!($('#avaliar-form').valid())) {
            alert('Preencha os campos obrigatórios!');
            return;
        }

        function geraEstrelas(count) {
            let str = '';


            for (let index = 0; index < count; index++) {
                str += `<span class="fa fa-star"></span>`
            }
            for (let index = 0; index < 5 - count; index++) {
                str += `<span class="fa fa-star-o"></span>`
            }
            return str;
        }

        function addComentario(comentario) {
            $('#div-comentarios').append(`
            <div class="card mb-3">
            <div class="card-header font-weight-bold">
              ${comentario.titulo}
              ${geraEstrelas(comentario.estrelas)}
            </div>
            <div class="card-body">
              <div class="review font-text">
                <p>${new Date().toLocaleDateString()} - ${comentario.apelido}</p>
                <p class="mb-0">
                  ${comentario.comentario}
                </p>
                <hr>
                <p class="mt-3 font-weight-bold">
                ${comentario.resultado}
                </p>
              </div>
            </div>
          </div>
            `);
        }

        let comentario = {
            titulo: $('#txtTitulo').val(),
            estrelas: $('#estrelas button i.fa.fa-star').length,
            comentario: $('#txtComentario').val(),
            apelido: $('#txtApelido').val(),
            localizacao: $('#txtLocalizacao').val(),
            resultado: $('#ddl-avaliacaoGeral option:selected').text()
        };

        addComentario(comentario);
        $('#modal-comentario').toggle('show');
        $('.modal-backdrop').toggle('show');
        alert('Comentário Salvo!');
    });
});