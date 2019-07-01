"use strict";

const order = {
    id: null,
    delivered: false,
    orderDate: '',
    provider: {},
    description: '',
    orderItems: []
};

let editingItem = {};

function resetItem() {
    editingItem = {
        id: null,
        count: 0,
        value: 0,
        product: {}
    };
}

const templateItemElement = (obj) =>
`<div class="card text-white bg-dark pl-2 product-item disintegration-target">
    <input type="hidden" data-product value="${obj.product.id}">
    <p class="p-0">${obj.product.name}</p>
    
    <div>
        <span>Unidades: ${obj.count}</span><br>
        <span>Valor Unitário: R$ ${obj.value}</span><br>
        <span>Total Produto: R$ ${obj.value * obj.count}</span>
    </div>
    <a class="text-white edit" onclick="openProductOrderItem(true, this);">
        <i class="far fa-edit fa-lg"></i>
    </a>
    
    <a class="text-white delete" onclick="deleteProductOrderItem(this);">
        <i class="far fa-trash-alt fa-lg"></i>
    </a>
</div>`;

$(document).ready(function () {
    $(".add-product-btn").click(() => openProductOrderItem(false, this));
    $(".edit").click(function(ev) {
        openProductOrderItem(true, this);
    });
    $(".delete").click(function(ev) {
        deleteProductOrderItem(this);
    });
    $('#product').change(cmbProductsOnChange);
    resetItem();

    $("#frm-order").validate({
        rules: {
            description: {
                maxlength: 255
            },
            provider: {
                required: true
            },
            orderDate: {
                required: true
            }
        },
        messages: {
            description: {
                maxlength: 'A descrição pode ter no máximo 255 caractéres!'
            },
            provider: {
                required: 'Insira o fornecedor do pedido'
            },
            orderDate: {
                required: 'Insira o data do pedido'
            }
        },
        submitHandler: function (form) {
            form.submit();
        }
    });
});

function saveProviderOrder() {
    if (!($('#frm-order').valid())) {
        swal({
            title: 'Erro!',
            text: 'Verifique as informações do seu formulário!',
            type: 'error',
            showConfirmButton: false,
            timer: 1000
        });
        return;
    }
    if(order.orderItems.length === 0)
    {
        swal({
            title: 'Erro!',
            text: 'Insira ao menos um produto ao pedido!',
            type: 'error',
            showConfirmButton: false,
            timer: 1000
        });
        return;
    }

    order.delivered   = $('#delivered').prop('checked');
    order.description = $('#description').val();
    order.orderDate   = $('#orderDate').val();
    order.provider.id = parseInt($('#provider').val());

    $.ajax({
        type: $('#frm-order').attr('method'),
        url: $('#frm-order').attr('action'),
        contentType : 'application/json',
        data : JSON.stringify(order),
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

function openProductOrderItem(editing, element) {
    if(editing) {
        let id = $(element)
            .parents('.col-lg-4')
            .find('[data-product]').val();


        editingItem = order.orderItems.filter((x) => x.product.id === id)[0];
        let { value, count, product } = editingItem;
        $("#product").val(product.id).focus();
        $("#count").val(count);
        $("#value").val(value);
    }else{
        onOpenModal();
    }
    $("#frm").modal();
}

function saveProductOrderItem() {
    let { id } = editingItem.product;

    editingItem.product.id   = $("#product").val();
    editingItem.product.name = $("#product option:selected").text();
    editingItem.count        = $("#count").val();
    editingItem.value        = $("#value").val();

    if(id && id > 0)
    {
        debugger;
        order.orderItems[order.orderItems.findIndex((x) => x.product.id == id)] = editingItem;
        let parent = $(`input[data-product][value=${id}]`).parent().parent();

        parent.html(templateItemElement(editingItem));
    }
    else{
        order.orderItems.push(editingItem);
        $("#products-container")
            .append(`<div class="col-lg-4">${templateItemElement(editingItem)}</div>`)

    }
    resetItem();
    updateLblTotal();
    swal({
        title: 'Salvo!',
        text: 'Produto Salvo com Sucesso!',
        type: 'success'
    }, () => $("#frm").modal('hide'));
}

function deleteProductOrderItem(element) {
    let colElement = $(element)
        .parents('.col-lg-4')
        .find('[data-product]');

    let id = colElement.val();

    swal({
        text: 'Deseja realmente excluir o produto?',
        title: 'Certeza?',
        type: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#DD6B55',
        cancelButtonText: 'Cancelar',
        confirmButtonText: 'Remover',
        closeOnConfirm: true
    }, () => {
        let htmlElement = $(element).parents('.card').get()[0];
        if (htmlElement.disintegrated) { return; }
        htmlElement.disintegrated = true;
        disintegrate(htmlElement);
        setTimeout(() => {
            $(element).parents('.col-lg-4').remove();
            order.orderItems.splice(order.orderItems.findIndex((x) => x.product.id == id), 1);
            updateLblTotal();
        }, 2000);
    });
}

function cmbProductsOnChange() {
    let val = $('#product').val();

    if(!editingItem.product.id && val > 0)
    {
        $('#value').val($('#product option:selected').attr('price'));
        onEnter($('#value').get()[0]);
    }
}

function updateLblTotal() {
    const val = order.orderItems.length == 0 ? 0 :
        order.orderItems
        .map(item => item.count * item.value)
        .reduce((prev, next) => prev + next);


    $('#total-produtos').text(String(val.toFixed(2)).replace('.', ','));
}

function finalize(url, alreadyDelivered) {
    if(alreadyDelivered)
    {
        swal({
            title: 'Atenção!',
            type: 'warning',
            text: 'Este pedido já foi entregue, impossível alterar!'
        });
        return;
    }

    let trat = () => {
        let destino = url;
        $.ajax({
            type: 'PUT',
            url: destino,
            success: function () {
                swal({
                    title: 'Atualização',
                    text: 'Pedido finalizado com sucesso!',
                    type: 'success'
                }, () => {
                    window.location.reload();
                });
            },
            error: () => {
                swal(
                    'Erro',
                    'Falha ao realizar a operação!',
                    'error'
                );
            }
        })

    };

    swal({
        title: 'Deseja realmente marcar o pedido como entregue?',
        text: 'Esta ação não poderá ser desfeita!',
        type: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#DD6B55',
        cancelButtonText: 'Cancelar',
        confirmButtonText: 'Finalizar',
        closeOnConfirm: false
    }, trat);
}

function cancelOrder(url, alreadyDelivered) {
    if(alreadyDelivered)
    {
        swal({
            title: 'Atenção!',
            type: 'warning',
            text: 'Este pedido já foi entregue, impossível alterar!'
        });
        return;
    }

    let trat = () => {
        let destino = url;
        $.ajax({
            type: 'PUT',
            url: destino,
            success: function () {
                swal({
                    title: 'Atualização',
                    text: 'Pedido cancelado com sucesso!',
                    type: 'success'
                }, () => {
                    window.location.reload();
                });
            },
            error: () => {
                swal(
                    'Erro',
                    'Falha ao realizar a operação!',
                    'error'
                );
            }
        })

    };

    swal({
        title: 'Deseja realmente cancelar o pedido?',
        text: 'Esta ação não poderá ser desfeita!',
        type: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#DD6B55',
        cancelButtonText: 'Cancelar',
        confirmButtonText: 'Finalizar',
        closeOnConfirm: false
    }, trat);
}