"use strict";

const files = [];

const previewTemplate = (src) => `<div id="preview" class="file-row template">
                            <div>
                                <span class="preview">
                                    <img src="${src}" id="" data-dz-thumbnail />
                                </span>
                            </div>
                            <div>
                              <button onclick="return removeImg(this);" type="button" data-dz-remove class="btn btn-danger delete">
                                <i class="glyphicon glyphicon-trash"></i>
                                <span>Excluir</span>
                              </button>
                            </div>
                          </div>`;

function removeImg(emitter) {
    let html = emitter.parentElement.parentElement;

    html.remove();

    let item = files.filter((x) => x.previewElement == html)[0];

    files.splice(files.indexOf(item), 1);
}

$(document).ready(function () {

    $(".file-dropzone").on('dragover', handleDragEnter);
    $(".file-dropzone").on('dragleave', handleDragLeave);
    $(".file-dropzone").on('drop', handleDragLeave);

    function handleDragEnter(e) {

        this.classList.add('drag-over');
    }

    function handleDragLeave(e) {

        this.classList.remove('drag-over');
    }

    Dropzone.options.formUpload = {
        autoProcessQueue: false,
        uploadMultiple: true,
        acceptedFiles: ".jpeg,.jpg,.png",
        maxFilesize: 256, // MB
        maxFiles: 100,
        paramName: 'file',
        previewsContainer: ".dropzone-previews",
        addedfile: function (file) {
            files.push(file);
            debugger;

            var reader = new FileReader();

            reader.onload = function (e) {
                $(".dropzone-previews").append(previewTemplate(e.target.result));
            };

            reader.readAsDataURL(file);
        },
    }
});

function editProduct(url) {

    $('#frm input, #frm textarea, #frm select').val(null);
    clearImageData();


    $.get(
        url,
        function (entity, status) {
            $("#id").val(entity.id);
            $("#name").val(entity.name);
            $("#description").val(entity.description);
            $("#value").val(entity.value);
            $("#count").val(entity.value);
            $("#category").val(entity.category.id);
            $("#brand").val(entity.brand.id);

            $.each($('#frm input, #frm textarea'), (index, item) => {
                onEnter(item);
            });

            entity.urlsImgs.forEach((x) => {
                $(".dropzone-previews").append(previewTemplate(x));
                const identifier = x.split('/').pop();
                const parts = identifier.split("_");
                const size = parseInt(parts[1]);
                parts.splice(0, 2);
                const name = parts.join("_");

                files.push(new File(
                    [new ArrayBuffer(size)],
                    name,
                    {
                        type: `image/${name.split('.').pop()}`
                    }));
            });
        }
    );

    $("#modal-form").modal();
}

function saveProduct(url) {
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

    let fileCount = files.length;
    if (fileCount > 0) {
        for (let i = 0; i < fileCount; i++) {
            formData.append('file', files[i]);
        }
    }
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
    while (files.length > 0)
        files.pop();

    $(".dropzone-previews").empty();
}
